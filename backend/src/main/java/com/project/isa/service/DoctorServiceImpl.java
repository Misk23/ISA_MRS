package com.project.isa.service;

import com.project.isa.domain.Exam;
import com.project.isa.domain.Patient;
import com.project.isa.dto.ConclusionResponseDTO;
import com.project.isa.repository.ExamRepository;
import com.project.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;


    public ArrayList<Exam> getUnconcludedExams(String doctor){

        ArrayList<Exam> rezultat = new ArrayList<Exam>();
        ArrayList<Exam> svi = examRepository.findAllByDoctor(doctor);
        for( Exam e : svi){
            if (!e.isConcluded() && !e.getPatient().equals("PREDEFINED"))
                rezultat.add(e);
        }
        return rezultat;
    }

    public void concludeExam(ConclusionResponseDTO conclusionResponseDTO) {
        System.out.println(conclusionResponseDTO.getExamId());
        System.out.println(conclusionResponseDTO.getPatientUsername());
        System.out.println(conclusionResponseDTO.getDate());
        System.out.println(conclusionResponseDTO.getTherapy());
        System.out.println(conclusionResponseDTO.getDiagnose());

        Patient patient = (Patient) userRepository.findByUsername(conclusionResponseDTO.getPatientUsername()).get();

        patient.getMedicalHistory().getDates().add(conclusionResponseDTO.getDate());
        patient.getMedicalHistory().getDiagnose().add(conclusionResponseDTO.getDiagnose());
        patient.getMedicalHistory().getTherapy().add(conclusionResponseDTO.getTherapy());

        Exam exam = examRepository.findById(conclusionResponseDTO.getExamId()).get();
        exam.setConcluded(true);

        examRepository.save(exam);
        userRepository.save(patient);
    }
}
