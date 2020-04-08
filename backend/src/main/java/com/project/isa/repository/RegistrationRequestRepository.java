package com.project.isa.repository;

import com.project.isa.domain.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    Optional<RegistrationRequest> findByUsername(String username);
    Optional<RegistrationRequest> findByEmail(String email);

}


