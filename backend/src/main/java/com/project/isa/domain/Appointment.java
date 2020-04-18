package com.project.isa.domain;

import java.io.Serializable;

public class Appointment implements Serializable {


    String start;
    String finish;
    private boolean free;

    public Appointment(){

    }

    public Appointment(boolean free) {
        this.free = free;
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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
