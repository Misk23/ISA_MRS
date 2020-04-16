package com.project.isa.dto;

public class ClinicAdminDTO {

    String username;
    String password;
    String clinic;


    public ClinicAdminDTO(){

    }

    public ClinicAdminDTO(String username, String password, String clinic) {
        this.username = username;
        this.password = password;
        this.clinic = clinic;
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

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }
}
