package com.project.isa.dto;

public class DoctorDTO {

    private String admin;
    private String username;
    private String name;
    private String specialties;
    private String date_of_creation;
    private int start;
    private int finish;
    private int price;

    public DoctorDTO(){

    }

    public DoctorDTO(String admin, String username, String name, String specialties, String date_of_creation,
                     int start, int finish, int price) {
        this.admin = admin;
        this.username = username;
        this.name = name;
        this.specialties = specialties;
        this.date_of_creation = date_of_creation;
        this.start = start;
        this.finish = finish;
        this.price = price;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
