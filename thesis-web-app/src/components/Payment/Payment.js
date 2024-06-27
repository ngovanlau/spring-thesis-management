import { useCallback, useContext, useEffect, useState } from "react";
import "../Payment/Payment.css";
import { LoadingContext, UserContext } from "../../configs/Context";
import { Button, Col, Row } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import API, { authAPI, endpoints } from "../../configs/API";
import { CustomerSnackbar } from "../Common/Common";
import { CircularProgress } from "@mui/material";

function Payment() {
	const [user] = useContext(UserContext);
	const { thesisId, orderId } = useParams();
	const [thesis, setThesis] = useState();
	const [, loadingDispatch] = useContext(LoadingContext);
	const [success, setSuccess] = useState(false);
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thanh toán thành công",
		severity: "success",
	});
	const [hidden, setHidden] = useState(true);

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

	const loadThesis = useCallback(async () => {
		const response = await API.get(endpoints["thesisDetail"](thesisId));
		setThesis(response.data);
	}, [thesisId]);

	const checkPayment = useCallback(async () => {
		if (orderId === "fail") {
			showSnackbar("Thanh toán không thành công", "error");
		} else if (orderId !== "0") {
			try {
				const response = await authAPI().get(
					endpoints["checkPayment"](orderId)
				);

				if (response.status === 200) {
					if (response.data) {
						showSnackbar("Thanh toán thành công", "success");
						setSuccess(true);
					} else {
						showSnackbar("Thanh toán thất bại", "error");
						setSuccess(false);
					}
				}
			} catch {
				showSnackbar("Lỗi kiểm tra quá trình thanh toán", "error");
			}
		} else setHidden(false);

		setHidden(false);
	}, [orderId]);

	useEffect(() => {
		loadingDispatch({ tyoe: "loading" });
		loadThesis();
		checkPayment();
		loadingDispatch({ tyoe: "unloading" });
	}, [loadThesis, checkPayment, loadingDispatch]);

	const handlePayment = async () => {
		setHidden(true);
		loadingDispatch({ type: "loading" });

		const response = await authAPI().post(endpoints["payment"], {
			amount: 10000,
			thesisId: thesis.thesis.id,
		});

		window.location.href = response.data;

		loadingDispatch({ type: "unloading" });
	};

	const handleDownload = async () => {
		setHidden(true);
		loadingDispatch({ type: "loading" });
		try {
			const response = await authAPI().post(
				endpoints["printPDF"],
				{
					thesisId: thesis.thesis.id,
					committeeId: thesis.committee.id,
				},
				{ responseType: "blob" }
			);

			if (response.status === 200) {
				const url = window.URL.createObjectURL(new Blob([response.data]));
				const a = document.createElement("a");
				a.href = url;
				a.download = "KET_QUA_KHOA_LUAN.pdf";
				document.body.appendChild(a);
				a.click();
				window.URL.revokeObjectURL(url);
				showSnackbar("Download thành công", "success");
			}
		} catch {
			showSnackbar("Download không thành công", "error");
		}
		setHidden(false);
		loadingDispatch({ type: "unloading" });
	};

	return (
		<>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			<div className="payment-item w-50 mx-auto my-4">
				{success ? (
					<>
						<h4 className="text-center text-success">DOWNLOAD PDF</h4>

						<div className="my-4 d-flex justify-content-center">
							{hidden ? (
								<>
									<CircularProgress />
								</>
							) : (
								<>
									<Button onClick={handleDownload}>Download</Button>
								</>
							)}
						</div>
					</>
				) : (
					<>
						<h4 className="text-center text-success">
							THANH TOÁN PHÍ DOWNLOAD PDF
						</h4>

						<Row className="mx-auto">
							<Col>
								Họ tên: {user.user.lastName + " " + user.user.firstName}
							</Col>
							<Col>Phí: 10,000 VNĐ</Col>
						</Row>

						<div
							className={`my-4 d-flex ${
								hidden ? "justify-content-center" : "justify-content-between"
							}`}>
							{hidden ? (
								<>
									<CircularProgress />
								</>
							) : (
								<>
									<Button onClick={handlePayment}>Thanh toán</Button>
									<Link
										to={`/theses/${thesisId}`}
										className="btn btn-danger ms-4">
										Hủy
									</Link>
								</>
							)}
						</div>
					</>
				)}
			</div>
		</>
	);
}

export default Payment;
