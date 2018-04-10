package com.natinc.oluwatobiloba.medmanager.models;

import java.util.Date;

public class Medication {

    private String name;
    private int color;
    private String description;
    private String numberOfPills;
    private String dose;
    private long interval;
    private Date start;
    private Date end;

    public Medication() {
    }

    public Medication(String name, int color, String description, String numberOfPills, String dose, long interval, Date start, Date end) {
        this.name = name;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
