package com.project.isa.controller;

import com.project.isa.domain.RegistrationRequest;
import com.project.isa.dto.RegistrationResponseDTO;
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
            return new ResponseEntity<String>("Action failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Action performed successfully", HttpStatus.OK);
    }




}
