package com.example.backend.domain;

import com.example.backend.domain.enums.TaskType;
import jakarta.persistence.*;


@jakarta.persistence.Entity
@Table(name="tasks")
public class Task extends Entity<Long> {
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name="poster_id")
    private User poster;

    @ManyToOne
    @JoinColumn(name="solver_id")
    private User solver;

    private String datePosted;

    private String length;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    public Task() {}

    public User getSolverId() {
        return solver;
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
}
