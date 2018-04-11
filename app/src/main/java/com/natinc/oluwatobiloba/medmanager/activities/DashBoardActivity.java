package com.natinc.oluwatobiloba.medmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.ui.MedicationListFragment;
import com.natinc.oluwatobiloba.medmanager.ui.UserDetailsFragment;

public class DashBoardActivity extends AppCompatActivity {

    FloatingActionButton addFab;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        addFab = findViewById(R.id.add_fab);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoardActivity.this, AddMedicationActivity.class);
                startActivity(intent);
            }
        });

        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.user_details_container, userDetailsFragment)
                .commit();

        MedicationListFragment medicationListFragment = new MedicationListFragment();

        fragmentManager.beginTransaction()
                .add(R.id.medication_list_container, medicationListFragment)
                .commit();
    }
}
