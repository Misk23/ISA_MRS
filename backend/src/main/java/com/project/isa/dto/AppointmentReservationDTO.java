package com.project.isa.dto;

public class AppointmentReservationDTO {

    private String clinic;
    private String doctor;
    private int version;
    private String patient;
    private String date;
    private String start;
    private String finish;
    private String price;

    public AppointmentReservationDTO(){}

    public AppointmentReservationDTO(String clinic, String doctor, String patient, String date, String start, String finish, String price) {
        this.clinic = clinic;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.start = start;
        this.finish = finish;
        this.price = price;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
