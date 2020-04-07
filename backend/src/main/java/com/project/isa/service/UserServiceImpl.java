package com.project.isa.service;


import com.project.isa.domain.RegistrationRequest;
import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.AuthorityRepository;
import com.project.isa.repository.RegistrationRequestRepository;
import com.project.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    public void sendRegistrationRequest(PatientDTO patientDTO) throws InvalidDataException, EntityAlreadyExistsException{

        if (patientDTO == null){
            throw new InvalidDataException("There is no data");
        }

        RegistrationRequest registrationRequest = new RegistrationRequest();

        if (patientDTO.getUsername() == null){
            throw new InvalidDataException("There is no username");
        }
        if (patientDTO.getPassword() == null){
            throw new InvalidDataException("There is no password");
        }
        if (patientDTO.getName() == null){
            throw new InvalidDataException("There is no name");
        }
        if (patientDTO.getLast_name() == null){
            throw new InvalidDataException("There is no last name");
        }
        if (patientDTO.getEmail() == null){
            throw new InvalidDataException("There is no email");
        }
        if (patientDTO.getAddress() == null){
            throw new InvalidDataException("There is no address");
        }
        if (patientDTO.getCity() == null){
            throw new InvalidDataException("There is no city");
        }
        if (patientDTO.getCountry() == null){
            throw new InvalidDataException("There is no country");
        }
        if (patientDTO.getTelephone() == 0){
            throw new InvalidDataException("There is no telephone");
        }
        if (patientDTO.getInsurance() == 0){
            throw new InvalidDataException("There is no insurance number");
        }

        if(userRepository.findByUsername(patientDTO.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException("Username is taken");
        }

        if(userRepository.findByEmail(patientDTO.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("Email already exists");
        }

        if(patientDTO.getUsername().length() < 3 || patientDTO.getUsername().length() > 20)
            throw new InvalidDataException("Invalid username format");
        if(patientDTO.getPassword().length() < 3 || patientDTO.getPassword().length() > 20)
            throw new InvalidDataException("Invalid password format");
        if(patientDTO.getName().length() < 3 || patientDTO.getName().length() > 20)
            throw new InvalidDataException("Invalid name format");
        if(patientDTO.getLast_name().length() < 3 || patientDTO.getLast_name().length() > 20)
            throw new InvalidDataException("Invalid surname format");

        registrationRequest.setUsername(patientDTO.getUsername());
        registrationRequest.setPassword(patientDTO.getPassword());
        registrationRequest.setName(patientDTO.getName());
        registrationRequest.setLast_name(patientDTO.getLast_name());
        registrationRequest.setEmail(patientDTO.getEmail());
        registrationRequest.setAddress(patientDTO.getAddress());
        registrationRequest.setCity(patientDTO.getCity());
        registrationRequest.setCountry(patientDTO.getCountry());
        registrationRequest.setTelephone(patientDTO.getTelephone());
        registrationRequest.setInsurance(patientDTO.getInsurance());

        registrationRequestRepository.save(registrationRequest);

    }


}
