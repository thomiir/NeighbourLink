package com.example.backend.service;

import com.example.backend.domain.Task;
import com.example.backend.domain.User;
import com.example.backend.domain.constants.SortingCriteria;
import com.example.backend.repository.PagingRepository;
import com.example.backend.repository.Repository;
import com.example.backend.util.paging.Page;
import com.example.backend.util.paging.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

@Service
public class TaskService {
    private final Repository<Long, Task> taskRepository;

    public TaskService(Repository<Long, Task> taskRepository, Repository<Long, User> userRepository) {
        this.taskRepository = taskRepository;
    }



    private Comparator<Task> getCriteriaComparator(SortingCriteria criteria) {
        return switch (criteria) {
            case DATE -> comparing(Task::getDatePosted);
            case TITLE -> comparing(Task::getTitle);
            case TYPE -> comparing(Task::getType);
            case DURATION -> comparing(Task::getLength);
            default -> null;
        };
    }


//    public Page<Task> findAllOnPage(Pageable pageable) {
//        return taskRepository.findAllOnPage(pageable);
//    }
    
    public Integer count() {
        return ((int) StreamSupport.stream(taskRepository.findAll().spliterator(), false).count());
    }

    public Integer getTasksSolvedByUser(User user) {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .filter(task -> task.getSolverId().equals(user.getId()))
                .toList()
                .size();
    }

    public Integer getTasksPostedByUser(User user) {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .filter(task -> task.getPosterId().equals(user.getId()))
                .toList()
                .size();
    }

    public List<Task> getAllTasks() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .toList();
    }
}
