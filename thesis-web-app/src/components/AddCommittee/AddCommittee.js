import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { Button, FloatingLabel, Form, Stack } from "react-bootstrap";
import Select from "react-select";
import "../AddCommittee/AddCommittee.css";
import { useNavigate } from "react-router-dom";
import API, { authAPI, endpoints } from "../../configs/API";
import { LoadingContext } from "../../configs/Context";
import { CustomerSnackbar } from "../Common/Common";

function AddCommittee() {
	const [, loadingDispatch] = useContext(LoadingContext);
	const [name, setName] = useState("");
	const [members, setMembers] = useState({
		chairman: null,
		secretary: null,
		criticalLecturer: null,
		member: null,
	});
	const [lecturers, setLecturers] = useState([]);
	const lecturerRef = useRef({
		chairman: null,
		secretary: null,
		criticalLecturer: null,
		member: null,
	});
	const [hidden, setHidden] = useState(true);
	const navigate = useNavigate();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thêm hội đồng thành công",
		severity: "success",
	});

	const loadLecturers = useCallback(async () => {
		const response = await API.get(endpoints["lecturers"]);

		setLecturers(
			response.data.map((l) => {
				return {
					value: l.id,
					label: `${l.lastName} ${l.firstName} - ${l.useruniversityid}`,
				};
			})
		);
		// lecturerRef.current = lecturers;
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Thêm hội đồng";
		loadLecturers();
		loadingDispatch({ type: "unloading" });
	}, [loadLecturers, loadingDispatch]);

	const isOptionSelected = (_, selectValue) => {
		return selectValue.length > 1;
	};

	const handleChange = (field, e) => {
		setMembers({ ...members, [field]: e });

		const lecturer = lecturerRef.current[field];

		if (e === null) {
			setLecturers((current) => {
				return [...current, lecturer];
			});
		} else {
			if (lecturer === null) {
				setLecturers((current) => {
					return [...current.filter((l) => l.value !== e.value)];
				});
			} else {
				setLecturers((current) => {
					return [...current.filter((l) => l.value !== e.value), lecturer];
				});
			}
		}

		lecturerRef.current = {
			...lecturerRef.current,
			[field]: e,
		};
	};

	const handleMultiChange = (field, e) => {
		setMembers({ ...members, [field]: e });

		let deleteLecturers = e;
		let backupLecturers = lecturerRef.current[field];

		if (deleteLecturers !== null && backupLecturers !== null) {
			deleteLecturers = deleteLecturers.filter((i) => {
				return !lecturerRef.current[field].includes(i);
			});

			deleteLecturers.forEach((d) => {
				setLecturers((current) => {
					return [...current.filter((l) => l.value !== d.value)];
				});
			});

			backupLecturers = backupLecturers.filter((i) => {
				return !e.includes(i);
			});

			backupLecturers.forEach((b) => {
				setLecturers((current) => {
					return [...current, b];
				});
			});
		}

		if (deleteLecturers !== null && backupLecturers === null) {
			deleteLecturers.forEach((d) => {
				setLecturers((current) => {
					return [...current.filter((l) => l.value !== d.value)];
				});
			});
		}

		if (backupLecturers !== null && deleteLecturers === null) {
			backupLecturers.forEach((b) => {
				setLecturers((current) => {
					return [...current, b];
				});
			});
		}

		lecturerRef.current = {
			...lecturerRef.current,
			[field]: e,
		};
	};

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

	const addCommittee = async (event) => {
		event.preventDefault();
		loadingDispatch({ type: "loading" });
		try {
			let data = [];
			data.push({
				roleName: "Chủ tịch",
				userId: members["chairman"].value,
			});
			data.push({
				roleName: "Thư kí",
				userId: members["secretary"].value,
			});
			data.push({
				roleName: "Phản biện",
				userId: members["criticalLecturer"].value,
			});

			const member = members["member"];
			if (member !== null && member.length > 0) {
				member.map((m) =>
					data.push({
						roleName: "Thành viên",
						userId: m.value,
					})
				);
			}

			const committee = {
				name: name,
				committeeUserDtos: data,
			};

			const response = await authAPI().post(endpoints["committees"], committee);

			if (response.status === 201) {
				showSnackbar("Thêm hội đồng thành công", "success");
				setTimeout(() => {
					navigate("/committees");
				}, 1000);
			}
		} catch {
			showSnackbar("Thêm hội đồng thất bại", "error");
		}
		loadingDispatch({ type: "unloading" });
	};

	const changeHidden = () => {
		setHidden(!hidden);
	};

	return (
		<div>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			<h1 className="text-success text-center my-3">
				Thêm hội đồng bảo vệ khóa luận
			</h1>
			<Stack>
				<Form className="w-50 mx-auto" onSubmit={addCommittee}>
					<FloatingLabel
						controlId="floatingInput"
						label="Tên hội đồng "
						className="mb-3">
						<Form.Control
							type="text"
							placeholder="Nhập tên hội đồng"
							className="mb-3"
							onChange={(e) => setName(e.target.value)}
							required
						/>
					</FloatingLabel>

					<Form.Group className="mb-3">
						<Form.Label>Chủ tịch</Form.Label>
						<Select
							name="lecturers"
							options={lecturers}
							className="basic-single fs-6 mb-3"
							classNamePrefix="select"
							isSearchable={true}
							placeholder="Chọn giảng viên"
							hideSelectedOptions={true}
							onChange={(e) => handleChange("chairman", e)}
							required
							isClearable
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Thư kí</Form.Label>
						<Select
							name="lecturers"
							options={lecturers}
							className="basic-single fs-6 mb-3"
							classNamePrefix="select"
							isSearchable={true}
							placeholder="Chọn giảng viên"
							hideSelectedOptions={true}
							onChange={(e) => handleChange("secretary", e)}
							required
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Giảng viên phản biện</Form.Label>
						<Select
							name="lecturers"
							options={lecturers}
							className="basic-single fs-6 mb-3"
							classNamePrefix="select"
							isSearchable={true}
							placeholder="Chọn giảng viên"
							hideSelectedOptions={true}
							onChange={(e) => handleChange("criticalLecturer", e)}
							required
						/>
					</Form.Group>

					<Form.Group hidden={hidden} className="mb-3">
						<Form.Label>
							Thành viên{" "}
							<span className="text-danger">
								(Có thể chọn tối đa 2 giảng viên)
							</span>
						</Form.Label>
						<Select
							isMulti
							name="lecturers"
							options={lecturers}
							className="basic-multi-select fs-6 mb-3"
							classNamePrefix="select"
							isOptionSelected={isOptionSelected}
							isSearchable={true}
							onChange={(e) => handleMultiChange("member", e)}
							placeholder="Chọn giảng viên"
						/>
					</Form.Group>

					<FloatingLabel>
						<Button
							onClick={changeHidden}
							variant="success"
							className="mb-3 fs-6">
							{hidden ? "Thêm thành viên" : "Ẩn thành viên"}
						</Button>
					</FloatingLabel>

					<FloatingLabel>
						<Button type="submit" variant="primary" className="mb-3 fs-6">
							Thêm hội đồng
						</Button>
					</FloatingLabel>
				</Form>
			</Stack>
		</div>
	);
}

export default AddCommittee;
