import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Promo from "./components/Promo";
import Footer from "./components/Footer";
import Profile from "./pages/Profile";
import Feedback from "./components/Feedback";

const App = () => {
  return (
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={<Promo />} />
          <Route path="/community" element={<Promo />} />
          <Route path="/chat" element={<Promo />} />
          <Route path="/tasks" element={<Promo />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
          <Feedback />
        <Footer />
      </Router>
  );
};

export default App;
