package com.library.repository;

import com.library.models.WfActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface WfActivityRepository extends JpaRepository<WfActivities, UUID> {

    /**
     * find all by status - default status => Helper.STATUS_ACTIVE
     * @param status
     * @return
     */
    WfActivities findBystatus(String status);

    /**
     * Find ativity by id and type
     * @param id
     * @param wfType
     * @return
     */
    WfActivities findByIdAndWfType(UUID id, String wfType);

    /**
     *
     * @param name
     * @param wfType
     * @return
     */
    WfActivities findByNameAndWfType(String name, String wfType);

}
