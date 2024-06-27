// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
	apiKey: "AIzaSyAOuFasDTthACUd3f5HTA4FSLiAtSXcM4o",
	authDomain: "thesis-management-chat.firebaseapp.com",
	projectId: "thesis-management-chat",
	storageBucket: "thesis-management-chat.appspot.com",
	messagingSenderId: "392709260216",
	appId: "1:392709260216:web:05d0dc13b0327a3b6e054d",
	measurementId: "G-6PT8E05YG8",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const db = getFirestore(app);
