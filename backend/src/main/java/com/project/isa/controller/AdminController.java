package com.project.isa.controller;

import com.project.isa.domain.RegistrationRequest;
import com.project.isa.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @RequestMapping(value = "get_registration_requests", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<RegistrationRequest>> getAllRegistrationRequests(){
        ArrayList<RegistrationRequest> registrationRequests = adminService.getAllRegistrationRequests();
        return new ResponseEntity<ArrayList<RegistrationRequest>>(registrationRequests, HttpStatus.OK);
    }
}
