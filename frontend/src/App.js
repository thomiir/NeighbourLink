import React from "react";
import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import Header from "./components/Header";
import Promo from "./components/Promo";
import Footer from "./components/Footer";
import Profile from "./pages/Profile";
import Feedback from "./components/Feedback";
import Tasks from "./pages/Tasks";
import YourTasks from "./pages/YourTasks";

const AppContent = () => {
    const location = useLocation();
    return (
        <>
            <Header />
            <Routes>
                <Route path="/" element={<Promo />} />
                <Route path="/community" element={<Promo />} />
                <Route path="/chat" element={<Promo />} />
                <Route path="/tasks" element={<Tasks />} />
                <Route path="/tasks/your-tasks" element={<YourTasks />} />
                <Route path="/tasks/all-tasks" element={<YourTasks />} />
                <Route path="/profile" element={<Profile />} />
            </Routes>
            {location.pathname === "/" && <Feedback />}
            <Footer />
        </>
    );
};

const App = () => (
    <Router>
        <AppContent />
    </Router>
);

export default App;