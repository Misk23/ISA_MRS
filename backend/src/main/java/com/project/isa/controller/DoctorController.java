package com.project.isa.controller;


import com.project.isa.domain.Clinic;
import com.project.isa.domain.Exam;
import com.project.isa.dto.ConclusionResponseDTO;
import com.project.isa.dto.RegistrationResponseDTO;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.service.DoctorService;
import com.project.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/get_unconcluded_exams/{doctor}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Exam>> getUnconcludedExams(@PathVariable String doctor) {
        try {
            ArrayList<Exam> exams = doctorService.getUnconcludedExams(doctor);
            return new ResponseEntity<ArrayList<Exam>>(exams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ArrayList<Exam>>(new ArrayList<Exam>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/conclude_exam", method = RequestMethod.POST)
    public ResponseEntity<String> concludeExam(@RequestBody ConclusionResponseDTO conclusionResponseDTO){
        try{
            doctorService.concludeExam(conclusionResponseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("Exam conclusion", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Exam conclusion successful", HttpStatus.OK);
    }
}
