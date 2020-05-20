package com.project.isa.repository;

import com.project.isa.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findById(Long id);
    ArrayList<Exam> findAllByPatient(String patient);
    ArrayList<Exam> findAllByClinicAndPatient(String clinic, String patient);
    ArrayList<Exam> findAllByDoctor(String doctor);
}
