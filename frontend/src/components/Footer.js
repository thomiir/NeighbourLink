import React from "react";
import ".././style.css";

const Footer = () => {
    return (
        <div className="promo-div">
            <footer>
                <br/>
                <ul className="footer-list">
                    <li className="footer-item">Home</li>
                    <li className="footer-item">About Us</li>
                    <li className="footer-item">Contact</li>
                    <li className="footer-item">Privacy Policy</li>
                </ul>
                <p style={{ fontFamily: "Merriweather", textAlign: "center", fontSize: "0.9rem", fontStyle: "italic" }}>
                    Copyright &copy; 2025 NeighbourLink
                </p>
            </footer>
        </div>
    );
};

export default Footer;
