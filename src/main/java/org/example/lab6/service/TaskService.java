package org.example.lab6.service;

import javafx.scene.control.TableColumn;
import org.example.lab6.domain.Task;
import org.example.lab6.domain.User;
import org.example.lab6.domain.constants.SortingCriteria;
import org.example.lab6.repository.PagingRepository;
import org.example.lab6.repository.Repository;
import org.example.lab6.util.paging.Page;
import org.example.lab6.util.paging.Pageable;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Comparator.*;

public class TaskService {
    private final PagingRepository<Long, Task> taskRepository;

    public TaskService(PagingRepository<Long, Task> taskRepository, Repository<Long, User> userRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> getTasks(Pageable pageable, SortingCriteria criteria, TableColumn.SortType sortType) {
        Comparator<Task> criteriaComparator = getCriteriaComparator(criteria);
        Comparator<Task> finalComparator = getDirectionComparator(criteriaComparator, sortType);

        return StreamSupport.stream(findAllOnPage(pageable).getElementsOnPage().spliterator(), false)
                .sorted(finalComparator)
                .collect(Collectors.toList());
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

    private Comparator<Task> getDirectionComparator(Comparator<Task> criteriaComparator, TableColumn.SortType sortType) {
        return switch (sortType) {
            case ASCENDING -> criteriaComparator;
            case DESCENDING -> criteriaComparator.reversed();
        };
    }

    public Page<Task> findAllOnPage(Pageable pageable) {
        return taskRepository.findAllOnPage(pageable);
    }
    
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
}
