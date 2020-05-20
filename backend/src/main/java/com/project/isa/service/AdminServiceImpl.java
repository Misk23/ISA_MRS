package com.project.isa.service;

import com.project.isa.domain.*;
import com.project.isa.dto.ClinicAdminDTO;
import com.project.isa.dto.ClinicDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ClinicRepository clinicRepository;

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
        //userAuthorityRepository.save(authorities);
    }

    public void createClinic(ClinicDTO clinicDTO) throws InvalidDataException, EntityAlreadyExistsException{

        if (clinicDTO == null){
            throw new InvalidDataException("Data is null");
        }

        if(clinicDTO.getName() == null){
            throw new InvalidDataException("Name not given");
        }

        if(clinicRepository.findByName(clinicDTO.getName()).isPresent()){
            throw new EntityAlreadyExistsException("Clinic with this name already exists");
        }

        Clinic clinic = new Clinic(clinicDTO);
        clinicRepository.save(clinic);

    }

    public ArrayList<String> getAllClinicNames(){
        ArrayList<String> klinike = new ArrayList<String>();
        for (Clinic klinika : clinicRepository.findAll()){
            klinike.add(klinika.getName());
        }
        return klinike;
    }

    public void createClinicAdmin(ClinicAdminDTO clinicAdminDTO) throws InvalidDataException, EntityAlreadyExistsException{

        if(clinicAdminDTO == null){
            throw new InvalidDataException("Data is null");
        }
        if (clinicAdminDTO.getUsername() == null){
            throw new InvalidDataException("There is no username");
        }
        if (clinicAdminDTO.getPassword() == null){
            throw new InvalidDataException("There is no password");
        }

        if(userRepository.findByUsername(clinicAdminDTO.getUsername()).isPresent()){
            throw new EntityAlreadyExistsException("Username is taken");
        }

        ClinicAdmin clinicAdmin = new ClinicAdmin();
        UserAuthority authorities = new UserAuthority();

        Authority auth = authorityRepository.findByName("CLINIC_ADMIN_ROLE").get();
        auth.getUserAuthorities().add(authorities);
        authorities.setAuthority(auth);
        authorities.setUser(clinicAdmin);
        System.out.println(authorities.getAuthority().getName());
        clinicAdmin.getUserAuthorities().add(authorities);

        clinicAdmin.setUsername(clinicAdminDTO.getUsername());

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        clinicAdmin.setPassword(enc.encode("admin"));
        System.out.println(enc.encode(clinicAdmin.getPassword()));

        Clinic clinic = clinicRepository.findByName(clinicAdminDTO.getClinic()).get();
        clinicAdmin.setClinic(clinic);
        clinic.getClinicAdmins().add(clinicAdmin);
        //clinicRepository.save(clinic);
        clinicAdminRepository.save(clinicAdmin);

    }
}
