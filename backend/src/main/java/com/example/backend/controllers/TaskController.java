package com.example.backend.controllers;

import com.example.backend.domain.Task;
import com.example.backend.domain.User;
import com.example.backend.service.TaskService;
import com.example.backend.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam("pageNumber") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);
        return taskService.findAllOnPage(pageable).toList();
    }

    @GetMapping("/count")
    public Integer getTasksPageCount() {
        return (int)Math.ceil((double) taskService.allCount() / 10);
    }

    @GetMapping("/your-tasks")
    public List<Task> getYourTasks(@RequestParam("username") String username, @RequestParam("pageNumber") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);
        User user = userService.findUser(username).get();
        return taskService.getTasksPostedByUserOnPage(user, pageable);
    }

    @GetMapping("/your-tasks/count")
    public Integer getYourTasksCount(@RequestParam("username") String username) {
        User user = userService.findUser(username).get();
        return (int)Math.ceil((double) taskService.tasksPostedByUserCount(user) / 10);
    }
}
