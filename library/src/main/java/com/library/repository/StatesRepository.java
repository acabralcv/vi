package com.library.repository;

import com.library.models.Recluso;
import com.library.models.States;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatesRepository extends JpaRepository<States, UUID> {

    Page<States> findByStatus(int status, Pageable pageable);
}
