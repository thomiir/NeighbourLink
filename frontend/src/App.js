import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Promo from "./components/Promo";
// import Feedback from "./components/Feedback";
import Footer from "./components/Footer";
import Profile from "./pages/Profile";

const App = () => {
  return (
      <Router>  {/* Wrap everything in Router */}
        <Header />
        <Routes>
          <Route path="/" element={<Promo />} />
          <Route path="/community" element={<Promo />} /> {/* Add other routes as needed */}
          <Route path="/chat" element={<Promo />} />
          <Route path="/tasks" element={<Promo />} />
          <Route path="/profile" element={<Profile />} />  {/* Add route for Profile */}
        </Routes>
        <Footer />
      </Router>
  );
};

export default App;
