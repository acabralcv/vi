package com.library.repository;

import com.library.models.Image;
import com.library.models.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    Page<Image> findByStatus(int status, Pageable pageable);

    List<Image> findByName(String name);

    Page<Image> findByUserId(UUID userId, Pageable pageable);
}