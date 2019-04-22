package com.repository;


import com.models.WfProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WfProcessRepository extends JpaRepository<WfProcess, UUID> {

    WfProcess findByProcessCode(String processCode);
}
