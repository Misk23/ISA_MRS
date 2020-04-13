package com.project.isa.dto;

import com.project.isa.domain.Patient;

public class PatientDTO {

    private String username;
    private String password;
    private String name;
    private String last_name;
    private String email;
    private String address;
    private String city;
    private String country;
    private String telephone;
    private String insurance;


    public PatientDTO(){

    }

    public PatientDTO (Patient patient){
        this.setUsername(patient.getUsername());
        this.setPassword(patient.getPassword());
        this.setName(patient.getName());
        this.setLast_name(patient.getLast_name());
        this.setEmail(patient.getEmail());
        this.setAddress(patient.getAddress());
        this.setCity(patient.getCity());
        this.setCountry(patient.getCountry());
        this.setTelephone(patient.getTelephone());
        this.setInsurance(patient.getInsurance());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
