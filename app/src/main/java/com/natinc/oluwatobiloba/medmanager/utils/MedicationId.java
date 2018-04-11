package com.natinc.oluwatobiloba.medmanager.utils;

import android.support.annotation.NonNull;

public class MedicationId {

    public String medicationId;

    public <T extends MedicationId> T withId(@NonNull final String id) {
        this.medicationId = id;
        return (T) this;
    }
}
