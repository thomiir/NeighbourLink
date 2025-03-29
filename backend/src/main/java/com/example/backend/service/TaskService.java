package com.example.backend.service;

import com.example.backend.domain.Task;
import com.example.backend.domain.User;
import com.example.backend.repository.ITaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> findAllOnPage(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
    
    public Integer allCount() {
        return taskRepository.findAll().size();
    }

    public Integer getTasksSolvedByUser(User user) {
        return taskRepository.findBySolver(user);
    }

    public List<Task> getTasksPostedByUserOnPage(User user, Pageable pageable) {
        return taskRepository.findByPoster(user, pageable).toList();
    }

    public Integer tasksPostedByUserCount(User user) {
        return taskRepository.findByPoster(user);
    }
}
