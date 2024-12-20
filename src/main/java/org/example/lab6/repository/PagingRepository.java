package org.example.lab6.repository;

import org.example.lab6.domain.Entity;
import org.example.lab6.util.paging.Page;
import org.example.lab6.util.paging.Pageable;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);

}
