package com.natinc.oluwatobiloba.medmanager.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.ui.UserDetailsFragment;
import com.natinc.oluwatobiloba.medmanager.utils.FirebaseUtils;

public class DashBoardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        FirebaseUser firebaseUser = FirebaseUtils.getFirebaseUser();

        Log.v("USer Name", firebaseUser.getDisplayName());

        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.user_details_container, userDetailsFragment)
                .commit();


    }
}
