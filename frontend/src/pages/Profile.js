import React from "react";
import profileImg from "../profile-removebg-preview.jpg"
import {CompositionExample} from "../components/Gauge";

const Profile = () => {
    return (
        <div>  {/**/}
            <div className="profile-items">
                <img src={profileImg} alt="profile-picture" height={250}></img>

                <div> {/* user's personal info*/}
                    <h1>About you</h1>
                    <table className="table-items">
                        <tbody>
                        <tr>
                            <td>Username:</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
                            <td>Full name:</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
                            <td>Address:</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
                        </tr>
                        <tr>
                            <td>Email address</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
                            <td>Community (Zip code)</td>
                            <td><textarea disabled={true} style={{resize: "none"}}></textarea></td>
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
        </div>
    );
};

export default Profile;
