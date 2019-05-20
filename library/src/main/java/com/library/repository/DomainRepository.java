package com.library.repository;

import com.library.models.Domain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DomainRepository extends JpaRepository<Domain, UUID> {

    Page<Domain> findByStatus(int status, Pageable pageable);

    List<Domain> findByName(String name);

    Page<Domain> findByDomainType(String domainType, Pageable pageable);

    Domain findByNameAndDomainType(String name, String domainType);
}
