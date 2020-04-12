package com.project.isa.service;

import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;

public interface UserService {

    public void sendRegistrationRequest(PatientDTO patientDTO) throws InvalidDataException, EntityAlreadyExistsException;

    public void verifyUser(String username) throws  InvalidDataException;

    public boolean checkVerification(String username) throws InvalidDataException;
}
