import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {useAuth} from "../components/AuthContext";

const authHeader = "Basic " + btoa(`${process.env.REACT_APP_USERNAME}:${process.env.REACT_APP_PASSWORD}`);

const Login = () => {
    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");
    const navigate = useNavigate();
    const { setIsLoggedIn } = useAuth();

    const handleSubmit = (e) => {
        e.preventDefault();

        fetch(`http://localhost:8081/api/users?username=${user}&password=${pass}`, {
            method: "GET",
            headers: {
                Authorization: authHeader,
                "Content-Type": "application/json",
            },
        })
            .then(res => res.json())
            .then(res => {
                setIsLoggedIn(res);
                console.log(res);
                navigate("/");
                });
    };

    return (
        <div>
            <div>
                <h2>Welcome back</h2>
                <p>Please enter your details</p>
            </div>
            <div>
                <form onSubmit={handleSubmit}>
                    <label>Email address:</label><br />
                    <input
                        type="text"
                        name="username"
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                        required
                    /><br />
                    <label>Password:</label><br />
                    <input
                        type="password"
                        name="password"
                        value={pass}
                        onChange={(e) => setPass(e.target.value)}
                        required
                    /><br />
                    <button type="submit">Login</button>
                </form>

            </div>
        </div>
    );
};

export default Login;
