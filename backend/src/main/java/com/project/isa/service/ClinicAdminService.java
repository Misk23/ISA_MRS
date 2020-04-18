package com.project.isa.service;

import com.project.isa.dto.DoctorDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;

public interface ClinicAdminService {

    void createDoctor(DoctorDTO doctorDTO) throws EntityAlreadyExistsException, InvalidDataException;
}