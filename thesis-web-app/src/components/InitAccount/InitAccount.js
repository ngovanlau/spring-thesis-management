import { useContext, useEffect, useRef, useState } from "react";
import { Button, FloatingLabel, Form, Image } from "react-bootstrap";
import { authAPI, endpoints } from "../../configs/API";
import { UserContext } from "../../configs/Context";
import "../Common/Common.css";
import { useNavigate } from "react-router-dom";
import { CircularProgress } from "@mui/material";
import { CustomerSnackbar } from "../Common/Common";
import cookies from "react-cookies";

function InitAccount() {
	const [user, userDispatch] = useContext(UserContext);
	const [password, setPassword] = useState();
	const [requiredPassword, setRequiredPassword] = useState();
	const [avatar, setAvatar] = useState(null);
	const avatarRef = useRef();
	const navigate = useNavigate();
	const [loading, setLoading] = useState(false);
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Kích hoạt tài khoản thành công",
		severity: "success",
	});

	useEffect(() => {
		document.title = "Kích hoạt tài khoản";
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

	const handleChange = (event) => {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = (e) => {
				setAvatar(e.target.result);
			};
		}
		setAvatar(null);
	};

	const handleInit = async (event) => {
		event.preventDefault();
		setLoading(true);

		if (password === requiredPassword) {
			let form = new FormData();
			form.append("password", password);

			const avatarFile = avatarRef.current.files[0];
			if (avatarFile) {
				form.append("avatar", avatarFile);
			}

			let response = await authAPI().post(endpoints["initAccount"], form, {
				headers: {
					"Content-Type": "multipart/form-data",
				},
			});

			if (response.status === 200) {
				showSnackbar("Kích hoạt tài khoản thành công", "success");

				userDispatch({ type: "login", payload: response.data });
				cookies.save("user", response.data, { maxAge: 60 * 30 });

				setPassword(null);
				setRequiredPassword(null);

				setTimeout(() => {
					navigate("/");
				}, 1000);
			}
		} else {
			showSnackbar("Mật khẩu không khớp", "error");
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

			<div className="w-50 box-shadow border-radius mx-auto my-5 p-5">
				<h1 className="text text-center text-primary">KÍCH HOẠT TÀI KHOẢN</h1>

				<div className="d-flex justify-content-center">
					<div>
						<Image
							src={avatar !== null ? avatar : user.user.avatar}
							width="200"
							height="200"
							alt="Ảnh đại diện"
							roundedCircle
							className="my-4 mx-auto"
							ref={avatarRef}
						/>
					</div>

					<div className="d-flex align-items-center ms-4">
						<Form.Group controlId="formFile" className="mb-3">
							<Form.Label>Chọn ảnh</Form.Label>
							<Form.Control
								type="file"
								accept=".jsp, .png"
								ref={avatarRef}
								onChange={(e) => handleChange(e)}
								required
							/>
						</Form.Group>
					</div>
				</div>

				<div className="mx-auto w-50">
					<Form onSubmit={handleInit}>
						<FloatingLabel
							controlId="password"
							label="Nhập mật khẩu mới"
							className="mb-3">
							<Form.Control
								type="password"
								placeholder="Nhập mật khẩu mới"
								required
								onChange={(e) => setPassword(e.target.value)}
							/>
						</FloatingLabel>

						<FloatingLabel
							controlId="requiredPassword"
							label="Nhập lại mật khẩu mới"
							className="mb-3">
							<Form.Control
								type="password"
								placeholder="Nhập lại mật khẩu mới"
								required
								onChange={(e) => setRequiredPassword(e.target.value)}
							/>
						</FloatingLabel>

						<Form.Group className="mb-3 d-flex justify-content-center">
							{loading ? (
								<>
									<CircularProgress />
								</>
							) : (
								<>
									<Button variant="info" type="submit" className="mt-3">
										Kích hoạt tài khoản
									</Button>
								</>
							)}
						</Form.Group>
					</Form>
				</div>
			</div>
		</>
	);
}

export default InitAccount;
