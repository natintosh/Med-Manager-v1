package com.natinc.oluwatobiloba.medmanager.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.natinc.oluwatobiloba.medmanager.R;

public class AddMedicationActivity extends AppCompatActivity {

    TextInputLayout mNameLayout, mDescriptionLayout,
            mPillsLayout, mDoseLayout, mIntervalLayout, mStartLayout, mEndLayout;

    EditText mNameEditText, mDescriptionEditText, mNumberEditText,
            mPillsEditText, mDoseEditText, mIntervalEditText, mStartEditText, mEndEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        initializingVariables();
    }

    private void initializingVariables() {
        mNameLayout = findViewById(R.id.input_layout_add_medication_name);
        mDescriptionLayout = findViewById(R.id.input_layout_add_medication_description);
        mPillsLayout = findViewById(R.id.input_layout_add_medication_pills);
        mDoseLayout = findViewById(R.id.input_layout_add_medication_dose);
        mIntervalLayout = findViewById(R.id.input_layout_add_medication_interval);
        mStartLayout = findViewById(R.id.input_layout_add_medication_start_date);
        mEndLayout = findViewById(R.id.input_layout_add_medication_end_date);

        mNameEditText = findViewById(R.id.input_add_medication_name);
        mDescriptionEditText = findViewById(R.id.input_add_medication_description);
        mPillsEditText = findViewById(R.id.input_add_medication_pills);
        mDoseEditText = findViewById(R.id.input_add_medication_dose);
        mIntervalEditText = findViewById(R.id.input_add_medication_interval);
        mStartEditText = findViewById(R.id.input_add_medication_start_date);
        mEndEditText = findViewById(R.id.input_add_medication_end_date);

    }

    
}
