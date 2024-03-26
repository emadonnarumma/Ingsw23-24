package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;

public interface ImageService {
    List<Image> getAllImagesByAuction(Auction auction);

    Image addImage(Image image);

    Boolean delete(Integer id);
}
