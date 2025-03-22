package com.example.backend.repository;

import com.example.backend.domain.Entity;
import com.example.backend.util.paging.Page;
import com.example.backend.util.paging.Pageable;

public interface PagedRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);

}