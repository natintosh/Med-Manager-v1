package com.natinc.oluwatobiloba.medmanager.models;

public class Medication {

    private String name;
    private String description;
    private String numberOfPills;
    private String dose;
    private String interval;
    private String start;
    private String end;

    public Medication() {

    }

    public Medication(String name, String description, String numberOfPills, String dose, String interval, String start, String end) {
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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
