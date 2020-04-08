package com.project.isa.repository;

import com.project.isa.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUsername(String username);
    Optional<Patient> findByEmail(String email);
}
