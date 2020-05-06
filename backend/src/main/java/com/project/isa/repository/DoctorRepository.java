package com.project.isa.repository;

import com.project.isa.domain.Doctor;
import com.project.isa.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByName(String name);
}
