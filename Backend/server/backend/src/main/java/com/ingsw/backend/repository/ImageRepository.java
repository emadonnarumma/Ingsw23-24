package com.ingsw.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
