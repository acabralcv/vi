package com.library.repository;

import com.library.models.Setor;
import com.library.models.Ala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface AlaRepository extends JpaRepository<Ala, UUID> {

    Page<Ala> findByStatus(int status, Pageable pageable);

    ArrayList<Ala> findBySetor(Setor setor);


}
