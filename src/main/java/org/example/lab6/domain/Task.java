package org.example.lab6.domain;

import org.example.lab6.domain.constants.TaskType;
import org.example.lab6.util.Time;

import java.time.LocalDateTime;

public class Task extends Entity<Long> {
    private final String title;
    private final String description;
    private final Long posterId;
    private Long solverId;
    private final LocalDateTime datePosted;
    private final Time length;
    private final TaskType type;

    public Long getSolverId() {
        return solverId;
    }

    public void setSolverId(Long solverId) {
        this.solverId = solverId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getPosterId() {
        return posterId;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public Time getLength() {
        return length;
    }

    public TaskType getType() {
        return type;
    }

    public Task(Long id, String title, String description, Long posterId, Long solverId, LocalDateTime datePosted, String length, TaskType type) {
        this.title = title;
        this.description = description;
        this.posterId = posterId;
        this.solverId = solverId;
        this.datePosted = datePosted;
        this.length = Time.parse(length);
        this.type = type;
        this.setId(id);
    }
}
