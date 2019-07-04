package com.library.repository;

import com.library.models.Cela;
import com.library.models.Ala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface CelaRepository extends JpaRepository<Cela, UUID> {

    Page<Cela> findByStatus(int status, Pageable pageable);


}
