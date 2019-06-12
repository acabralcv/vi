package com.library.repository;

import com.library.models.Profile;
import com.library.models.Tasks;
import com.library.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, UUID> {

    Page<Tasks> findByStatus(int status, Pageable pageable);

    Set<Tasks> findByMessage(String message);

    Page<Tasks> findByStatusAndUser(Integer status, User user, Pageable pageable);

    @Query("SELECT t FROM Tasks t  " +
            "WHERE t.status = ?1 and t.user = ?2 and t.targetUser =?3 and t.taskType = ?4 ")
    Optional<Tasks> findIfExists(Integer status, User user, User targetUser, String taskType);
}

