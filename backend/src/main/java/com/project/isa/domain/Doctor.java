package com.project.isa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Doctor extends User{

    @ManyToOne
    @JsonBackReference
    Clinic clinic;
}
