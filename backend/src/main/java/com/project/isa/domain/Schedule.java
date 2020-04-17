package com.project.isa.domain;


import javax.persistence.*;
import java.util.*;


@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int start;

    @Column
    private int finish;

    @Column
    private int price;

    @Column
    final HashMap<Date, ArrayList<Appointment>> appointmens;


    public Schedule() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public HashMap<Date, ArrayList<Appointment>> getAppointmens() {
        return appointmens;
    }
}
