package com.project.isa.service;


import com.project.isa.domain.Patient;
import com.project.isa.domain.RegistrationRequest;
import com.project.isa.domain.User;
import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.AuthorityRepository;
import com.project.isa.repository.PatientRepository;
import com.project.isa.repository.RegistrationRequestRepository;
import com.project.isa.repository.UserRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

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
        if (patientDTO.getTelephone() == null){
            throw new InvalidDataException("There is no telephone");
        }
        if (patientDTO.getInsurance() == null){
            throw new InvalidDataException("There is no insurance number");
        }

        if(patientRepository.findByUsername(patientDTO.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException("Username is taken");
        }

        if(registrationRequestRepository.findByUsername(patientDTO.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException("Username is taken");
        }

        /*if(patientRepository.findByEmail(patientDTO.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("Email already exists");
        }*/

        //Zakomentarisano zarad lakseg testiranja
        /*if(registrationRequestRepository.findByEmail(patientDTO.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("Email already exists");
        }*/

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

    public void verifyUser(String username) throws InvalidDataException{

        if (username == null ) throw new InvalidDataException("Username not defined");

        Optional<Patient> user = patientRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new InvalidDataException("Username not found");
        }

        user.get().setVerified(true);
        patientRepository.save(user.get());
    }

    public boolean checkVerification(String username) throws InvalidDataException{

        if (username == null ) throw new InvalidDataException("Username not defined");

        Optional<Patient> user = patientRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new InvalidDataException("Username not found");
        }

        return user.get().isVerified();
    }


}
