package com.project.isa.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private ArrayList<String> dates;

    @Column
    private ArrayList<String> diagnose;

    @Column
    private ArrayList<String> therapy;

    @Column
    private HashMap<String, Integer> clinic_reviews;

    @Column
    private HashMap<String, Integer> doctor_reviews;


    public MedicalHistory(){
        this.dates = new ArrayList<String>();
        this.diagnose = new ArrayList<String>();
        this.therapy = new ArrayList<String>();
        this.clinic_reviews = new HashMap<String, Integer>();
        this.doctor_reviews = new HashMap<String, Integer>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public ArrayList<String> getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(ArrayList<String> diagnose) {
        this.diagnose = diagnose;
    }

    public ArrayList<String> getTherapy() {
        return therapy;
    }

    public void setTherapy(ArrayList<String> therapy) {
        this.therapy = therapy;
    }

    public HashMap<String, Integer> getClinic_reviews() {
        return clinic_reviews;
    }

    public void setClinic_reviews(HashMap<String, Integer> clinic_reviews) {
        this.clinic_reviews = clinic_reviews;
    }

    public HashMap<String, Integer> getDoctor_reviews() {
        return doctor_reviews;
    }

    public void setDoctor_reviews(HashMap<String, Integer> doctor_reviews) {
        this.doctor_reviews = doctor_reviews;
    }
}
