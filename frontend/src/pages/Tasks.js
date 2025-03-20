import React from "react";
import { Link } from "react-router-dom";

const Tasks = () => {
    return (
        <div>
            <h2>Tasks Page</h2>

            <nav>
                <Link to="/tasks/your-tasks">Your Tasks</Link> | {" "}
                <Link to="/tasks/all-tasks">All Tasks</Link>
            </nav>

        </div>
    );
};

export default Tasks;