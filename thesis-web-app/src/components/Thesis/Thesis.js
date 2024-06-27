import { useCallback, useContext, useEffect, useState } from "react";
import "./Thesis.css";
import { Button, Col, Row, Stack } from "react-bootstrap";
import { Link, useSearchParams } from "react-router-dom";
import API, { authAPI, endpoints } from "../../configs/API";
import { LoadingContext, UserContext } from "../../configs/Context";
import {
	CustomerSnackbar,
	isAcademicManager,
	isLecturer,
	isStudent,
} from "../Common/Common";
import {
	Alert,
	Dialog,
	DialogActions,
	DialogTitle,
	Pagination,
} from "@mui/material";

function Thesis() {
	const [theses, setTheses] = useState([]);
	const [user] = useContext(UserContext);
	const [, loadingDispatch] = useContext(LoadingContext);
	const [page, setPage] = useState(1);
	const [totalPage, setTotalPage] = useState(1);
	const [openDialog, setOpenDialog] = useState(false);
	const [thesisId, setThesisId] = useState();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Xóa khóa luận thành công",
		severity: "success",
	});
	const [params] = useSearchParams();

	const loadTheses = useCallback(async () => {
		let response;
		if (isAcademicManager(user)) {
			let url = `${endpoints["theses"]}?page=${page}`;

			let search = params.get("search");
			if (search) url = `${url}&search=${search}`;

			response = await API.get(url);
			if (response.status === 200) {
				setTheses(response.data.result);
				setTotalPage(response.data.totalPages);
			}
		} else {
			if (isLecturer(user))
				response = await authAPI().get(endpoints["thesesOfLecturer"]);
			else if (isStudent(user))
				response = await authAPI().get(endpoints["thesisOfUser"]);

			if (response.status === 200) setTheses(response.data);
		}
	}, [page, params, user]);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Trang chủ";
		loadTheses();
		loadingDispatch({ type: "unloading" });
	}, [loadTheses, loadingDispatch]);

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

	const handleDeleteThesis = async (thesisId) => {
		loadingDispatch({ type: "loading" });
		try {
			const response = await authAPI().delete(
				endpoints["thesisDetail"](thesisId)
			);

			if (response.status === 204) {
				showSnackbar("Xóa khóa luận thành công", "success");
				loadTheses();
			}
		} catch {
			showSnackbar("Xóa khóa luận thất bại", "error");
		}
		loadingDispatch({ type: "unloading" });
	};

	const handleCloseDialog = () => {
		setOpenDialog(false);
	};

	const handleSuccessDialog = () => {
		setOpenDialog(false);
		handleDeleteThesis(thesisId);
	};

	return (
		<>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			{isAcademicManager(user) && (
				<>
					<Link to="add-thesis" className="btn btn-success mt-4">
						Thêm khóa luận
					</Link>
					<Pagination
						count={totalPage}
						color="primary"
						className="mt-4"
						onChange={(event, value) => setPage(value)}
					/>
					<Dialog
						open={openDialog}
						onClose={handleCloseDialog}
						aria-labelledby="alert-dialog-title"
						aria-describedby="alert-dialog-description">
						<DialogTitle id="alert-dialog-title">
							<Alert severity="warning">
								Bạn có chắc chắn xóa khóa luận không?
							</Alert>
						</DialogTitle>
						<DialogActions>
							<Button onClick={handleSuccessDialog}>Đồng ý</Button>
							<Button variant="danger" onClick={handleCloseDialog} autoFocus>
								Hủy
							</Button>
						</DialogActions>
					</Dialog>
				</>
			)}

			<Row className="my-2">
				{theses.length < 1 ? (
					<>
						<Alert variant="filled" severity="info" className="w-50 mx-auto">
							Hiện không có khóa luận
						</Alert>
					</>
				) : (
					<>
						{theses.map((thesis) => {
							const url = `theses/${thesis.id}`;
							return (
								<Col md={6} key={thesis.id}>
									<Row className="thesis-item my-3 w-100">
										{isLecturer(user) && (
											<>
												{thesis.isScoring ? (
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
										<Col className="px-0">
											<h5>{thesis.name}</h5>
											{!isLecturer(user) && (
												<h6>
													Điểm:{" "}
													{thesis.score !== null
														? thesis.score.toFixed(2)
														: "Đang trong quá trình chấm điểm"}
												</h6>
											)}
											<div>
												Cập nhật lần cuối:{" "}
												{new Date(thesis.updateDate).toLocaleString()}
											</div>
										</Col>
										<Col md="auto" className="px-0">
											<Stack gap={2} direction="vertical">
												<Link to={url} className="btn btn-success">
													Xem chi tiết
												</Link>
												{isAcademicManager(user) && (
													<Button
														variant="danger"
														onClick={() => {
															setThesisId(thesis.id);
															setOpenDialog(true);
														}}>
														Xóa
													</Button>
												)}
											</Stack>
										</Col>
									</Row>
								</Col>
							);
						})}
					</>
				)}
			</Row>
		</>
	);
}

export default Thesis;
