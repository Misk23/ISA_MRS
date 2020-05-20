package com.project.isa.service;

import com.project.isa.domain.Exam;
import com.project.isa.dto.ConclusionResponseDTO;

import java.util.ArrayList;

public interface DoctorService {

    ArrayList<Exam> getUnconcludedExams(String doctor);

    void concludeExam(ConclusionResponseDTO conclusionResponseDTO);

}
