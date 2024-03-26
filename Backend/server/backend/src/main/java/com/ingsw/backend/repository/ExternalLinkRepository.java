package com.ingsw.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.ExternalLink;

public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Integer> {
}
