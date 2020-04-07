package com.project.isa.service;

import com.project.isa.domain.RegistrationRequest;
import com.project.isa.repository.RegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminServiceImpl {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    public ArrayList<RegistrationRequest>  getAllRegistrationRequests(){
        return (ArrayList<RegistrationRequest>)registrationRequestRepository.findAll();
    }
}
