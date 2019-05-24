package com.library.repository;


import com.library.models.Document;
import com.library.models.Image;
import com.library.models.Profile;
import com.library.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    //Page<Document> findByStatus(int status, Pageable pageable);
    Page<Document> findByStatus(int status, Pageable pageable);

    List<Document> findByName(String name);
}
