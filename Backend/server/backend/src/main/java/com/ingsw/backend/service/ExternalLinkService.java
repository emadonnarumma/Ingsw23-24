package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.model.ExternalLink;

public interface ExternalLinkService {
	
	public ExternalLink addExternalLink(ExternalLink externalLink);
	
	public Boolean delete(Integer id);
	
	public Optional<ExternalLink> update(Integer id, ExternalLink externalLink);

}
