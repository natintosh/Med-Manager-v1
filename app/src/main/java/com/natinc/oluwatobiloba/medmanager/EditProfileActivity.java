package com.natinc.oluwatobiloba.medmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import lib.kingja.switchbutton.SwitchMultiButton;

public class EditProfileActivity extends AppCompatActivity {

    String mGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SwitchMultiButton mSwitchMultiButton = findViewById(R.id.switchmultibutton);
        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(EditProfileActivity.this, tabText, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
