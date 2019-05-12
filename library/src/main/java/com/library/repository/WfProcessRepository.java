package com.library.repository;


import com.library.models.WfProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WfProcessRepository extends PagingAndSortingRepository<WfProcess, UUID> {

    /**
     * find all by status - default status => Helper.STATUS_ACTIVE
     * @param status
     * @return
     */
    Page<WfProcess> findByStatus(int status, Pageable pageable);

    /**
     *
     * @param processCode
     * @return
     */
    WfProcess findByProcessCode(String processCode);
}
