package com.ingsw.backend.controller;

import java.util.Optional;

import com.ingsw.backend.enumeration.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.backend.model.ExternalLink;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.ExternalLinkService;
import com.ingsw.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externalLink")
public class ExternalLinkController {

    private final ExternalLinkService externalLinkService;
    private final UserService userService;

    public ExternalLinkController(@Qualifier("mainExternalLinkService") ExternalLinkService externalLinkService,
                                  @Qualifier("mainUserService") UserService userService) {
        this.externalLinkService = externalLinkService;
        this.userService = userService;
    }

    @PostMapping("/{email}/{role}")
    public ResponseEntity<ExternalLink> addExternalLink(@Valid @RequestBody ExternalLink externalLink, @PathVariable Role role, @PathVariable String email) {

        Optional<User> user = userService.getUser(email, role);

        if (user.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        externalLink.setUser(user.get());

        ExternalLink savedExternalLink = externalLinkService.addExternalLink(externalLink);

        return new ResponseEntity<>(savedExternalLink, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExternalLink> updateExternalLink(@PathVariable Integer id, @Valid @RequestBody ExternalLink externalLink) {
        Optional<ExternalLink> existingExternalLink = externalLinkService.get(id);
        if (existingExternalLink.isPresent())
            externalLink.setUser(existingExternalLink.get().getUser());
        else {
            return ResponseEntity.notFound().build();
        }

        Optional<ExternalLink> updatedExternalLink = externalLinkService.update(id, externalLink);

        if (updatedExternalLink.isPresent()) {

            return ResponseEntity.ok(updatedExternalLink.get());

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExternalLink(@PathVariable Integer id) {

        if (Boolean.TRUE.equals(externalLinkService.delete(id))) {

            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }
    }

}
