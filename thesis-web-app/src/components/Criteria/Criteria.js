import { useCallback, useContext, useEffect, useState } from "react";
import API, { authAPI, endpoints } from "../../configs/API";
import { Button, FloatingLabel, Form, Table } from "react-bootstrap";
import { Alert } from "@mui/material";
import { LoadingContext } from "../../configs/Context";
import { CustomerSnackbar } from "../Common/Common";

function Criteria() {
	const [loading, loadingDispatch] = useContext(LoadingContext);
	const [criteria, setCriteria] = useState([]);
	const [hidden, setHidden] = useState(true);
	const [name, setName] = useState(null);
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thêm tiêu chí thành công",
		severity: "success",
	});

	const loadCriteria = useCallback(async () => {
		const response = await API.get(endpoints["criteria"]);

		setCriteria(response.data);
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Tiêu chí";
		loadCriteria();
		loadingDispatch({ type: "unloading" });
	}, [loadCriteria, loadingDispatch]);

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

	const changeHidden = () => {
		setHidden(!hidden);
	};

	const addCriteria = async () => {
		loadingDispatch({ type: "loading" });
		if (name === null || name.trim().length === 0) {
			showSnackbar("Chưa nhập tên tiêu chí", "error");
		} else {
			try {
				const response = await authAPI().post(endpoints["criteria"], {
					name: name,
				});

				if (response.status === 201) {
					showSnackbar("Thêm tiêu chí thành công", "success");
					setName("");
					setHidden(true);
					setCriteria(response.data);
				}
			} catch {
				showSnackbar("Thêm tiêu chí thất bại", "error");
			}
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

			<div className="my-4">
				<div hidden={hidden} className="criteria-item my-4 w-100">
					<FloatingLabel
						controlId="floatingInput"
						label="Tên tiêu chí"
						className="mb-3">
						<Form.Control
							type="text"
							placeholder="Nhập tên khóa luận"
							className="mb-3"
							value={name}
							onChange={(e) => setName(e.target.value)}
						/>
					</FloatingLabel>

					<Button variant="success" onClick={addCriteria}>
						Thêm
					</Button>
				</div>

				<Button variant={hidden ? "primary" : "danger"} onClick={changeHidden}>
					{hidden ? "Thêm tiêu chí" : "Ẩn thêm tiêu chí"}
				</Button>
			</div>

			<div className="my-4">
				{criteria.length < 1 ? (
					<>
						{loading && (
							<Alert variant="filled" severity="info" className="w-50 mx-auto">
								Hiện không có tiêu chí
							</Alert>
						)}
					</>
				) : (
					<>
						<Table striped hover>
							<thead>
								<tr>
									<th>ID</th>
									<th>Tên tiêu chí</th>
								</tr>
							</thead>
							<tbody>
								{criteria.map((c) => (
									<tr>
										<td>{c.id}</td>
										<td>{c.name}</td>
									</tr>
								))}
							</tbody>
						</Table>
					</>
				)}
			</div>
		</>
	);
}

export default Criteria;
