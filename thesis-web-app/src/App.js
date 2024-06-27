import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Container } from "react-bootstrap";
import Header from "./layout/Header/Header";
import Footer from "./layout/Footer/Footer";
import UserDetail from "./components/UserDetail/UserDetail";
import AddThesis from "./components/AddThesis/AddThesis";
import AddCommittee from "./components/AddCommittee/AddCommittee";
import InitAccount from "./components/InitAccount/InitAccount";
import Login from "./components/Login/Login";
import Thesis from "./components/Thesis/Thesis";
import Committee from "./components/Committee/Committee";
import Lecturer from "./components/Lecturer/Lecturer";
import Student from "./components/Student/Student";
import ThesisDetail from "./components/ThesisDetail/ThesisDetail";
import Criteria from "./components/Criteria/Criteria";
import Score from "./components/Score/Score";
import Payment from "./components/Payment/Payment";
import { useEffect, useReducer } from "react";
import { LoadingReducer, UserReducer } from "./configs/Reducer";
import { LoadingContext, UserContext } from "./configs/Context";
import {
	isAcademicManager,
	isAccountInit,
	isLecturer,
	isStudent,
} from "./components/Common/Common";
import cookies from "react-cookies";
import ChangePassword from "./components/ChangePassword/ChangePassword";
import ForgetPassword from "./components/ForgetPassword/ForgetPassword";
import ChatBox from "./components/ChatBox/ChatBox";

function App() {
	const [user, userDispatch] = useReducer(UserReducer, null);
	const [loading, loadingDispatch] = useReducer(LoadingReducer, false);

	useEffect(() => {
		if (cookies.load("user")) {
			userDispatch({ type: "login", payload: cookies.load("user") });
		}
	}, []);

	return (
		<BrowserRouter>
			<UserContext.Provider value={[user, userDispatch]}>
				<LoadingContext.Provider value={[loading, loadingDispatch]}>
					{user === null ? (
						<>
							<Container>
								<Routes>
									<Route path="/*" element={<Login />} />
									<Route path="/forget-password" element={<ForgetPassword />} />
								</Routes>
							</Container>
						</>
					) : (
						<>
							{isAccountInit(user) ? (
								<>
									<Header />
									<Container>
										<Routes>
											<Route path="/" element={<Thesis />} />
											<Route
												path="/theses/:thesisId"
												element={<ThesisDetail />}
											/>
											<Route path="/init-account" element={<InitAccount />} />
											<Route path="/user-detail" element={<UserDetail />} />
											<Route
												path="/user-detail/change"
												element={<ChangePassword />}
											/>

											<Route path="/chat-box" element={<ChatBox />} />

											{isAcademicManager(user) && (
												<>
													<Route path="/add-thesis" element={<AddThesis />} />
													<Route path="/committees" element={<Committee />} />
													<Route
														path="/add-committee"
														element={<AddCommittee />}
													/>
													<Route path="/lecturers" element={<Lecturer />} />
													<Route path="/students" element={<Student />} />
													<Route path="/criteria" element={<Criteria />} />
												</>
											)}

											{isLecturer(user) && (
												<>
													<Route
														path="/theses/:thesisId"
														element={<ThesisDetail />}>
														<Route path="score" element={<Score />} />
													</Route>

													<Route path="/committees" element={<Committee />} />
												</>
											)}

											{isStudent(user) && (
												<>
													<Route
														path="/theses/:thesisId/"
														element={<ThesisDetail />}>
														<Route
															path="payment/:orderId"
															element={<Payment />}
														/>
													</Route>
												</>
											)}
										</Routes>
									</Container>
									<Footer />
								</>
							) : (
								<>
									<Container>
										<Routes>
											<Route path="/*" element={<InitAccount />} />
										</Routes>
									</Container>
								</>
							)}
						</>
					)}
				</LoadingContext.Provider>
			</UserContext.Provider>
		</BrowserRouter>
	);
}

export default App;
