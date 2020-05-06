package com.project.isa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Patient extends User {

    @Column(nullable = true, columnDefinition = "DEFAULT NULL")
    private String name;

    @Column(columnDefinition = "varchar(255) default ''")
    private String last_name;

    @Column(columnDefinition = "varchar(255) default ''")
    private String email;

    @Column(columnDefinition = "varchar(255) default ''")
    private String address;

    @Column(columnDefinition = "varchar(255) default ''")
    private String city;

    @Column(columnDefinition = "varchar(255) default ''")
    private String country;

    @Column(columnDefinition = "varchar(255) default ''")
    private String telephone;

    @Column(columnDefinition = "varchar(255) default ''")
    private String insurance;

    @Column(nullable = false, columnDefinition = "BOOL DEFAULT FALSE")
    private boolean isVerified;


    public Patient(){

    }

    public Patient(String name, String last_name, String email, String address, String city, String country, String telephone, String insurance, boolean isVerified) {
        super();
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.insurance = insurance;
        this.isVerified = isVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
