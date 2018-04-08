package com.natinc.oluwatobiloba.medmanager.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUtils {

    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mFirebaseUser;

    public static FirebaseAuth getFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        return mFirebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        mFirebaseUser = getFirebaseAuth().getCurrentUser();
        return mFirebaseUser;
    }
}
