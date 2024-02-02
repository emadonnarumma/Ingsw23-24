package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;

public interface ImageService {
	
	public List<Image> getAllImagesByAuction(Auction auction);

	public Image addImage(Image image);
	
	public Boolean delete(Integer id);

}
