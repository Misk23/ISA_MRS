package com.project.isa.service;


import com.project.isa.domain.*;
import com.project.isa.dto.AppointmentReservationDTO;
import com.project.isa.dto.PatientDTO;
import com.project.isa.dto.ReservePredefinedDTO;
import com.project.isa.dto.ReviewDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.*;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
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

    public void sendAppointmentReservationRequest(AppointmentReservationDTO appointmentReservationDTO) throws EntityDoesNotExistException{
        /*System.out.println("Evo me");

        System.out.println(appointmentReservationDTO.getPatient());*/

        Exam exam = new Exam();
        exam.setClinic(appointmentReservationDTO.getClinic());
        exam.setDoctor(appointmentReservationDTO.getDoctor());
        exam.setDate(appointmentReservationDTO.getDate());
        exam.setStart(appointmentReservationDTO.getStart());
        exam.setFinish(appointmentReservationDTO.getFinish());
        exam.setPrice(appointmentReservationDTO.getPrice());
        exam.setPatient(appointmentReservationDTO.getPatient());


        Doctor doctor = doctorRepository.findByName(appointmentReservationDTO.getDoctor()).get();

        if(appointmentReservationDTO.getVersion() == doctor.getVersion()) {

            for (Appointment a : doctor.getSchedule().getAppointmens().get(appointmentReservationDTO.getDate())) {
                if (a.getStart().equals(appointmentReservationDTO.getStart())) {
                    a.setFree(false);
                    doctor.setVersion(doctor.getVersion() + 1);
                }
            }
        }
        else
            throw new EntityDoesNotExistException("Appointment already reserved, please try again");

//        System.out.println(appointmentReservationDTO.getFinish());
        doctorRepository.save(doctor);
        examRepository.save(exam);

    }

    public ArrayList<Exam> getMyExams(String patient) throws EntityDoesNotExistException{

        if(!patientRepository.findByUsername(patient).isPresent())
            throw new EntityDoesNotExistException("Nema tog pacijenta");

        return examRepository.findAllByPatient(patient);
    }

    public void cancelAppointment(Long id) throws EntityDoesNotExistException{

        if(!examRepository.findById(id).isPresent())
            throw new EntityDoesNotExistException("Nema tog pregleda");

        Exam exam = examRepository.findById(id).get();

        Doctor doctor = doctorRepository.findByName(exam.getDoctor()).get();

        for (Appointment a: doctor.getSchedule().getAppointmens().get(exam.getDate())) {
            if (a.getStart().equals(exam.getStart())){
                a.setFree(true);
            }
        }

        doctorRepository.save(doctor);
        examRepository.delete(exam);

    }

    public ArrayList<Exam> getPredefinedExams(String clinic) throws EntityDoesNotExistException{

        if(!clinicRepository.findByName(clinic).isPresent()){
            throw new EntityDoesNotExistException("Klinika ne postoji");
        }

        return examRepository.findAllByClinicAndPatient(clinic, "PREDEFINED");
    }

    public void reservePredefined(ReservePredefinedDTO reservePredefinedDTO) throws EntityDoesNotExistException{
        /*System.out.println("Evo me 2222");
        System.out.println(reservePredefinedDTO.getId());
        System.out.println(reservePredefinedDTO.getUsername());*/

        Exam exam = examRepository.findById(reservePredefinedDTO.getId()).get();
        if(reservePredefinedDTO.getVersion() == exam.getVersion()) {
            exam.setPatient(reservePredefinedDTO.getUsername());
            exam.setVersion(exam.getVersion()+1);
            examRepository.save(exam);
        }else
            throw new EntityDoesNotExistException("Predefined exam no longer available, try again");
    }

    public MedicalHistory getMedicalHistory(String username) throws EntityDoesNotExistException{

        if(!userRepository.findByUsername(username).isPresent()){
            throw new EntityDoesNotExistException("There is no user with given username");
        }

        Patient patient = (Patient) userRepository.findByUsername(username).get();

        /*System.out.println(patient.getMedicalHistory().getDates());
        System.out.println(patient.getMedicalHistory().getDiagnose());
        System.out.println(patient.getMedicalHistory().getTherapy());*/
        return patient.getMedicalHistory();
    }

    public void leaveReview(ReviewDTO reviewDTO) throws EntityDoesNotExistException{

        if(!userRepository.findByUsername(reviewDTO.getPatient()).isPresent()){
            throw new EntityDoesNotExistException("Nije pronadjen ovaj pacijent");
        }

        Patient patient = (Patient) userRepository.findByUsername(reviewDTO.getPatient()).get();



        if (reviewDTO.getType().equals("clinic")){
            Clinic clinic = clinicRepository.findByName(reviewDTO.getName()).get();
            if(reviewDTO.getPrevious_score() == 0) {
                patient.getMedicalHistory().getClinic_reviews().put(reviewDTO.getName(), reviewDTO.getScore());

                clinic.getReviews().add(reviewDTO.getScore());

                System.out.println("Dodao novi review;");
                for ( int s : clinic.getReviews()){
                    System.out.println(s);
                }
            }
            else{
                patient.getMedicalHistory().getClinic_reviews().put(reviewDTO.getName(), reviewDTO.getScore());

                System.out.println("Izmenio prethodnu ocenu");

                for(int i=0; i<clinic.getReviews().size(); ++i){
                    if(clinic.getReviews().get(i) == reviewDTO.getPrevious_score()){
                        clinic.getReviews().remove(i);
                        clinic.getReviews().add(reviewDTO.getScore());
                    }
                }


                for ( int s : clinic.getReviews()) {
                    System.out.println(s);
                }
            }
            clinicRepository.save(clinic);

        }else if(reviewDTO.getType().equals("doctor")){
            Doctor doctor = doctorRepository.findByName(reviewDTO.getName()).get();
            if(reviewDTO.getPrevious_score() == 0) {
                patient.getMedicalHistory().getDoctor_reviews().put(reviewDTO.getName(), reviewDTO.getScore());

                doctor.getReviews().add(reviewDTO.getScore());

                System.out.println("Dodao novi review Doktor;");
                for ( int s : doctor.getReviews()){
                    System.out.println(s);
                }
            }
            else{
                patient.getMedicalHistory().getDoctor_reviews().put(reviewDTO.getName(), reviewDTO.getScore());

                System.out.println("Izmenio prethodnu ocenu DOKTOR");

                for(int i=0; i<doctor.getReviews().size(); ++i){
                    if(doctor.getReviews().get(i) == reviewDTO.getPrevious_score()){
                        doctor.getReviews().remove(i);
                        doctor.getReviews().add(reviewDTO.getScore());
                    }
                }


                for ( int s : doctor.getReviews()) {
                    System.out.println(s);
                }
            }
            doctorRepository.save(doctor);
        }

        userRepository.save(patient);

    }
}
