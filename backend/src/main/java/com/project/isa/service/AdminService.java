package com.project.isa.service;

import com.project.isa.domain.Clinic;
import com.project.isa.domain.RegistrationRequest;
import com.project.isa.dto.ClinicAdminDTO;
import com.project.isa.dto.ClinicDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;

import java.util.ArrayList;

public interface AdminService {

    ArrayList<RegistrationRequest> getAllRegistrationRequests();

    void denyRegistrationRequest(String username) throws InvalidDataException;

    void approveRegistrationRequest(String username) throws InvalidDataException;

    void createClinic(ClinicDTO clinicDTO) throws InvalidDataException, EntityAlreadyExistsException;

    void createClinicAdmin(ClinicAdminDTO clinicAdminDTO) throws InvalidDataException, EntityAlreadyExistsException;

    ArrayList<String> getAllClinicNames();

}
