package com.api.microservices.services;

import com.api.microservices.domaine.User;
import java.util.Optional;


public interface UserRepository extends GenericRepository<User> {

    Optional<User> findByUsernameIgnoreCase(String username);

}


