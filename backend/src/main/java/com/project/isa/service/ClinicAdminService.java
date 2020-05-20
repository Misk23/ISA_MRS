package com.project.isa.service;

import com.project.isa.domain.Clinic;
import com.project.isa.dto.DoctorDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;

public interface ClinicAdminService {

    void createDoctor(DoctorDTO doctorDTO) throws EntityAlreadyExistsException, InvalidDataException;

    Clinic getClinic(String username) throws EntityDoesNotExistException;
}