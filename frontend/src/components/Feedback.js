import React from "react";
import ".././style.css";

const feedbackData = [
    {
        text: "I love NeighbourLink! I needed help carrying some heavy boxes after moving in, and within minutes, someone nearby offered to lend a hand.",
        user: "Emily S.",
        review: "5 stars on Yelp"
    },
    {
        text: "NeighbourLink is such a great idea! I used it to find someone who could give me a lift to the grocery store when my car broke down.",
        user: "John M.",
        review: "A happy user of NeighbourLink"
    },
    {
        text: "This app has made my neighbourhood feel so much more like a true community. I’ve helped several people with small tasks.",
        user: "Sarah K.",
        review: "Proud of her friendly neighbourhood"
    }
];

const Feedback = () => {
    return (
        <div className="promo-div">
            <h1 className="feedback-title">Our users' feedback</h1>
            <div className="feedback-container">
                {feedbackData.map((feedback, index) => (
                    <div className="feedback-text" key={index}>
                        {feedback.text}
                        <div className="feedback-user">
                            <br />
                            <strong>- {feedback.user}</strong>, {feedback.review}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Feedback;
