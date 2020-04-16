package com.project.isa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.isa.dto.ClinicDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clinic", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    List<ClinicAdmin> clinicAdmins;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    List<Doctor> doctors;


    public Clinic() {
    }

    public Clinic(String name, String address, String description){
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Clinic(ClinicDTO clinicDTO){
        this.name = clinicDTO.getName();
        this.address = clinicDTO.getAddress();
        this.description = clinicDTO.getDescription();
        this.clinicAdmins = new ArrayList<ClinicAdmin>();
        this.doctors = new ArrayList<Doctor>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ClinicAdmin> getClinicAdmins() {
        return clinicAdmins;
    }

    public void setClinicAdmins(List<ClinicAdmin> clinicAdmins) {
        this.clinicAdmins = clinicAdmins;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
