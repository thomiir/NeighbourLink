package com.example.backend.controllers;

import com.example.backend.domain.Task;
import com.example.backend.service.TaskService;
import com.example.backend.util.paging.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam("pageNumber") Integer pageNumber) {
        Pageable pageable = new Pageable(10, pageNumber);
        System.out.println(pageNumber);
        return StreamSupport.stream(taskService.findAllOnPage(pageable).getElementsOnPage().spliterator(), false).toList();

    }

    @GetMapping("/count")
    public Integer getTasksPageCount() {
        return (int)Math.ceil((double) taskService.count() / 10);
    }

}
