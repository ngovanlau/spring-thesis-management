import { Button, FloatingLabel, Form } from "react-bootstrap";
import "../Login/Login.css";
import { Link, useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import API, { authAPI, endpoints } from "../../configs/API";
import { UserContext } from "../../configs/Context";
import cookies from "react-cookies";
import { CircularProgress } from "@mui/material";
import { CustomerSnackbar } from "../Common/Common";

function Login() {
	const [username, setUserName] = useState();
	const [password, setPassword] = useState();
	const [loading, setLoading] = useState(false);
	const [, userDispatch] = useContext(UserContext);
	const navigate = useNavigate();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Đăng nhập thành công",
		severity: "success",
	});

	useEffect(() => {
		document.title = "Đăng nhập";
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

	const login = async (event) => {
		event.preventDefault();
		setLoading(true);
		try {
			const response = await API.post(endpoints["login"], {
				username: username,
				password: password,
			});

			if (response.status === 200) {
				cookies.save("token", response.data, { maxAge: 60 * 30 });
			}

			setTimeout(async () => {
				const response = await authAPI().get(endpoints["currentUser"]);

				if (response.status === 200) {
					showSnackbar("Đăng nhập thành công", "success");

					userDispatch({ type: "login", payload: response.data });
					cookies.save("user", response.data, { maxAge: 60 * 30 });

					setUserName(null);
					setPassword(null);

					setTimeout(() => {
						navigate("/");
					}, 1000);
				}
			}, 200);
		} catch {
			showSnackbar("Tài khoản hoặc mật khẩu không đúng!", "error");
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
					<h1 className="text-center text-primary mb-3">ĐĂNG NHẬP</h1>
					<Form onSubmit={login}>
						<FloatingLabel
							controlId="floatingInput"
							label="Tên đăng nhập"
							className="mb-3">
							<Form.Control
								type="text"
								onChange={(e) => setUserName(e.target.value)}
								placeholder="Tên đăng nhập"
								required
								disabled={loading}
							/>
						</FloatingLabel>
						<FloatingLabel
							controlId="floatingPassword"
							label="Password"
							className="mb-3">
							<Form.Control
								type="password"
								onChange={(e) => setPassword(e.target.value)}
								placeholder="Mật khẩu"
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
									<Button type="submit" variant="primary" className="mb-3 fs-5">
										Đăng nhập
									</Button>

									<Link to="/forget-password">Forget password?</Link>
								</>
							)}
						</FloatingLabel>
					</Form>
				</div>
			</div>
		</>
	);
}

export default Login;
