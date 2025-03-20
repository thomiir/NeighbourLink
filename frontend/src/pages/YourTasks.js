import React from "react"

const YourTasks = () => {
    return (
    //     daca fetch-ul e gol, nu se mai afiseaza tabelul ci mesajul
    //     You haven't posted any tasks yet!
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
                <tr>
                    <td>1</td>
                    <td>Test</td>
                    <td>Test</td>
                    <td>Test</td>
                    <td>Test</td>
                    <td>Test</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div className="pagination">
            <button className="pagination-button">Previous</button>
            <h1>Page x of x</h1>
            <button className="pagination-button">Next</button>
        </div>
    </div>
    );
}

export default YourTasks;