package com.project.isa.controller;

import com.project.isa.domain.Clinic;
import com.project.isa.domain.RegistrationRequest;
import com.project.isa.dto.ClinicAdminDTO;
import com.project.isa.dto.ClinicDTO;
import com.project.isa.dto.RegistrationResponseDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.service.AdminService;
import com.project.isa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/get_registration_requests", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<RegistrationRequest>> getAllRegistrationRequests(){
        ArrayList<RegistrationRequest> registrationRequests = adminService.getAllRegistrationRequests();
        return new ResponseEntity<ArrayList<RegistrationRequest>>(registrationRequests, HttpStatus.OK);
    }

    @RequestMapping(value = "/deny_registration_request", method = RequestMethod.POST)
    public ResponseEntity<String> denyRegistrationRequest(@RequestBody RegistrationResponseDTO registrationResponseDTO){

        try{
            adminService.denyRegistrationRequest(registrationResponseDTO.getUsername());
            emailService.sendEmail("Registration denied", registrationResponseDTO.getMessage(), registrationResponseDTO.getEmail());
        } catch (InvalidDataException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Action failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Action performed successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/approve_registration_request", method = RequestMethod.POST)
    public ResponseEntity<String> approveRegistrationRequest(@RequestBody RegistrationResponseDTO registrationResponseDTO){
        try{
            adminService.approveRegistrationRequest(registrationResponseDTO.getUsername());
            emailService.sendEmail("Registration approved",
                    registrationResponseDTO.getMessage() +
                            "Go to \n http://localhost:8081/user/verify_user/"+ registrationResponseDTO.getUsername()+ " to verify your account",
                    registrationResponseDTO.getEmail());
        }catch (InvalidDataException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Registration approval failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Registration approval successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/create_clinic", method = RequestMethod.POST)
    public ResponseEntity<String> createClinic(@RequestBody ClinicDTO clinicDTO){
        try{
            adminService.createClinic(clinicDTO);
        }catch (InvalidDataException | EntityAlreadyExistsException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Clinic creation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Clinic creation successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/create_clinic_admin", method = RequestMethod.POST)
    public ResponseEntity<String> createClinicAdmin(@RequestBody ClinicAdminDTO clinicAdminDTO){
        try{
            adminService.createClinicAdmin(clinicAdminDTO);
        }catch (InvalidDataException | EntityAlreadyExistsException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Clinic admin creation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Clinic admin creation successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/get_clinic_names", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<String>> getAllClinicNames(){
        ArrayList<String> clinics = adminService.getAllClinicNames();
        return new ResponseEntity<ArrayList<String>>(clinics, HttpStatus.OK);
    }







}
