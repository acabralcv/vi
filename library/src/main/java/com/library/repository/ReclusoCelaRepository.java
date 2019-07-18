package com.library.repository;


import com.library.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReclusoCelaRepository extends JpaRepository<ReclusoCela, UUID> {

    Page<ReclusoCela> findByStatus(int status, Pageable pageable);

    ArrayList<ReclusoCela> findByRecluso(Recluso recluso);

    Optional<ReclusoCela> findByReclusoAndCela(Recluso recluso, Cela cela);
}
