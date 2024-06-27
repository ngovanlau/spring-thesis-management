import { useCallback, useContext, useEffect, useState } from "react";
import API, { endpoints } from "../../configs/API";
import { Image, Table } from "react-bootstrap";
import { Alert } from "@mui/material";
import { LoadingContext } from "../../configs/Context";

function Student() {
	const [loading, loadingDispatch] = useContext(LoadingContext);
	const [students, setStudents] = useState([]);

	const loadStudent = useCallback(async () => {
		const response = await API.get(endpoints["students"]);

		setStudents(response.data);
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Sinh viên";
		loadStudent();
		loadingDispatch({ type: "unloading" });
	}, [loadStudent, loadingDispatch]);

	return (
		<div className="my-4">
			{students.length < 1 ? (
				<>
					{loading && (
						<Alert variant="filled" severity="info" className="w-50 mx-auto">
							Hiện không có sinh viên
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
							{students.map((student) => (
								<tr key={student.id}>
									<td>
										<Image
											src={student.avatar}
											width={70}
											height={70}
											roundedCircle
										/>
									</td>
									<td>{student.useruniversityid}</td>
									<td>{student.lastName}</td>
									<td>{student.firstName}</td>
									<td>{student.email}</td>
									<td>{student.phone}</td>
									<td>{student.gender === "male" ? "Nam" : "Nữ"}</td>
									<td>{new Date(student.birthday).toLocaleDateString()}</td>
								</tr>
							))}
						</tbody>
					</Table>
				</>
			)}
		</div>
	);
}

export default Student;
