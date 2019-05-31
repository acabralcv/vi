package com.library.repository;

import com.library.models.Document;
import com.library.models.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaisRepository extends JpaRepository<Pais, UUID>{

    Page<Pais> findByStatus(int status, Pageable pageable);

    Pais findByNome(String nome);

}
