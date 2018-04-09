package com.natinc.oluwatobiloba.medmanager.utils;

import android.widget.EditText;

public class DateTimeHelper {

    private EditText editText;
    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;

    public DateTimeHelper() {

    }

    public DateTimeHelper(EditText editText) {
        this.editText = editText;
    }

    public DateTimeHelper(EditText editText, int year, int month, int dayOfMonth) {
        this.editText = editText;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public DateTimeHelper(EditText editText, int hourOfDay, int minute) {
        this.editText = editText;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public DateTimeHelper(EditText editText, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        this.editText = editText;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
