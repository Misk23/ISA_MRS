package com.project.isa.domain;


import javax.persistence.*;
import java.util.ArrayList;

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


    public MedicalHistory(){
        this.dates = new ArrayList<String>();
        this.diagnose = new ArrayList<String>();
        this.therapy = new ArrayList<String>();
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
}
