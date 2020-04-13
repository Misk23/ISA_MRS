package com.project.isa.service;

import com.project.isa.domain.Patient;
import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;

public interface UserService {

    void sendRegistrationRequest(PatientDTO patientDTO) throws InvalidDataException, EntityAlreadyExistsException;

    void verifyUser(String username) throws  InvalidDataException, EntityDoesNotExistException;

    boolean checkVerification(String username) throws InvalidDataException, EntityDoesNotExistException;

    Patient findByUsername(String username) throws InvalidDataException, EntityDoesNotExistException;

    boolean changePatient(PatientDTO patientDTO) throws EntityDoesNotExistException;
}
