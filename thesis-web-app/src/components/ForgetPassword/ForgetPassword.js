import { useEffect, useState } from "react";
import { CustomerSnackbar } from "../Common/Common";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { CircularProgress } from "@mui/material";
import API, { endpoints } from "../../configs/API";
import { useNavigate } from "react-router-dom";

function ForgetPassword() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [requiredPassword, setRequiredPassword] = useState("");
	const [otp, setOtp] = useState("");
	const [open, setOpen] = useState(false);
	const [loading, setLoading] = useState(false);
	const [data, setData] = useState({
		message: "Thay đổi password thành công",
		severity: "success",
	});
	const [showInputPassword, setShowInputPassword] = useState(false);
	const [showInputOtp, setShowInputOtp] = useState(false);
	const [showInputUsername, setShowInputUsername] = useState(true);
	const navigate = useNavigate();

	useEffect(() => {
		document.title = "Quên password";
	}, []);

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

	const getOtp = async () => {
		setLoading(true);
		try {
			const response = await API.post(endpoints["forgetPassword"], {
				username: username,
			});

			if (response.status === 200) {
				showSnackbar("Gửi mã OTP thành công", "success");
				setShowInputUsername(false);
				setShowInputOtp(true);
			}
		} catch {
			showSnackbar("Không tồn tại tài khoản", "error");
		}
		setLoading(false);
	};

	const handleInputUsername = (e) => {
		if (e.target.value.trim().length === 0) {
			setUsername("");
			showSnackbar("Không được nhập khoảng trắng vào username", "error");
		} else {
			setUsername(e.target.value.trim());
		}
	};

	const handleConfirm = () => {
		if (username === "") {
			showSnackbar("Chưa nhập username", "error");
		} else {
			getOtp();
		}
	};

	const handleBack = () => {
		setShowInputOtp(false);
		setShowInputUsername(true);
	};

	const handleInputOtp = (e) => {
		if (e.target.value.trim().length === 0) {
			setOtp("");
			showSnackbar("Không được nhập khoảng trắng vào mã OTP", "error");
		} else {
			setOtp(e.target.value.trim());
		}
	};

	const handleCheckOtp = async () => {
		setLoading(true);
		if (otp === "") {
			showSnackbar("Chưa nhập mã OTP", "error");
		} else {
			try {
				const response = await API.post(endpoints["checkOtp"], {
					otp_code: otp,
					username: username,
				});

				if (response.status === 200) {
					showSnackbar("Thành công", "success");
					setShowInputOtp(false);
					setShowInputPassword(true);
				}
			} catch {
				showSnackbar("Mã OTP không trùng khớp", "error");
			}
		}
		setLoading(false);
	};

	const handleInputPassword = (e, func) => {
		if (e.target.value.trim().length === 0) {
			func("");
			showSnackbar("Không được nhập khoảng trắng vào password", "error");
		} else {
			func(e.target.value.trim());
		}
	};

	const handleReplacePassword = async () => {
		setLoading(true);
		if (password === "") {
			showSnackbar("Chưa nhập password", "error");
		} else if (password === requiredPassword) {
			try {
				const response = await API.patch(endpoints["replacePassword"], {
					username: username,
					password: password,
					otp_code: otp,
				});

				if (response.status === 200) {
					showSnackbar("Thay đổi mật khẩu thành công", "success");
					setTimeout(() => {
						navigate("/");
					}, 1000);
				}
			} catch {
				showSnackbar("Thay đổi mật khẩu thất bại", "error");
			}
		} else {
			showSnackbar("Nhập lại mật khẩu không trùng khớp", "error");
		}
		setLoading(false);
	};

	return (
		<>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			<h1 className="text text-center text-success mt-150">
				HỆ THỐNG QUẢN LÝ KHÓA LUẬN
			</h1>
			<div className="flex-box my-4">
				<div className="form-login">
					{showInputUsername && (
						<>
							<FloatingLabel
								controlId="floatingUsername"
								label="Username"
								className="mb-3">
								<Form.Control
									type="text"
									value={username}
									onChange={(e) => handleInputUsername(e)}
									placeholder="Tên đăng nhập"
								/>
							</FloatingLabel>
							<div className="d-flex justify-content-center">
								{loading ? (
									<>
										<CircularProgress className="mt-3" />
									</>
								) : (
									<>
										<Button variant="primary" onClick={handleConfirm}>
											Xác nhận
										</Button>
									</>
								)}
							</div>
						</>
					)}

					{showInputOtp && (
						<>
							<Form.Group className="mb-3" controlId="formOtp">
								<Form.Label>Nhập mã OTP</Form.Label>
								<Form.Control
									type="text"
									value={otp}
									placeholder="Nhập mã OTP"
									required
									onChange={(e) => handleInputOtp(e)}
								/>
							</Form.Group>
							<div
								className={`d-flex ${
									loading ? "justify-content-center" : "justify-content-between"
								}`}>
								{loading ? (
									<>
										<CircularProgress className="mt-3" />
									</>
								) : (
									<>
										<Button variant="primary" onClick={handleBack}>
											Back
										</Button>
										<Button
											variant="success"
											className="mx-4"
											onClick={handleCheckOtp}>
											Xác nhận
										</Button>
										<Button variant="info" onClick={getOtp}>
											Gửi lại mã
										</Button>
									</>
								)}
							</div>
						</>
					)}

					{showInputPassword && (
						<>
							<FloatingLabel
								controlId="floatingPassword"
								label="Password"
								className="mb-3">
								<Form.Control
									type="password"
									value={password}
									onChange={(e) => handleInputPassword(e, setPassword)}
									placeholder="Nhập mật khẩu mới"
									required
									disabled={loading}
								/>
							</FloatingLabel>
							<FloatingLabel
								controlId="floatingRequiredPassword"
								label="RequiredPassword"
								className="mb-3">
								<Form.Control
									type="password"
									value={requiredPassword}
									onChange={(e) => handleInputPassword(e, setRequiredPassword)}
									placeholder="Nhập lại mật khẩu"
									required
									disabled={loading}
								/>
							</FloatingLabel>
							<FloatingLabel className="flex-box">
								{loading ? (
									<>
										<CircularProgress className="mt-3" />
									</>
								) : (
									<>
										<Button
											onClick={handleReplacePassword}
											variant="primary"
											className="mb-3">
											Thay đổi password
										</Button>
									</>
								)}
							</FloatingLabel>
						</>
					)}
				</div>
			</div>
		</>
	);
}

export default ForgetPassword;
