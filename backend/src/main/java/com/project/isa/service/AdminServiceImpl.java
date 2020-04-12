package com.project.isa.service;

import com.project.isa.domain.Authority;
import com.project.isa.domain.Patient;
import com.project.isa.domain.RegistrationRequest;
import com.project.isa.domain.UserAuthority;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.AuthorityRepository;
import com.project.isa.repository.PatientRepository;
import com.project.isa.repository.RegistrationRequestRepository;;
import com.project.isa.repository.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

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

        UserAuthority authorities = new UserAuthority();

        Authority auth = authorityRepository.findByName("PATIENT_ROLE").get();

        auth.getUserAuthorities().add(authorities);
        authorities.setAuthority(auth);
        authorities.setUser(patient);
        System.out.println(authorities.getAuthority().getName());
        patient.getUserAuthorities().add(authorities);

        patient.setUsername(registrationRequest.get().getUsername());
        patient.setEmail(registrationRequest.get().getEmail());
        patient.setName(registrationRequest.get().getName());
        patient.setLast_name(registrationRequest.get().getLast_name());
        patient.setAddress(registrationRequest.get().getAddress());
        patient.setCity(registrationRequest.get().getCity());
        patient.setCountry(registrationRequest.get().getCountry());
        patient.setTelephone(registrationRequest.get().getTelephone());
        patient.setInsurance(registrationRequest.get().getTelephone());
        patient.setVerified(false);

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        patient.setPassword(enc.encode(registrationRequest.get().getPassword()));
        System.out.println(enc.encode(patient.getPassword()));

        registrationRequestRepository.delete(registrationRequest.get());
        patientRepository.save(patient);
        userAuthorityRepository.save(authorities);
    }
}
