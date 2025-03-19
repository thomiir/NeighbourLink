import React from "react";
import { Link } from "react-router-dom";
import "../style.css";

const Header = () => {
    return (
        <nav>
            <Link to="/" className="navbar-logo">NeighbourLink</Link>
            <ul className="navbar-list">
                <li className="navbar-item"><Link to="/community">Community</Link></li>
                <li className="navbar-item"><Link to="/chat">Chat</Link></li>
                <li className="navbar-item"><Link to="/tasks">Tasks</Link></li>
                <li className="navbar-item"><Link to="/profile">Profile</Link></li>
            </ul>
            <Link to="/login" className="login-button">LOGIN</Link>
        </nav>
    );
};

export default Header;
