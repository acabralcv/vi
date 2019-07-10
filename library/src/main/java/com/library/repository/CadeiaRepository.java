package com.library.repository;

import com.library.models.Cadeia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CadeiaRepository extends JpaRepository<Cadeia, UUID> {

    Page<Cadeia> findByStatus(int status, Pageable pageable);
}
