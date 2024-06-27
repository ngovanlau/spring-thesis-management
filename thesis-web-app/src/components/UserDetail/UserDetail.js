import { useContext, useEffect, useRef, useState } from "react";
import {
	Button,
	Col,
	Container,
	Form,
	Image,
	InputGroup,
	Row,
	Stack,
} from "react-bootstrap";
import { LoadingContext, UserContext } from "../../configs/Context";
import { Link } from "react-router-dom";
import { authAPI, endpoints } from "../../configs/API";
import { CustomerSnackbar } from "../Common/Common";
import cookies from "react-cookies";

function UserDetail() {
	const [user, userDispatch] = useContext(UserContext);
	const [src, setSrc] = useState(null);
	const imageRef = useRef(null);
	const [, loadingDispatch] = useContext(LoadingContext);
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thay đổi ảnh đại diện thành công",
		severity: "success",
	});

	useEffect(() => {
		document.title = "Thông tin cá nhân";
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

	const change = (event) => {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();

			reader.readAsDataURL(file);

			reader.onload = (e) => {
				setSrc(e.target.result);
			};
		}
		setSrc(null);
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		loadingDispatch({ type: "loading" });

		try {
			let form = new FormData();
			form.append("avatar", imageRef.current.files[0]);

			const response = await authAPI().post(endpoints["changeAvatar"], form, {
				headers: {
					"Content-Type": "multipart/form-data",
				},
			});

			if (response.status === 200) {
				showSnackbar("Thay đổi ảnh đại diện thành công", "success");
				userDispatch({ type: "login", payload: response.data });
				cookies.save("user", response.data, { maxAge: 60 * 30 });
			}
		} catch {
			showSnackbar("Thay đổi ảnh đại diện thất bại", "error");
		}
		loadingDispatch({ type: "unloading" });
	};

	return (
		<>
			{user === null ? (
				<></>
			) : (
				<>
					<CustomerSnackbar
						open={open}
						message={data.message}
						severity={data.severity}
					/>

					<Stack>
						<h1 className="text-center text-success my-4">Thông tin cá nhân</h1>

						<div className="d-flex justify-content-center">
							<div>
								<Image
									src={src !== null ? src : user.user.avatar}
									width="200"
									height="200"
									alt="Ảnh đại diện"
									roundedCircle
									className="my-4 mx-auto"
								/>
							</div>

							<div className="d-flex align-items-center ms-4">
								<Form onSubmit={handleSubmit}>
									<Form.Group controlId="formFile" className="mb-3">
										<Form.Label>Chọn ảnh</Form.Label>
										<Form.Control
											type="file"
											accept=".jsp, .png"
											ref={imageRef}
											onChange={(e) => change(e)}
											required
										/>
									</Form.Group>

									<Button type="submit">Thay đổi ảnh đại diện</Button>
								</Form>
							</div>
						</div>

						<Form class="needs-validation">
							<Container fluid className="w-50 mx-auto my-4">
								<Row className="mb-3">
									<Col>
										<Form.Group controlId="lastName">
											<Form.Label>Họ</Form.Label>
											<Form.Control
												type="text"
												value={user.user.lastName}
												disabled
												required
											/>
										</Form.Group>
									</Col>

									<Col>
										<Form.Group controlId="firstName">
											<Form.Label>Tên</Form.Label>
											<Form.Control
												type="text"
												value={user.user.firstName}
												required
												disabled
											/>
										</Form.Group>
									</Col>
								</Row>

								<Row className="mb-3">
									<Form.Group controlId="universityId" className="col-sm-6">
										<Form.Label>Mã ID</Form.Label>
										<Form.Control
											type="number"
											value={user.user.useruniversityid}
											disabled
											required
										/>
									</Form.Group>

									<Form.Group controlId="birthday" className="col-sm-6">
										<Form.Label>Ngày sinh</Form.Label>
										<Form.Control
											type="text"
											value={new Date(user.user.birthday).toLocaleDateString()}
											disabled
											required
										/>
									</Form.Group>
								</Row>

								<Row className="mb-3">
									<Form.Group controlId="faculty" className="col-sm-6">
										<Form.Label>Khoa</Form.Label>
										<Form.Control
											type="text"
											value={user.faculty.name}
											required
											disabled
										/>
									</Form.Group>

									<Form.Group controlId="phone" className="col-sm-6">
										<Form.Label>Phone</Form.Label>
										<InputGroup>
											<InputGroup.Text>84</InputGroup.Text>
											<Form.Control
												type="number"
												value={user.user.phone.substring(1)}
												required
												disabled
											/>
										</InputGroup>
									</Form.Group>
								</Row>

								<Row className="mb-3">
									<Form.Group controlId="email" className="col-sm-6">
										<Form.Label>Email</Form.Label>
										<InputGroup>
											<InputGroup.Text>@</InputGroup.Text>
											<Form.Control
												type="text"
												value={user.user.email}
												required
												disabled
											/>
										</InputGroup>
									</Form.Group>
									<div className="col-sm-6 d-flex justify-content-end align-items-end">
										<Link to="/user-detail/change" className="btn btn-danger">
											Thay đổi password
										</Link>
									</div>
								</Row>
							</Container>
						</Form>
					</Stack>
				</>
			)}
		</>
	);
}

export default UserDetail;
