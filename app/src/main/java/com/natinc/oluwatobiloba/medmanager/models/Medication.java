package com.natinc.oluwatobiloba.medmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.natinc.oluwatobiloba.medmanager.utils.MedicationId;

public class Medication extends MedicationId implements Parcelable {

    private String name;
    private int color;
    private String description;
    private String numberOfPills;
    private String dose;
    private long interval;
    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };
    private long start;

    public Medication() {
    }

    private long end;

    public Medication(String name, int color, String description, String numberOfPills, String dose, long interval, long start, long end) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.numberOfPills = numberOfPills;
        this.dose = dose;
        this.interval = interval;
        this.start = start;
        this.end = end;
    }

    protected Medication(Parcel in) {
        name = in.readString();
        color = in.readInt();
        description = in.readString();
        numberOfPills = in.readString();
        dose = in.readString();
        interval = in.readLong();
        start = in.readLong();
        end = in.readLong();
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(color);
        parcel.writeString(description);
        parcel.writeString(numberOfPills);
        parcel.writeString(dose);
        parcel.writeLong(interval);
        parcel.writeLong(start);
        parcel.writeLong(end);
    }
}