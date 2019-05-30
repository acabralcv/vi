package com.library.repository;

import com.library.models.Profile;
import com.library.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Page<Profile> findByStatus(int status, Pageable pageable);

    List<Profile> findByName(String name);

    @Query("SELECT p FROM Profile p INNER JOIN UserProfiles up ON up.profile = p WHERE up.status = ?1 and up.user = ?2")
    Set<Profile> findProfileByStatus(Integer status, User user);
}
