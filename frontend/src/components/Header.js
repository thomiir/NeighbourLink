import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "./AuthContext";

const Header = () => {
    const {isLoggedIn} = useAuth();

    return (
        <nav>
            <Link to="/" className="navbar-logo">NeighbourLink</Link>
            <ul className="navbar-list">
                <li className="navbar-item"><Link to="/community">Community</Link></li>
                <li className="navbar-item"><Link to="/chat">Chat</Link></li>
                <li className="navbar-item"><Link to="/all-tasks">All Tasks</Link></li>
                <li className="navbar-item"><Link to="/your-tasks">Your Tasks</Link></li>
            </ul>
            {isLoggedIn ? (
                <>
                    <Link to="/profile" className="login-button">DASHBOARD</Link>
                </>
                ) : (
                    <Link to="/logon" className="login-button">LOGIN</Link>
                )}
        </nav>
    );
}

export default Header;