import React, {useContext, useEffect, useState} from "react";
import profileImg from "../profile-removebg-preview.jpg"
import {CompositionExample} from "../components/Gauge";
import Footer from "../components/Footer";
import Header from "../components/Header";
import {useAuth} from "../components/AuthContext";
import {useNavigate} from "react-router-dom";

const authHeader = "Basic " + btoa(`${process.env.REACT_APP_USERNAME}:${process.env.REACT_APP_PASSWORD}`);

const Profile = () => {
    const {username, setIsLoggedIn} = useAuth();
    const [userData, setUserData] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8081/api/users/userData?username=${username}`, {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json"
            }
        }).then(res => res.json()).then(userData => setUserData(userData));
    },[]);

    return (
        <>
        <Header/>
        <div>
            <div className="profile-items">
                <img src={profileImg} alt="profile-picture" height={250}></img>

                <div> {/* user's personal info*/}
                    <h1>About you</h1>
                    <table className="table-items">
                        <tbody>
                        <tr>
                            <td>Username:</td>
                            <td><textarea disabled={true} className='textarea' value={userData['username']}></textarea></td>
                            <td>Full name:</td>
                            <td><textarea disabled={true} className='textarea' value={userData['fullName']}></textarea></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" disabled={true} className='textarea' value={userData['password']}></input></td>
                            <td>Address:</td>
                            <td><textarea disabled={true} className='textarea' value={userData['address']}></textarea></td>
                        </tr>
                        <tr>
                            <td>Email address</td>
                            <td><textarea disabled={true} className='textarea' value={userData['email']}></textarea></td>
                            <td>Community (Zip code)</td>
                            <td><textarea disabled={true} className='textarea' value={userData['zipCode']}></textarea></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div style={{paddingBottom: "3rem"}}> {/* user's personal stats*/}
                <h1 style={{paddingLeft:"10rem", paddingTop:"1rem"}}>Your stats</h1>
                <div className="stats">
                    <ul className="stats-list">
                        <li><CompositionExample/></li>
                        <li className="stats-item">Tasks <strong>posted</strong></li>
                    </ul>
                    <ul className="stats-list">
                        <li><CompositionExample/></li>
                        <li className="stats-item">Tasks <strong>solved</strong></li>
                    </ul>

                </div>
            </div>
            <button onClick={() => {setIsLoggedIn(false); navigate("/");}}>Logout</button>
        </div>
        <Footer/>
        </>
    );
};

export default Profile;
