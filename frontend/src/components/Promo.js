import React from "react";
import promoImage from "../promo-img-removebg-preview.png";
import ".././style.css";

const Promo = () => {
    return (
        <div className="promo-div">
            <br/>
            <br/>
            <h1 className="promo-title">
                Welcome to NeighbourLink<br/> Your Neighbourhood, Your Community!
            </h1>
            <div className="promo-container">
                <div className="promo-text">
                    &nbsp;&nbsp;&nbsp;&nbsp;Looking for a way to connect with your neighbours, share helpful favours, and build a stronger sense of community?
                    NeighbourLink is here to make that happen! Sign up now and start connecting - it's simple, free, and impactful!
                    <br/><br/>
                    <a href="/login">
                        &nbsp;&nbsp;&nbsp;&nbsp;<button className="join-button">JOIN NOW!</button>
                    </a>
                </div>
                <img className="promo-img" src={promoImage} alt="NeighbourLink" />
            </div>
        </div>
    );
};

export default Promo;
