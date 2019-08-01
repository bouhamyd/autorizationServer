package com.api.microservices.services;

import com.api.microservices.domaine.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;


@Service
public class AuthorityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityService.class);

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Transactional(readOnly = true)
    public List<Authority> findAll() {
        LOGGER.debug("Recherche de tous les rôles");
        return authorityRepository.findAll();
    }
}
