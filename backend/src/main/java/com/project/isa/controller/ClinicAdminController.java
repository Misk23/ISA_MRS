package com.project.isa.controller;


import com.project.isa.dto.ClinicAdminDTO;
import com.project.isa.dto.DoctorDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.service.ClinicAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic_admin")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;

    @RequestMapping(value = "/create_doctor", method = RequestMethod.POST)
    public ResponseEntity<String> createDoctor(@RequestBody DoctorDTO doctorDTO){
        try{
            clinicAdminService.createDoctor(doctorDTO);
        }catch (InvalidDataException | EntityAlreadyExistsException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Doctor creation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Doctor creation successful", HttpStatus.OK);
    }
}
