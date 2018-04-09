package com.natinc.oluwatobiloba.medmanager.models;

import com.google.firebase.Timestamp;

public class Medication {

    private String name;
    private String description;
    private String numberOfPills;
    private String dose;
    private Timestamp interval;
    private Timestamp start;
    private Timestamp end;

    public Medication() {

    }

    public Medication(String name, String description, String numberOfPills, String dose, Timestamp interval, Timestamp start, Timestamp end) {
        this.name = name;
        this.description = description;
        this.numberOfPills = numberOfPills;
        this.dose = dose;
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDetails(String description) {
        this.description = description;
    }

    public String getNumberOfPills() {
        return numberOfPills;
    }

    public void setNumberOfPills(String numberOfPills) {
        this.numberOfPills = numberOfPills;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Timestamp getInterval() {
        return interval;
    }

    public void setInterval(Timestamp interval) {
        this.interval = interval;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
