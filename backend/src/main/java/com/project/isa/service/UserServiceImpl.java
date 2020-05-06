package com.project.isa.service;


import com.project.isa.domain.*;
import com.project.isa.dto.AppointmentReservationDTO;
import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.*;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ExamRepository examRepository;

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

        if(userRepository.findByUsername(patientDTO.getUsername()).isPresent()) {
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

        //TODO Dodati u konstruktor
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

    public void verifyUser(String username) throws InvalidDataException, EntityDoesNotExistException{

        if (username == null ) throw new InvalidDataException("Username not defined");

        Optional<Patient> patient = patientRepository.findByUsername(username);

        if(!patient.isPresent()){
            throw new EntityDoesNotExistException("Username not found");
        }

        patient.get().setVerified(true);
        patientRepository.save(patient.get());
    }

    public boolean checkVerification(String username) throws InvalidDataException, EntityDoesNotExistException{

        if (username == null ) throw new InvalidDataException("Username not defined");

        Optional<Patient> patient = patientRepository.findByUsername(username);

        if(!patient.isPresent()){
            throw new EntityDoesNotExistException("Username not found");
        }

        return patient.get().isVerified();
    }

    public Patient findByUsername(String username) throws InvalidDataException, EntityDoesNotExistException {

        if (username == null ) throw new InvalidDataException("Username not defined");

        Optional<Patient> patient = patientRepository.findByUsername(username);

        if(!patient.isPresent()){
            throw new EntityDoesNotExistException("Username not found");
        }

        return patient.get();
    }

    public boolean changePatient(PatientDTO patientDTO) throws EntityDoesNotExistException{

        Optional<Patient> patient = patientRepository.findByUsername(patientDTO.getUsername());

        if(!patient.isPresent()){
            throw new EntityDoesNotExistException("Username not found");
        }

        patient.get().setName(patientDTO.getName());
        patient.get().setLast_name(patientDTO.getLast_name());
        patient.get().setAddress(patientDTO.getAddress());
        patient.get().setCity(patientDTO.getCity());
        patient.get().setCountry(patientDTO.getCountry());
        patient.get().setTelephone(patientDTO.getTelephone());

        if (!patient.get().getPassword().equals(patientDTO.getPassword())){
            BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
            patient.get().setPassword(enc.encode(patientDTO.getPassword()));
            System.out.println(enc.encode(patient.get().getPassword()));
        }

        patientRepository.save(patient.get());
        return true;
    }

    public ArrayList<Clinic> getAllClinics(){
        return (ArrayList<Clinic>)clinicRepository.findAll();
    }

    public ArrayList<Doctor> getDoctors(String name){
        long clinic_id = clinicRepository.findByName(name).get().getId();
        ArrayList<Doctor> doctors = (ArrayList<Doctor>) doctorRepository.findAll();
        ArrayList<Doctor> doctors1 = new ArrayList<Doctor>();
        for (Doctor d: doctors){
            if(clinic_id == d.getClinic().getId()){
                doctors1.add(d);
            }
        }
        return  doctors1;
    }

    public void sendAppointmentReservationRequest(AppointmentReservationDTO appointmentReservationDTO){
        System.out.println("Evo me");

        Exam exam = new Exam();
        exam.setClinic(appointmentReservationDTO.getClinic());
        exam.setDoctor(appointmentReservationDTO.getDoctor());
        exam.setDate(appointmentReservationDTO.getDate());
        exam.setStart(appointmentReservationDTO.getStart());
        exam.setFinish(appointmentReservationDTO.getFinish());
        exam.setPrice(appointmentReservationDTO.getPrice());

        Doctor doctor = doctorRepository.findByName(appointmentReservationDTO.getDoctor()).get();

        for (Appointment a: doctor.getSchedule().getAppointmens().get(appointmentReservationDTO.getDate())) {
            if (a.getStart().equals(appointmentReservationDTO.getStart())){
                a.setFree(false);
            }
        }

        System.out.println(appointmentReservationDTO.getFinish());
        doctorRepository.save(doctor);
        examRepository.save(exam);

    }


}
