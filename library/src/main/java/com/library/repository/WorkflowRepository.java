package com.library.repository;

import com.library.models.Workflow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {

    Page<Workflow> findByStatus(int status, Pageable pageable);

    Optional<Workflow> findByProcessCode(String processCode);

    Optional<Workflow> findByTargetTableId(UUID targetTableId);
}