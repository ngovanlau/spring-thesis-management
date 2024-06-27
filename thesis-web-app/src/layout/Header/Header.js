import {
	Button,
	Container,
	Dropdown,
	Form,
	Image,
	Nav,
	NavDropdown,
	Navbar,
} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import "../Header/Header.css";
import { useContext, useState } from "react";
import { LoadingContext, UserContext } from "../../configs/Context";
import { isAcademicManager, isLecturer } from "../../components/Common/Common";
import { LinearProgress } from "@mui/material";

function Header() {
	const [user, userDispatch] = useContext(UserContext);
	const [loading, loadingDispatch] = useContext(LoadingContext);
	const navigate = useNavigate();
	const [search, setSearch] = useState();

	const logout = () => {
		loadingDispatch({ type: "loading" });
		if (user !== null) {
			userDispatch({ type: "logout" });
		}
		loadingDispatch({ type: "unloading" });

		navigate("/");
	};

	const handleSearch = (event) => {
		event.preventDefault();
		navigate(`/?search=${search}`);
	};

	return (
		<>
			<Navbar expand="lg" className="bg-body-tertiary header">
				<Container>
					<Link to="/" className="navbar-brand">
						QUẢN LÝ KHÓA LUẬN
					</Link>
					<Navbar.Collapse id="navbarScroll">
						<Nav
							className="me-auto m-2 my-lg-0"
							style={{ maxHeight: "100px" }}
							navbarScroll>
							<Link to="/" className="nav-link">
								Trang chủ
							</Link>

							{(isAcademicManager(user) || isLecturer(user)) && (
								<Link to="/committees" className="nav-link">
									Hội đồng
								</Link>
							)}

							{isAcademicManager(user) && (
								<>
									<Link to="/lecturers" className="nav-link">
										Giảng viên
									</Link>

									<Link to="/students" className="nav-link">
										Sinh viên
									</Link>

									<Link to="/criteria" className="nav-link">
										Tiêu chí
									</Link>
								</>
							)}
						</Nav>

						<Form className="d-flex" onSubmit={handleSearch}>
							<Form.Control
								type="search"
								placeholder="Tìm kiếm"
								className="me-2"
								aria-label="Search"
								value={search}
								onChange={(e) => setSearch(e.target.value)}
							/>
							<Button variant="outline-success" type="submit">
								Search
							</Button>
						</Form>

						{user === null ? (
							<>
								<div className="ms-2">
									<Link to="/login" className="btn btn-primary me-2">
										Đăng nhập
									</Link>
								</div>
							</>
						) : (
							<>
								<Dropdown className="dropdown text-end ms-4">
									<Dropdown.Toggle className="header-btn-primary">
										<Image
											src={user === null ? "" : user.user.avatar}
											alt="mdo"
											width="50"
											height="50"
											className="rounded-circle"
										/>
									</Dropdown.Toggle>
									<Dropdown.Menu className="r-0 header-dropdown-menu">
										<Dropdown.Item>
											<Link className="dropdown-item" to="user-detail">
												Thông tin cá nhân
											</Link>
										</Dropdown.Item>

										<Dropdown.Item>
											<Link className="dropdown-item" to="chat-box">
												Chat
											</Link>
										</Dropdown.Item>

										<NavDropdown.Divider />

										<NavDropdown.Item>
											<Button className="dropdown-item" onClick={logout}>
												Đăng xuất
											</Button>
										</NavDropdown.Item>
									</Dropdown.Menu>
								</Dropdown>
							</>
						)}
					</Navbar.Collapse>
				</Container>
			</Navbar>
			<LinearProgress hidden={!loading} />
		</>
	);
}

export default Header;
