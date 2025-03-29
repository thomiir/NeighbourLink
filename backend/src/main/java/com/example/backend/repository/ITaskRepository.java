package com.example.backend.repository;

import com.example.backend.domain.Task;
import com.example.backend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByPoster(User user, Pageable pageable);
    
    Integer findByPoster(User user);

    Integer findBySolver(User user);
}
