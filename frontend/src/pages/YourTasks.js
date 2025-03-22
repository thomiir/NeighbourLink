import React, { useEffect, useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";

const authHeader = "Basic " + btoa(`${process.env.REACT_APP_USERNAME}:${process.env.REACT_APP_PASSWORD}`);

const YourTasks = () => {
    const [tasks, setTasks] = useState([]);
    const [pageNumber, setPageNumber] = useState(1);
    const [pageCount, setPageCount] = useState(1);

    useEffect(() => {
        fetch("http://localhost:8081/api/tasks/count", {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json"
            },
        })
            .then(res => res.json())
            .then(json => setPageCount(json))
    }, []);


    const fetchTasks = (currentPage = pageNumber) => {
        fetch(`http://localhost:8081/api/tasks?pageNumber=${currentPage}`, {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json",
            }
        })
            .then(res => res.json())
            .then(json => setTasks(json))
    };



    useEffect(() => {
        fetchTasks();
    }, []);


    const handlePrevious = () => {
        if (pageNumber > 1)
            setPageNumber(prevPage => {
                const newPage = prevPage - 1;
                fetchTasks(newPage);
                return newPage;
            });
    }

    const handleNext = () => {
        if (pageNumber < pageCount)
            setPageNumber(prevPage => {
                const newPage = prevPage + 1;
                fetchTasks(newPage);
                return newPage;
            });
    }

    return (
        <>
        <Header/>
        <div className="your-tasks">
            <h1 className="tasks-title">Your posted tasks</h1>
            <button className="add-task-button">ADD TASK</button>
                <div className="tasks-table">
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
                                <td>{index + 1}</td>
                                <td>{task.type}</td>
                                <td>{task.title}</td>
                                <td>{task.posterId.fullName}</td>
                                <td>{task.datePosted}</td>
                                <td>{task.length}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            <div className="pagination">
                <button className="pagination-button" onClick={handlePrevious}>Previous</button>
                <h1>Page {pageNumber} of {pageCount}</h1>
                <button className="pagination-button" onClick={handleNext}>Next</button>
            </div>
        </div>
        <Footer/>
        </>
    );
};

export default YourTasks;
