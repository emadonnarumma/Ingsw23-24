package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.model.ExternalLink;

public interface ExternalLinkService {
	ExternalLink addExternalLink(ExternalLink externalLink);
	
	Boolean delete(Integer id);
	
	Optional<ExternalLink> update(Integer id, ExternalLink externalLink);

	Optional<ExternalLink> get(Integer id);
}
