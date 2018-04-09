package com.natinc.oluwatobiloba.medmanager.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.ui.DatePickerFragment;
import com.natinc.oluwatobiloba.medmanager.ui.TimePickerFragment;
import com.natinc.oluwatobiloba.medmanager.utils.DateTimeHelper;

import java.util.Calendar;

public class AddMedicationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextInputLayout mNameLayout, mDescriptionLayout,
            mPillsLayout, mDoseLayout, mIntervalLayout, mStartLayout, mEndLayout;

    EditText mNameEditText, mDescriptionEditText, mNumberEditText,
            mPillsEditText, mDoseEditText, mIntervalEditText, mStartEditText, mEndEditText;

    DateTimeHelper mDateTimeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        initializingVariables();
        invalidateInputs();


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

    private void invalidateInputs() {

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
            // Validate inputs and add medications to firebase
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(mDateTimeHelper.getYear(), mDateTimeHelper.getMonth(), mDateTimeHelper.getDayOfMonth(),
                mDateTimeHelper.getHourOfDay(), mDateTimeHelper.getMinute());

        String date = DateFormat.getDateFormat(this).format(calendar);

        mDateTimeHelper.getEditText().setText(date);
    }
}
