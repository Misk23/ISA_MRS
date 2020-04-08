package com.project.isa.service;

import com.project.isa.domain.RegistrationRequest;
import com.project.isa.exceptions.InvalidDataException;

import java.util.ArrayList;

public interface AdminService {

    public ArrayList<RegistrationRequest> getAllRegistrationRequests();
    public void denyRegistrationRequest(String username) throws InvalidDataException;
    public void approveRegistrationRequest(String username) throws InvalidDataException;
}
