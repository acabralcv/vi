package com.library.repository;

import com.library.models.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SetorRepository extends JpaRepository<Setor, UUID> {

    Page<Setor> findByStatus(int status, Pageable pageable);
}
