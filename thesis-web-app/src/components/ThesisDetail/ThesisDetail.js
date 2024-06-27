import { useCallback, useContext, useEffect, useState } from "react";
import API, { authAPI, endpoints } from "../../configs/API";
import { Button, Form } from "react-bootstrap";
import Select from "react-select";
import {
	Link,
	Outlet,
	useNavigate,
	useParams,
	useResolvedPath,
} from "react-router-dom";
import { LoadingContext, UserContext } from "../../configs/Context";
import {
	CustomerSnackbar,
	isAcademicManager,
	isLecturer,
	isStudent,
} from "../Common/Common";
import { Alert } from "@mui/material";

function ThesisDetail() {
	const [, loadingDispatch] = useContext(LoadingContext);
	const [user] = useContext(UserContext);
	const [thesis, setThesis] = useState();
	const { thesisId } = useParams();
	const [committees, setCommittees] = useState([]);
	const [hidden, setHidden] = useState(true);
	const [committee, setCommittee] = useState(null);
	const navigate = useNavigate();
	const patch = useResolvedPath();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thành công",
		severity: "success",
	});

	const loadThesis = useCallback(async () => {
		const response = await API.get(endpoints["thesisDetail"](thesisId));
		setThesis(response.data);
	}, [thesisId]);

	const loadCommittees = useCallback(async () => {
		const response = await API.get(endpoints["activeCommittees"]);

		setCommittees(
			response.data.map((committee) => {
				return { value: committee.id, label: committee.name };
			})
		);
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Chi tiết khóa luận";
		loadThesis();
		loadCommittees();
		loadingDispatch({ type: "unloading" });
	}, [loadThesis, loadCommittees, loadingDispatch, patch]);

	const showSnackbar = (message, severity) => {
		setData({
			message: message,
			severity: severity,
		});

		setOpen(true);

		setTimeout(() => {
			setOpen(false);
		}, 2000);
	};

	const changeHidden = () => {
		setHidden(!hidden);
	};

	const addCommittee = async () => {
		loadingDispatch({ type: "loading" });
		if (committee === null || thesis.thesis.score > 0) {
			if (committee === null) showSnackbar("Vui lòng chọn hội đồng", "error");

			if (thesis.thesis.score > 0)
				showSnackbar("Khóa luận đã được chấm điểm", "error");
		} else {
			const response = await authAPI().patch(
				endpoints["addOrUpdateCommitteeForThesis"],
				{
					thesisId: parseInt(thesisId),
					committeeId: committee,
				}
			);

			if (response.status === 200) {
				showSnackbar("Thêm hội đồng vào khóa luận thành công", "success");
				setThesis(response.data);
				loadCommittees();
				setHidden(true);
			}
		}
		loadingDispatch({ type: "unloading" });
	};

	const checkScoring = () => {
		if (thesis.scores === null || thesis.scores.length < 1) {
			return false;
		} else {
			return thesis.scores.find((s) => s.userId === user.user.id);
		}
	};

	const handleNextScoring = () => {
		navigate(`/theses/${thesisId}/score`);
	};

	return (
		<>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			{thesis ? (
				<>
					<div className="w-75 thesis-item my-4 mx-auto">
						{isLecturer(user) && (
							<>
								{checkScoring() ? (
									<>
										<Alert severity="success" className="mb-3">
											Đã chấm điểm
										</Alert>
									</>
								) : (
									<>
										<Alert severity="info" className="mb-3">
											Chưa chấm điểm{" "}
										</Alert>
									</>
								)}
							</>
						)}
						<h2>{thesis.thesis.name}</h2>
						<div>
							Sinh viên thực hiện:{" "}
							{thesis.students.map((student) => (
								<span key={student.id}>
									{student.lastName} {student.firstName},{" "}
								</span>
							))}
						</div>
						<div>
							Giảng viên hướng dẫn:{" "}
							{thesis.lecturers.map((lecturer) => (
								<span key={lecturer.id}>
									{lecturer.lastName} {lecturer.firstName},{" "}
								</span>
							))}
						</div>
						<div>
							Hội đồng bảo vệ:{" "}
							{thesis.committee === null
								? "Chưa có hội đồng"
								: thesis.committee.name}
						</div>

						{!isLecturer(user) && (
							<div>
								Điểm:{" "}
								{thesis.thesis.score !== null
									? thesis.thesis.score
									: "Đang trong quá trình chấm điểm"}
							</div>
						)}
						<div>
							Cập nhật lần cuối:{" "}
							{new Date(thesis.thesis.updateDate).toLocaleString()}
						</div>

						{isAcademicManager(user) && (
							<>
								<div hidden={hidden} className="thesis-item my-4 w-100">
									<Form.Group className="mb-3">
										<Form.Label>Chọn hội đồng</Form.Label>
										<Select
											name="committees"
											options={committees}
											className="basic-single fs-6 mb-3"
											classNamePrefix="select"
											isSearchable={true}
											placeholder="Chọn hội đồng"
											hideSelectedOptions={true}
											onChange={(e) => {
												if (e !== null) setCommittee(e.value);
												else setCommittee(null);
											}}
											required
											isClearable
										/>
									</Form.Group>

									<Button variant="success" onClick={addCommittee}>
										{thesis.committee === null ? "Thêm" : "Chỉnh sửa"}
									</Button>
								</div>

								<div className="mt-4">
									{hidden ? (
										<>
											<Button variant="info" onClick={changeHidden}>
												{thesis.committee === null
													? "Thêm hội đồng"
													: "Chỉnh sửa hội đồng"}
											</Button>
										</>
									) : (
										<>
											<Button variant="danger" onClick={changeHidden}>
												{thesis.committee === null
													? "Ẩn thêm hội đồng"
													: "Ẩn chỉnh sửa hội đồng"}
											</Button>
										</>
									)}
								</div>
							</>
						)}

						{isLecturer(user) && (
							<>
								{patch.pathname === `/theses/${thesis.thesis.id}` && (
									<div className="mt-4" hidden={!hidden}>
										<Button onClick={handleNextScoring} variant="success">
											{checkScoring() ? "Chỉnh sửa điểm" : "Chấm điểm"}
										</Button>
									</div>
								)}

								<Outlet />
							</>
						)}

						{isStudent(user) && (
							<>
								{patch.pathname === `/theses/${thesis.thesis.id}` && (
									<div className="mt-4">
										<Link to="payment/0" className="btn btn-info">
											In file PDF
										</Link>
									</div>
								)}

								<Outlet />
							</>
						)}
					</div>
				</>
			) : (
				<></>
			)}
		</>
	);
}

export default ThesisDetail;
