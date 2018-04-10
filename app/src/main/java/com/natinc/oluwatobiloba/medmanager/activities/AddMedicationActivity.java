package com.natinc.oluwatobiloba.medmanager.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.models.Medication;
import com.natinc.oluwatobiloba.medmanager.ui.DatePickerFragment;
import com.natinc.oluwatobiloba.medmanager.ui.TimePickerFragment;
import com.natinc.oluwatobiloba.medmanager.utils.DateTimeHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddMedicationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextInputLayout mNameLayout, mDescriptionLayout,
            mPillsLayout, mDoseLayout, mIntervalLayout, mStartLayout, mEndLayout;

    EditText mNameEditText, mDescriptionEditText, mNumberEditText,
            mPillsEditText, mDoseEditText, mIntervalEditText, mStartEditText, mEndEditText;

    Calendar mCalendar;

    DateTimeHelper mDateTimeHelper;

    String mNameOfDrug;
    int mColor;
    String mDescription;
    String mNumberOfPills;
    String mDose;
    long mInterval;
    Date mStart;
    Date mEnd;

    // Firebase Instance Variable
    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        initializingVariables();

        mIntervalEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timepicker");
                mDateTimeHelper = new DateTimeHelper();
                mDateTimeHelper.setEditText(mIntervalEditText);
            }
        });

        mStartEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datepicker");
                mDateTimeHelper = new DateTimeHelper();
                mDateTimeHelper.setEditText(mStartEditText);
            }
        });

        mEndEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datepicker");
                mDateTimeHelper = new DateTimeHelper();
                mDateTimeHelper.setEditText(mEndEditText);
            }
        });
    }

    private boolean isErrorInInput() {
        mNameOfDrug = mNameEditText.getText().toString();
        Random mRandom = new Random();
        mColor = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        mDescription = mDescriptionEditText.getText().toString();
        mDose = mDoseEditText.getText().toString();
        mNumberOfPills = mPillsEditText.getText().toString();

        if (mNameOfDrug.isEmpty()) {
            mNameLayout.setError("Enter a name");
        } else if (mDescription.isEmpty()) {
            mDescriptionLayout.setError("Enter a brief description about the drug");
        } else if (mNumberOfPills.isEmpty()) {
            mPillsLayout.setError("Enter total number of drugs");
        } else if (mDose.isEmpty()) {
            mDoseLayout.setError("Enter amount of dose");
        } else if (mInterval == 0) {
            mIntervalLayout.setError("Enter the drug interval or frequency");
        } else if (mStart == null) {
            mStartLayout.setError("Enter a date");
        } else if (mEnd == null) {
            mEndLayout.setError("Enter a date");
        } else {
            mNameLayout.setErrorEnabled(false);
            mDescriptionLayout.setErrorEnabled(false);
            mPillsLayout.setErrorEnabled(false);
            mDoseLayout.setErrorEnabled(false);
            mIntervalLayout.setErrorEnabled(false);
            mStartLayout.setErrorEnabled(false);
            mEndLayout.setErrorEnabled(false);
            return false;
        }
        return true;
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

        mIntervalEditText.setFocusable(false);
        mStartEditText.setFocusable(false);
        mEndEditText.setFocusable(false);
        mIntervalEditText.setInputType(InputType.TYPE_NULL);
        mStartEditText.setInputType(InputType.TYPE_NULL);
        mEndEditText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add_medication) {
            if (!isErrorInInput()) {
                Medication medication = new Medication(mNameOfDrug, mColor, mDescription, mNumberOfPills, mDose, mInterval, mStart, mEnd);
                mFirestore.collection("Users").document(mFirebaseUser.getUid())
                        .collection("Medications").add(medication)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String successMessage = "Successfully added new medication";
                                Toast.makeText(AddMedicationActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddMedicationActivity.this, DashBoardActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String errorMessage = "Error occurred while adding medication";
                                Toast.makeText(AddMedicationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "timePicker");
        mDateTimeHelper.setYear(year);
        mDateTimeHelper.setMonth(month);
        mDateTimeHelper.setDayOfMonth(dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mDateTimeHelper.setHourOfDay(hourOfDay);
        mDateTimeHelper.setMinute(minute);

        mCalendar = Calendar.getInstance();
        mCalendar.set(mDateTimeHelper.getYear(), mDateTimeHelper.getMonth(), mDateTimeHelper.getDayOfMonth(),
                mDateTimeHelper.getHourOfDay(), mDateTimeHelper.getMinute());

        String date = DateFormat.getDateFormat(this).format(mCalendar.getTime());
        mDateTimeHelper.getEditText().setText(date);

        if (mDateTimeHelper.getEditText().equals(mIntervalEditText)) {
            mInterval = hourOfDay * 60 * 60 + minute * 60;
            String intervalString = hourOfDay + ":" + minute;
            mDateTimeHelper.getEditText().setText(intervalString);
        } else if (mDateTimeHelper.getEditText().equals(mStartEditText)) {
            mStart = mCalendar.getTime();
        } else if (mDateTimeHelper.getEditText().equals(mEndEditText)) {
            mEnd = mCalendar.getTime();
        }
    }
}
