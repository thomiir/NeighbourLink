package com.example.backend.domain;

import com.example.backend.domain.constants.TaskType;
import com.example.backend.util.Time;

import java.time.LocalDateTime;

public class Task extends Entity<Long> {
    private final String title;
    private final String description;
    private final User poster;
    private User solver;
    private final String datePosted;
    private final String length;
    private final TaskType type;

    public User getSolverId() {
        return solver;
    }

    public void setSolverId(User solverId) {
        this.solver = solverId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getPosterId() {
        return poster;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getLength() {
        return length;
    }

    public TaskType getType() {
        return type;
    }

    public Task(Long id, String title, String description, User poster, User solver, String datePosted, String length, TaskType type) {
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.solver = solver;
        this.datePosted = datePosted;
        this.length = length;
        this.type = type;
        this.setId(id);
    }
}
