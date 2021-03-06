package com.library.repository;

import com.library.models.Profile;
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
public interface UserRepository extends JpaRepository<User, UUID> {

    Page<User> findByStatus(int status, Pageable pageable);

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    @Query("SELECT p FROM User p INNER JOIN UserProfiles up ON up.user = p WHERE up.status = ?1 and up.user = ?2")
    Set<Profile> findProfileByStatus(Integer status, User user);


}

