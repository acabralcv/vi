package com.library.repository;

import com.library.models.Cadeia;
import com.library.models.Complexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface ComplexoRepository extends JpaRepository<Complexo, UUID> {

    Page<Complexo> findByStatus(int status, Pageable pageable);

    ArrayList<Complexo> findByCadeia(Cadeia cadeia);
}
