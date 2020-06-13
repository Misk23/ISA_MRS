package com.project.isa.service;

import com.project.isa.domain.*;
import com.project.isa.dto.AppointmentReservationDTO;
import com.project.isa.dto.PatientDTO;
import com.project.isa.dto.ReservePredefinedDTO;
import com.project.isa.dto.ReviewDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface UserService {

    void sendRegistrationRequest(PatientDTO patientDTO) throws InvalidDataException, EntityAlreadyExistsException;

    void verifyUser(String username) throws  InvalidDataException, EntityDoesNotExistException;

    boolean checkVerification(String username) throws InvalidDataException, EntityDoesNotExistException;

    Patient findByUsername(String username) throws InvalidDataException, EntityDoesNotExistException;

    boolean changePatient(PatientDTO patientDTO) throws EntityDoesNotExistException;

    ArrayList<Clinic> getAllClinics();

    ArrayList<Doctor> getDoctors(String name);

    void sendAppointmentReservationRequest(AppointmentReservationDTO appointmentReservationDTO) throws EntityDoesNotExistException;

    ArrayList<Exam> getMyExams(String patient) throws EntityDoesNotExistException;

    void cancelAppointment(Long id) throws EntityDoesNotExistException;

    ArrayList<Exam> getPredefinedExams(String clinic) throws EntityDoesNotExistException;

    void reservePredefined(ReservePredefinedDTO reservePredefinedDTO) throws EntityDoesNotExistException;

    MedicalHistory getMedicalHistory(String username) throws EntityDoesNotExistException;

    void leaveReview(ReviewDTO reviewDTO) throws  EntityDoesNotExistException;
}
