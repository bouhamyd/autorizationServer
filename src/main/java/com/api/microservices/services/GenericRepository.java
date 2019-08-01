package com.api.microservices.services;


import com.api.microservices.domaine.IdentifiableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T extends IdentifiableEntity> extends JpaRepository<T, Long> {

}
