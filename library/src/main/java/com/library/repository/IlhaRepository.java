package com.library.repository;

import com.library.models.Ilha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IlhaRepository extends JpaRepository<Ilha, UUID> {

    Page<Ilha> findByStatus(int status, Pageable pageable);

    Ilha findByNome(String nome);

}
