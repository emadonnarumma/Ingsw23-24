package com.ingsw.backend.service.dbservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;
import com.ingsw.backend.repository.ImageRepository;
import com.ingsw.backend.service.ImageService;

@Service("mainImageService")
public class ImageDbService implements ImageService {
	
	private final ImageRepository imageRepository;


	public ImageDbService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	@Override
	public List<Image> getAllImagesByAuction(Auction auction) {
		
		return imageRepository.findAllByAuction(auction);
	}

	@Override
	public List<Image> addImages(List<Image> images) {
		
		return imageRepository.saveAll(images);
	}

	@Override
	public Boolean delete(Integer id) {
		
	    if (!imageRepository.existsById(id)) {
	        return false;
	    }
	    
	    imageRepository.deleteById(id);
	    
	    return true;
	}

}
