package com.api.microservices.services;

import com.api.microservices.domaine.Authority;
import java.util.Optional;

public interface AuthorityRepository extends GenericRepository<Authority> {

    Optional<Authority> findByNameIgnoreCase(String name);

}
