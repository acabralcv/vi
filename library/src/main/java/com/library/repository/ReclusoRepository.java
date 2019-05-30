package com.library.repository;

import com.library.models.Recluso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReclusoRepository extends JpaRepository<Recluso, UUID> {

    Page<Recluso> findByStatus(int status, Pageable pageable);

    List<Recluso> findByNome(String nome);
}
