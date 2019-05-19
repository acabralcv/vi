package com.library.repository;

import com.library.models.Profile;
import com.library.models.User;
import com.library.models.UserProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfiles, UUID> {

    Page<UserProfiles> findByStatus(int status, Pageable pageable);
}
