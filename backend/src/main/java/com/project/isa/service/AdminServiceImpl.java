package com.project.isa.service;

import com.project.isa.domain.Patient;
import com.project.isa.domain.RegistrationRequest;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.PatientRepository;
import com.project.isa.repository.RegistrationRequestRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private PatientRepository patientRepository;

    public ArrayList<RegistrationRequest>  getAllRegistrationRequests(){
        return (ArrayList<RegistrationRequest>)registrationRequestRepository.findAll();
    }

    public void denyRegistrationRequest(String username) throws InvalidDataException {

        if(username == null) throw new InvalidDataException("Username not defined");

        Optional<RegistrationRequest> registrationRequest =
                registrationRequestRepository.findByUsername(username);

        if (!registrationRequest.isPresent()) throw new InvalidDataException("Registration request not found");

        registrationRequestRepository.delete(registrationRequest.get());
    }

    public void approveRegistrationRequest(String username) throws InvalidDataException{

        if(username == null) throw new InvalidDataException("Username not defined");

        Optional<RegistrationRequest> registrationRequest =
                registrationRequestRepository.findByUsername(username);

        if (!registrationRequest.isPresent()) throw new InvalidDataException("Registration request not found");

        Patient patient = new Patient();

        patient.setUsername(registrationRequest.get().getUsername());
        patient.setPassword(registrationRequest.get().getPassword());
        patient.setEmail(registrationRequest.get().getEmail());
        patient.setName(registrationRequest.get().getName());
        patient.setLast_name(registrationRequest.get().getLast_name());
        patient.setAddress(registrationRequest.get().getAddress());
        patient.setCity(registrationRequest.get().getCity());
        patient.setCountry(registrationRequest.get().getCountry());
        patient.setTelephone(registrationRequest.get().getTelephone());
        patient.setInsurance(registrationRequest.get().getTelephone());
        patient.setVerified(false);

        registrationRequestRepository.delete(registrationRequest.get());
        patientRepository.save(patient);
    }
}
