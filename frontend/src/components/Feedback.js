import React from "react";
import ".././style.css";

const feedbackData = [
    {
        text: "I love NeighbourLink! I needed help carrying some heavy boxes after moving in, and within minutes, someone nearby\n" +
            "                offered to lend a hand. It’s so refreshing to be able to rely on neighbors for things like this instead of feeling\n" +
            "                like you have to handle everything alone. The app is easy to use, and I’ve been able to help others too. Highly\n" +
            "                recommend it if you want to build a stronger, friendlier community!",
        user: "Emily S.",
        review: "5 stars on Yelp"
    },
    {
        text: "NeighbourLink is such a great idea! I used it to find someone who could give me a lift to the grocery store when mycar broke down. The person I " +
            "connected with was super friendly and made the whole experience really pleasant. The only thing I’d love to see improved is more people using it in my " +
            "area, but overall, it’s a fantastic app that helps you feel more connected to your community!",
        user: "John M.",
        review: "A happy user of NeighbourLink"
    },
    {
        text: "This app has made my neighbourhood feel so much more like a true community. I’ve helped several people with small tasks,\n" +
            "                like watching their pets or picking up mail, and I’ve also gotten help when I needed it. The trust-based system is great\n" +
            "                because it ensures you’re helping and receiving assistance from people you can count on. It’s a win-win for everyone!",
        user: "Sarah K.",
        review: "Proud of her friendly neighbourhood"
    }
];

const Feedback = () => {
    return (
        <div>
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
