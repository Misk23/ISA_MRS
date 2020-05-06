package com.project.isa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Doctor extends User{


    @Column( nullable = true)
    private String name;

    @Column
    private Specialties specialties;

    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    Clinic clinic;

    public Doctor() {
    }

    public Doctor(Long id, String username, String password) {
        super(id, username, password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialties getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Specialties specialties) {
        this.specialties = specialties;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
