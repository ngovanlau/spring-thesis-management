import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { LoadingContext, UserContext } from "../../configs/Context";
import { Accordion, Col, Row } from "react-bootstrap";
import "./ChatBox.css";
import "../Common/Common.css";
import { Avatar, Button, TextField } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import API, { endpoints } from "../../configs/API";
import {
	addDoc,
	collection,
	onSnapshot,
	query,
	serverTimestamp,
	where,
} from "firebase/firestore";
import { db } from "../../configs/firebase";

function ChatBox() {
	const [user] = useContext(UserContext);
	const [, loadingDispatch] = useContext(LoadingContext);
	const [receivers, setReceivers] = useState({
		academicManagers: [],
		lecturers: [],
		students: [],
	});
	const [receiver, setReceiver] = useState(null);
	const [message, setMessage] = useState("");
	const [messages, setMessages] = useState([]);
	const [collection1, setCollection1] = useState([]);
	const [collection2, setCollection2] = useState([]);
	const scroll = useRef();
	const [hidden, setHidden] = useState(true);

	const loadUsers = useCallback(async () => {
		const response = await API.get(endpoints["users"]);
		if (response.status === 200) {
			switch (user.role.name) {
				case "ROLE_GIAOVU":
					setReceivers({
						...response.data,
						academicManagers: response.data.academicManagers.filter(
							(u) => u.userUniversityId !== user.user.useruniversityid
						),
					});
					break;
				case "ROLE_GIANGVIEN":
					setReceivers({
						...response.data,
						lecturers: response.data.lecturers.filter(
							(u) => u.userUniversityId !== user.user.useruniversityid
						),
					});
					break;
				default:
					setReceivers({
						...response.data,
						students: response.data.students.filter(
							(u) => u.userUniversityId !== user.user.useruniversityid
						),
					});
					break;
			}
		}
	}, [user]);

	useEffect(() => {
		document.title = "Chat";
		loadUsers();
	}, [loadUsers]);

	useEffect(() => {
		if (receiver === null) return;

		const q1 = query(
			collection(
				db,
				`${user.user.useruniversityid}_${receiver.userUniversityId}`
			),
			where("userUniversityId", "==", user.user.useruniversityid)
		);

		const unsubscribe1 = onSnapshot(q1, (querySnapshot) => {
			const fetchedMessages = [];
			querySnapshot.forEach((doc) => {
				fetchedMessages.push({ ...doc.data(), id: doc.id });
			});
			setCollection1(fetchedMessages);
		});

		const q2 = query(
			collection(
				db,
				`${receiver.userUniversityId}_${user.user.useruniversityid}`
			),
			where("userUniversityId", "==", receiver.userUniversityId)
		);

		const unsubscribe2 = onSnapshot(q2, (querySnapshot) => {
			const fetchedMessages = [];
			querySnapshot.forEach((doc) => {
				fetchedMessages.push({ ...doc.data(), id: doc.id });
			});
			setCollection2(fetchedMessages);
		});

		return () => {
			unsubscribe1();
			unsubscribe2();
			setMessages([]);
		};
	}, [receiver, user]);

	useEffect(() => {
		const fetchedMessages = [...collection1, ...collection2];
		const sortedMessages = fetchedMessages.sort(
			(a, b) => a.createdDate - b.createdDate
		);
		setMessages(sortedMessages);
		loadingDispatch({ type: "unloading" });
	}, [collection1, collection2, loadingDispatch]);

	useEffect(() => {
		scroll.current.scrollTo({
			top: scroll.current.scrollHeight,
		});
	}, [messages]);

	const getReceiver = (fullName, userUniversity, avatar) => {
		loadingDispatch({ type: "loading" });
		setReceiver({
			fullName: fullName,
			userUniversityId: userUniversity,
			avatar: avatar,
		});
		setHidden(false);
	};

	const sendMessage = async () => {
		if (message.trim() === "") {
			return;
		}

		await addDoc(
			collection(
				db,
				`${user.user.useruniversityid}_${receiver.userUniversityId}`
			),
			{
				fullName: user.user.lastName + " " + user.user.firstName,
				userUniversityId: user.user.useruniversityid,
				avatar: user.user.avatar,
				message: message,
				createdDate: serverTimestamp(),
			}
		);

		setMessage("");
		scroll.current.scrollTo({
			top: scroll.current.scrollHeight,
		});
	};

	return (
		<>
			<Row className="h-chatbox box-shadow chat-box m-4 p-4">
				<Col md={3} className="h-100">
					<div className="py-2 px-3 box-shadow br d-flex">
						<Avatar alt="Hình ảnh" src={user.user.avatar} />
						<div className="d-flex align-self-center ms-3 fs-5">
							{user.user.lastName + " " + user.user.firstName}
						</div>
					</div>

					{receivers && (
						<Accordion className="mt-4">
							<Accordion.Item eventKey="0">
								<Accordion.Header>Giáo vụ</Accordion.Header>
								<Accordion.Body className="list">
									{receivers.academicManagers.map((u) => (
										<div key={u.userUniversityId} className="py-2">
											<div
												className="receiver box-shadow border-radius p-2 d-flex"
												id={u.userUniversityId}
												onClick={() => {
													getReceiver(u.fullName, u.userUniversityId, u.avatar);
													window.location.hash = u.userUniversityId;
												}}>
												<Avatar alt="Remy Sharp" src={u.avatar} />
												<div className="d-flex align-self-center ms-3 fs-5">
													{u.fullName}
												</div>
											</div>
										</div>
									))}
								</Accordion.Body>
							</Accordion.Item>
							<Accordion.Item eventKey="1">
								<Accordion.Header>Giảng viên</Accordion.Header>
								<Accordion.Body className="list">
									{receivers.lecturers.map((u) => (
										<div key={u.userUniversityId} className="py-2">
											<div
												className="receiver box-shadow border-radius p-2 d-flex"
												id={u.userUniversityId}
												onClick={() => {
													getReceiver(u.fullName, u.userUniversityId, u.avatar);
													window.location.hash = u.userUniversityId;
												}}>
												<Avatar alt="Hình ảnh" src={u.avatar} />
												<div className="d-flex align-self-center ms-3 fs-5">
													{u.fullName}
												</div>
											</div>
										</div>
									))}
								</Accordion.Body>
							</Accordion.Item>
							<Accordion.Item eventKey="2">
								<Accordion.Header>Sinh viên</Accordion.Header>
								<Accordion.Body className="list">
									{receivers.students.map((u) => (
										<div key={u.userUniversityId} className="py-2">
											<div
												className="receiver box-shadow border-radius p-2 d-flex"
												id={u.userUniversityId}
												onClick={() => {
													getReceiver(u.fullName, u.userUniversityId, u.avatar);
													window.location.hash = u.userUniversityId;
												}}>
												<Avatar alt="Hình ảnh" src={u.avatar} />
												<div className="d-flex align-self-center ms-3 fs-5">
													{u.fullName}
												</div>
											</div>
										</div>
									))}
								</Accordion.Body>
							</Accordion.Item>
						</Accordion>
					)}
				</Col>

				<Col md={9} className="d-flex flex-column chat justify-content-between">
					{receiver && (
						<div className="header p-2 w-100 box-shadow d-flex">
							<Avatar alt="Remy Sharp" src={receiver.avatar} />
							<div className="d-flex align-self-center ms-3 fs-5">
								{receiver.fullName}
							</div>
						</div>
					)}

					<div className="content p-2 my-4" ref={scroll}>
						{messages.map((m) => (
							<div
								key={Math.random()}
								className={`d-flex my-4 ${
									m.userUniversityId === user.user.useruniversityid
										? "flex-row-reverse"
										: ""
								}`}>
								<Avatar className="mt-auto" alt={m.fullName} src={m.avatar} />
								<div
									className={`box-shadow p-2 br d-flex align-self-center ${
										m.userUniversityId === user.user.useruniversityid
											? "message-left me-2"
											: "message-right ms-2"
									}`}>
									{m.message}
								</div>
							</div>
						))}
					</div>

					<Row className="footer" hidden={hidden}>
						<Col md={10}>
							<TextField
								id="outlined-basic"
								label="Chat"
								variant="outlined"
								size="small"
								className="w-100"
								value={message}
								onChange={(e) => setMessage(e.target.value)}
							/>
						</Col>
						<Col md={2} className="d-flex">
							<Button
								variant="contained"
								className="w-100"
								onClick={() => sendMessage()}
								endIcon={<SendIcon />}>
								Send
							</Button>
						</Col>
					</Row>
				</Col>
			</Row>
		</>
	);
}

export default ChatBox;
