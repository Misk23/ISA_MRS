package com.project.isa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Doctor extends User{


    @Column( nullable = false)
    private String name;

    @Column
    private Specialties specialties;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Schedule schedule;

    @ManyToOne
    @JsonBackReference
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
}
