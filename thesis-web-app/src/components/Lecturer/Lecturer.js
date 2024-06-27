import { useCallback, useContext, useEffect, useState } from "react";
import "./Lecturer.css";
import { Image, Table } from "react-bootstrap";
import API, { endpoints } from "../../configs/API";
import { Alert } from "@mui/material";
import { LoadingContext } from "../../configs/Context";

function Lecturer() {
	const [loading, loadingDispatch] = useContext(LoadingContext);
	const [lecturers, setLecturers] = useState([]);

	const loadLecturers = useCallback(async () => {
		let response = await API.get(endpoints["lecturers"]);

		setLecturers(response.data);
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Giảng viên";
		loadLecturers();
		loadingDispatch({ type: "unloading" });
	}, [loadLecturers, loadingDispatch]);

	return (
		<div className="my-4">
			{lecturers.length < 1 ? (
				<>
					{loading && (
						<Alert variant="filled" severity="info" className="w-50 mx-auto">
							Hiện không có giảng viên
						</Alert>
					)}
				</>
			) : (
				<>
					<Table striped hover>
						<thead>
							<tr>
								<th></th>
								<th>ID</th>
								<th>Họ</th>
								<th>Tên</th>
								<th>Email</th>
								<th>Số điện thoại</th>
								<th>Giới tính</th>
								<th>Ngày sinh</th>
							</tr>
						</thead>
						<tbody>
							{lecturers.map((lecturer) => (
								<tr key={lecturer.id}>
									<td>
										<Image
											src={lecturer.avatar}
											width={70}
											height={70}
											roundedCircle
										/>
									</td>
									<td>{lecturer.useruniversityid}</td>
									<td>{lecturer.lastName}</td>
									<td>{lecturer.firstName}</td>
									<td>{lecturer.email}</td>
									<td>{lecturer.phone}</td>
									<td>{lecturer.gender === "male" ? "Nam" : "Nữ"}</td>
									<td>{new Date(lecturer.birthday).toLocaleDateString()}</td>
								</tr>
							))}
						</tbody>
					</Table>
				</>
			)}
		</div>
	);
}

export default Lecturer;
