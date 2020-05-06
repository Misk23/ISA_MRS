package com.project.isa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.isa.dto.ClinicAdminDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ClinicAdmin extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    Clinic clinic;

    public ClinicAdmin() {
    }

    public ClinicAdmin(Long id, String username, String password) {
        super(id, username, password);
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
