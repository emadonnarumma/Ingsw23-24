package com.ingsw.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingsw.backend.model.ExternalLink;

@Repository
public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Integer> {

}
