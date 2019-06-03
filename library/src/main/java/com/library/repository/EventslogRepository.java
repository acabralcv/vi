package com.library.repository;

import com.library.models.Document;
import com.library.models.Domain;
import com.library.models.Eventslog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventslogRepository extends JpaRepository<Eventslog, UUID> {

    Page<Eventslog> findByStatus(int status, Pageable pageable);
}
