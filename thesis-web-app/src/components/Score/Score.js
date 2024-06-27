import { useCallback, useContext, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import API, { authAPI, endpoints } from "../../configs/API";
import { Button, Form, InputGroup } from "react-bootstrap";
import "../Score/Score.css";
import { LoadingContext, UserContext } from "../../configs/Context";
import { CustomerSnackbar } from "../Common/Common";

function Score() {
	const [user] = useContext(UserContext);
	const { thesisId } = useParams();
	const [thesis, setThesis] = useState(null);
	const [criteria, setCriteria] = useState([]);
	const [scores, setScores] = useState();
	const [, loadingDispatch] = useContext(LoadingContext);
	const [isScoring, setIsScoring] = useState(false);
	const navigate = useNavigate();
	const [open, setOpen] = useState(false);
	const [data, setData] = useState({
		message: "Thành công",
		severity: "success",
	});

	const loadThesis = useCallback(async () => {
		const response = await API.get(endpoints["thesisDetail"](thesisId));
		setThesis(response.data);

		setIsScoring(
			response.data.scores.find((s) => s.userId === user.user.id) ? true : false
		);

		if (
			response.data !== null &&
			response.data.scores.find((s) => s.userId === user.user.id)
		) {
			response.data.scores
				.filter((s) => s.userId === user.user.id)
				.forEach((i) => {
					setScores((current) => {
						return { ...current, [i.criteriaId]: i.score };
					});
				});
		}
	}, [thesisId, user]);

	const loadCriteria = useCallback(async () => {
		const response = await API.get(endpoints["criteria"]);
		setCriteria(response.data);

		response.data.forEach((c) => {
			setScores((current) => {
				return { ...current, [c.id]: parseFloat(0) };
			});
		});
	}, []);

	useEffect(() => {
		loadingDispatch({ type: "loading" });
		document.title = "Chấm điểm";
		loadCriteria();
		loadThesis();

		loadingDispatch({ type: "unloading" });
	}, [loadThesis, loadCriteria, loadingDispatch]);

	const handleChange = (id, e) => {
		if (parseFloat(e.target.value) > 10 || parseFloat(e.target.value) < 0) {
			e.target.value = "";
			setScores((current) => {
				return { ...current, [id]: parseFloat(0) };
			});
		} else {
			if (e.target.value === "") {
				setScores((current) => {
					return { ...current, [id]: parseFloat(0) };
				});
			} else {
				setScores((current) => {
					return { ...current, [id]: parseFloat(e.target.value) };
				});
			}
		}
	};

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

	const handleScore = async () => {
		loadingDispatch({ type: "loading" });
		try {
			const score = {
				thesisId: thesisId,
				committeeId: thesis.committee.id,
				scores: Object.keys(scores).map((key) => ({
					criteriaId: key,
					score: scores[key],
				})),
			};

			const response = await authAPI().post(endpoints["score"], score);

			if (response.status === 201) {
				showSnackbar("Thành công", "success");

				setTimeout(() => {
					navigate(`/theses/${thesis.thesis.id}`);
				}, 1000);
			}
		} catch {
			showSnackbar("Thất bại", "error");
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

			{thesis ? (
				<>
					<div className="w-75 thesis-item my-4 mx-auto">
						<div>
							<h2>BẢNG ĐIỂM</h2>
						</div>

						<Form>
							{criteria.map((c) => (
								<Form.Group key={c.id} className="mb-3 thesis-item my-4 w-100">
									<Form.Label>Tiêu chí: {c.name}</Form.Label>

									<InputGroup>
										<InputGroup.Text id="basic-addon1">Điểm</InputGroup.Text>
										<Form.Control
											type="number"
											defaultValue={isScoring ? scores[c.id] : ""}
											max={10}
											min={0}
											placeholder="Điểm"
											onChange={(e) => handleChange(c.id, e)}
										/>
									</InputGroup>
								</Form.Group>
							))}
						</Form>

						<div className="mt-4">
							<Button onClick={handleScore} variant="success">
								Chấm điểm
							</Button>

							<Link
								to={`/theses/${thesis.thesis.id}`}
								className="ms-3 btn btn-danger">
								Ẩn chấm điểm
							</Link>
						</div>
					</div>
				</>
			) : (
				<></>
			)}
		</>
	);
}

export default Score;
