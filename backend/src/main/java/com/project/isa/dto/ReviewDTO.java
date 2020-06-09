package com.project.isa.dto;

public class ReviewDTO {

    private String name;
    private int score;
    private String type;
    private String patient;
    private int previous_score;

    public ReviewDTO() {
    }

    public ReviewDTO(String name, int score, String type, String patient, int previous_score) {
        this.name = name;
        this.score = score;
        this.type = type;
        this.patient = patient;
        this.previous_score = previous_score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public int getPrevious_score() {
        return previous_score;
    }

    public void setPrevious_score(int previous_score) {
        this.previous_score = previous_score;
    }


}
