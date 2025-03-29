import React, { useEffect, useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import { useAuth } from "../components/AuthContext";
import { useNavigate } from "react-router-dom";

const authHeader = "Basic " + btoa(`${process.env.REACT_APP_USERNAME}:${process.env.REACT_APP_PASSWORD}`);

const YourTasks = () => {
    const [tasks, setTasks] = useState([]);
    const [pageNumber, setPageNumber] = useState(1);
    const [pageCount, setPageCount] = useState(1);
    const [error, setError] = useState("");

    const { isLoggedIn, username } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        console.log("Auth state in YourTasks:", { isLoggedIn, username });

        if (!isLoggedIn || !username) {
            console.log("User not logged in, redirecting to login");
            navigate("/login");
        }
    }, [isLoggedIn, username, navigate]);

    useEffect(() => {
        if (!username) return;
        console.log("Fetching task count for user:", username);

        fetch(`http://localhost:8081/api/tasks/your-tasks/count?username=${encodeURIComponent(username)}`, {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json"
            },
        })
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch task count");
                return res.json();
            })
            .then(count => {
                console.log("Task count received:", count);
                setPageCount(Math.max(1, count));
            })
            .catch(err => {
                console.error("Error fetching task count:", err);
                setError("Failed to load tasks. Please try again.");
            });
    }, [username]);

    // Fetch tasks
    useEffect(() => {
        if (!username) return; // Only fetch if we have a username

        fetchTasks();
    }, [username, pageNumber]);

    const fetchTasks = (currentPage = pageNumber) => {
        if (!username) return;
        console.log(`Fetching tasks for user: ${username}, page: ${currentPage}`);

        fetch(`http://localhost:8081/api/tasks/your-tasks?username=${encodeURIComponent(username)}&pageNumber=${currentPage}`, {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json",
            }
        })
            .then(res => {
                if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
                return res.json();
            })
            .then(data => {
                console.log("Tasks received:", data);
                setTasks(Array.isArray(data) ? data : []);
            })
            .catch(err => {
                console.error("Error fetching tasks:", err);
                setError("Failed to load tasks. Please try again.");
                setTasks([]);
            });
    };

    const handlePrevious = () => {
        if (pageNumber > 1) {
            setPageNumber(prevPage => prevPage - 1);
        }
    };

    const handleNext = () => {
        if (pageNumber < pageCount) {
            setPageNumber(prevPage => prevPage + 1);
        }
    };

    if (error) {
        return (
            <>
                <Header />
                <div className="your-tasks-header">
                    <h1 className="tasks-title">Your posted tasks</h1>
                    <div className="error-message" style={{ color: "red" }}>{error}</div>
                </div>
                <Footer />
            </>
        );
    }

    return (
        <>
            <Header />
            <div>
                <div className="your-tasks-header">
                    <h1 className="tasks-title">Your posted tasks</h1>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button className="add-task-button">ADD TASK</button>
                </div>
                <div className="tasks-table">
                    {tasks.length === 0 ? (
                        <p>No tasks found.</p>
                    ) : (
                        <table className="table-items">
                            <thead>
                            <tr>
                                <td>Task No.</td>
                                <td>Type</td>
                                <td>Title</td>
                                <td>Poster</td>
                                <td>Date</td>
                                <td>Duration</td>
                            </tr>
                            </thead>
                            <tbody>
                            {tasks.map((task, index) => (
                                <tr key={task.id}>
                                    <td>{(pageNumber-1) * 10 + index + 1}</td>
                                    <td>{task.type}</td>
                                    <td>{task.title}</td>
                                    <td>{task.posterId.fullName}</td>
                                    <td>{task.datePosted}</td>
                                    <td>{task.length}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>

                <div className="pagination">
                    <button
                        className="pagination-button"
                        onClick={handlePrevious}
                        disabled={pageNumber <= 1}
                        style={pageNumber<= 1 ? {backgroundColor: "#b0b2b8", color:"black"}  : {backgroundColor: "#1a1b1f"} }
                    >
                        Previous
                    </button>
                    <h1>Page {pageNumber} of {pageCount}</h1>
                    <button
                        className="pagination-button"
                        onClick={handleNext}
                        disabled={pageNumber >= pageCount}
                        style={pageNumber>=pageCount ? {backgroundColor: "#b0b2b8", color: "black"}  : {backgroundColor: "#1a1b1f"} }
                    >
                        Next
                    </button>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default YourTasks;