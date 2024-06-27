import axios from "axios";
import cookies from "react-cookies";

const BASE_URL = process.env.REACT_APP_BASE_URL;

export let endpoints = {
	initAccount: "/api/users/init-account/",
	lecturers: `/api/users/lecturers/`,
	students: `/api/users/students/`,
	getTwoRoleList: `/api/users/role/get2RoleList/`,
	theses: `/api/theses/`,
	committees: `/api/committees/`,
	thesisDetail: (thesisId) => `/api/theses/${thesisId}/`,
	activeCommittees: `/api/committees/active/`,
	addOrUpdateCommitteeForThesis: `/api/theses/committee/`,
	noneThesisStudents: `/api/users/students/noneThesis/`,
	criteria: `/api/criteria/`,
	login: "/api/users/login/",
	users: "/api/users/",
	currentUser: "/api/users/current-user/",
	thesisOfUser: "/api/users/theses/",
	committeesOfUser: "/api/users/committees/",
	thesesOfLecturer: "/api/users/lecturer/theses/",
	score: "/api/theses/scores/",
	committeeDetail: (committeeId) => `/api/committees/${committeeId}/`,
	printPDF: "/api/pdf/generate/",
	payment: "/api/payment/",
	checkPayment: (orderId) => `/api/payment/check-payment/${orderId}/`,
	changeAvatar: "/api/users/avatar/",
	changePassword: "/api/users/password/",
	forgetPassword: "/api/users/forget-password/",
	checkOtp: "/api/users/otp/",
	replacePassword: "/api/users/replace-password/",
};

export const authAPI = () => {
	return axios.create({
		baseURL: `${BASE_URL}`,
		headers: {
			Authorization: cookies.load("token"),
		},
	});
};

export default axios.create({
	baseURL: `${BASE_URL}`,
});
