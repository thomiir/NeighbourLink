import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HeaderLogin from "./components/Header";
import Promo from "./components/Promo";
import Footer from "./components/Footer";
import Profile from "./pages/Profile";
import Feedback from "./components/Feedback";
import YourTasks from "./pages/YourTasks";
import Login from "./pages/Login";
import AllTasks from "./pages/AllTasks";
import {AuthProvider} from "./components/AuthContext";

const App = () => (
    <AuthProvider>
        <Router>
            <Routes>
                <Route path="/" element={<><HeaderLogin /><Promo /><Feedback /><Footer /></>} />
                <Route path="/community" element={<Promo />} />
                <Route path="/chat" element={<Promo />} />
                <Route path="/your-tasks" element={<YourTasks />} />
                <Route path="/all-tasks" element={<AllTasks />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/logon" element={<Login/>}/>
            </Routes>
        </Router>
    </AuthProvider>
);

export default App;