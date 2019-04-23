package com.library.repository;

import com.library.models.WfProcess;
import com.library.models.WfWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WfWorkflowRepository extends JpaRepository<WfWorkflow, UUID> {

    WfWorkflow findByWfType(String wfType);
}
