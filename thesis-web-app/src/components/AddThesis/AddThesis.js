import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { Button, FloatingLabel, Form, Stack } from "react-bootstrap";
import Select from "react-select";
import API, { endpoints } from "../../configs/API";
import { useNavigate } from "react-router-dom";
import { LoadingContext } from "../../configs/Context";
import { CustomerSnackbar } from "../Common/Common";

function AddThesis() {
	const [, loadingDispatch] = useContext(LoadingContext);
	const [name, setName] = useState("");
	const [lecturers, setLecturers] = useState([]);
	const [students, setStudents] = useState([]);
	const studentSelect = useRef();
	const lecturerSelect = useRef();
	const navigate = useNavigate();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thêm khóa luận thành công",
		severity: "success",
	});

	const loadStudents = useCallback(async () => {
		const response = await API.get(endpoints["students"]);

		setStudents(
			response.data.map((s) => {
				return {
					value: s.id,
					label: `${s.lastName} ${s.firstName} - ${s.useruniversityid}`,
				};
			})
		);
	}, []);

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
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Thêm khóa luận";
		loadStudents();
		loadLecturers();
		loadingDispatch({ type: "unloading" });
	}, [loadStudents, loadLecturers, loadingDispatch]);

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

	const isOptionSelected = (_, selectValue) => {
		return selectValue.length > 1;
	};

	const addThesis = async (event) => {
		event.preventDefault();
		loadingDispatch({ type: "loading" });
		try {
			let users = [];
			lecturerSelect.current.props.value.forEach((i) => users.push(i.value));
			studentSelect.current.props.value.forEach((i) => users.push(i.value));

			let response = await API.post(endpoints["theses"], {
				name: name,
				userIds: users,
			});

			if (response.status === 201) {
				showSnackbar("Thêm khóa luận thành công", "success");
				setTimeout(() => {
					navigate("/");
				}, 1000);
				loadStudents();
			}
		} catch {
			showSnackbar("Thêm khóa luận thất bại", "error");
		}
		loadingDispatch({ type: "unloading" });
	};

	return (
		<>
			<CustomerSnackbar
				open={open}
				message={data.message}
				severity={data.severity}
			/>

			<h1 className="text-success text-center my-3">THÊM KHÓA LUẬN</h1>

			<Stack>
				<Form className="w-50 mx-auto" onSubmit={addThesis}>
					<FloatingLabel
						controlId="floatingInput"
						label="Tên khóa luận"
						className="mb-3">
						<Form.Control
							type="text"
							placeholder="Nhập tên khóa luận"
							className="mb-3"
							onChange={(e) => setName(e.target.value)}
							required
						/>
					</FloatingLabel>

					<Form.Group className="mb-3">
						<Form.Label>Danh sách sinh viên</Form.Label>
						<Select
							isMulti
							name="students"
							options={students}
							className="basic-multi-select fs-6 mb-3"
							classNamePrefix="select"
							isOptionSelected={isOptionSelected}
							isSearchable={true}
							placeholder="Chọn sinh viên"
							ref={studentSelect}
							required
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Danh sách giảng viên</Form.Label>
						<Select
							isMulti
							name="lecturers"
							options={lecturers}
							className="basic-multi-select fs-6 mb-3"
							classNamePrefix="select"
							isOptionSelected={isOptionSelected}
							isSearchable={true}
							placeholder="Chọn giảng viên"
							ref={lecturerSelect}
							required
						/>
					</Form.Group>

					<FloatingLabel>
						<Button type="submit" variant="primary" className="mb-3 fs-6">
							Thêm khóa luận
						</Button>
					</FloatingLabel>
				</Form>
			</Stack>
		</>
	);
}

export default AddThesis;
