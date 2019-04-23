package com.library.repository;

import com.library.models.WfActivities;
import com.library.models.WfProcess;
import com.library.models.WfWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface WfActivityRepository extends JpaRepository<WfActivities, UUID> {

    WfActivities findByName(String name);
}
