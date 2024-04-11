package com.ingsw.backend.service.dbservice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.ExternalLink;
import com.ingsw.backend.repository.ExternalLinkRepository;
import com.ingsw.backend.service.ExternalLinkService;

@Service("mainExternalLinkService")
public class ExternalLinkDbService implements ExternalLinkService{
	
	private final ExternalLinkRepository externalLinkRepository;
	

	public ExternalLinkDbService(ExternalLinkRepository externalLinkRepository) {
		this.externalLinkRepository = externalLinkRepository;
	}


	@Override
	public ExternalLink addExternalLink(ExternalLink externalLink) {
		
		return externalLinkRepository.save(externalLink);
	}

	@Override
	public Boolean delete(Integer id) {
		
		if (!externalLinkRepository.existsById(id)) {
	        return false;
	    }
	    
	    externalLinkRepository.deleteById(id);
	    
	    return true;
	}

	@Override
	public Optional<ExternalLink> update(Integer id, ExternalLink externalLink) {
		
		if (!externalLinkRepository.existsById(id)) {
	        return Optional.empty();
	    }

		externalLink.setIdExternalLink(id);
	    ExternalLink updatedExternalLink = externalLinkRepository.save(externalLink);
	    
	    return Optional.of(updatedExternalLink);
	}

	@Override
	public Optional<ExternalLink> get(Integer id) {

		if (!externalLinkRepository.existsById(id)) {
	        return Optional.empty();
	    }

		return externalLinkRepository.findById(id);
	}

}
