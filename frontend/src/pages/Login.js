import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/AuthContext";
import img from "../11197293.jpg"

const authHeader = "Basic " + btoa(`${process.env.REACT_APP_USERNAME}:${process.env.REACT_APP_PASSWORD}`);

const Login = () => {
    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const { isLoggedIn, setIsLoggedIn, username, setUsername, login } = useAuth();

    useEffect(() => {
        console.log("Login component mounted with auth state:", { isLoggedIn, username });
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        console.log("Login attempt with username:", user);
        try {
            const response = await fetch(`http://localhost:8081/api/users?username=${encodeURIComponent(user)}&password=${encodeURIComponent(pass)}`, {
                method: "GET",
                headers: {
                    Authorization: authHeader,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            console.log("Login response:", result);

            if (login) {
                login(user, !!result);
            } else {
                console.log("Setting username to:", user);
                setUsername(user);
                setTimeout(() => {
                    console.log("Setting isLoggedIn to:", !!result);
                    setIsLoggedIn(!!result);
                    console.log("Navigating to home page");
                    navigate("/");
                }, 100);
                return;
            }
            console.log("Auth state after login:", { username: user, isLoggedIn: !!result });
            navigate("/");

        } catch (err) {
            console.error("Login error:", err);
            setError("Login failed. Please try again.");
        }
    };

    return (
        <div style={{display: "flex", justifyContent: "flex-end"}}>
            <div>
                <div>
                    <h2>Welcome back</h2>
                    <p>Please enter your details</p>
                    {error && <div style={{color: "red"}}>{error}</div>}
                </div>
                <div>
                    <form onSubmit={handleSubmit}>
                        <label>Email address:</label><br/>
                        <input
                            type="text"
                            name="username"
                            value={user}
                            onChange={(e) => setUser(e.target.value)}
                            required
                        /><br/>
                        <label>Password:</label><br/>
                        <input
                            type="password"
                            name="password"
                            value={pass}
                            onChange={(e) => setPass(e.target.value)}
                            required
                        /><br/>
                        <button type="submit">Login</button>
                    </form>
                </div>
            </div>
            <div>
                <img src={img} alt="abcd" style={{maxWidth: "50%"}}></img>
            </div>
        </div>
    );
};

export default Login;