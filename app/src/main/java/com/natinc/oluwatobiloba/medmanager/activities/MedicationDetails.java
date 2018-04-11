package com.natinc.oluwatobiloba.medmanager.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.models.Medication;

import java.util.Calendar;

public class MedicationDetails extends AppCompatActivity {

    private static final String TAG = MedicationDetails.class.getSimpleName();
    TextView mName, mIcon, mDescription, mNumberOfPills, mDose, mInterval, mStart, mEnd;
    Calendar mCalendar;
    Medication mMedication;
    String mId;
    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirestore;
    private String name;
    private int color;
    private String description;
    private String numberOfPills;
    private String dose;
    private long interval;
    private long start;
    private long end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);

        Intent getActivityIntent = getIntent();

        mMedication = getActivityIntent.getParcelableExtra(Intent.EXTRA_PACKAGE_NAME);
        mId = getActivityIntent.getStringExtra(Intent.EXTRA_UID);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        name = mMedication.getName();
        color = mMedication.getColor();
        description = mMedication.getDescription();
        numberOfPills = mMedication.getNumberOfPills();
        dose = mMedication.getDose();
        interval = mMedication.getInterval();
        start = mMedication.getStart();
        end = mMedication.getEnd();

        mName = findViewById(R.id.medication_details_name);
        mIcon = findViewById(R.id.medication_details_icon);
        mDescription = findViewById(R.id.medication_details_description);
        mNumberOfPills = findViewById(R.id.medication_details_pills);
        mDose = findViewById(R.id.medication_details_dose);
        mInterval = findViewById(R.id.medication_details_interval);
        mStart = findViewById(R.id.medication_details_start);
        mEnd = findViewById(R.id.medication_details_end);

        mName.setText(name);
        ((GradientDrawable) mIcon.getBackground()).setColor(color);
        mIcon.setText(String.valueOf(name.charAt(0)).toUpperCase());
        mDescription.setText(description);
        mNumberOfPills.setText(numberOfPills);
        mDose.setText(dose);
        String timeFormat = (interval) / (60 * 60) + ":" + ((interval) % (60 * 60)) / 60;
        mInterval.setText(timeFormat);

        setDate(mStart, start);
        setDate(mEnd, end);
    }

    private void setDate(TextView tv, long milliseconds) {
        mCalendar = Calendar.getInstance();

        mCalendar.setTimeInMillis(milliseconds);
        String date = DateFormat.getLongDateFormat(this).format(mCalendar.getTime());
        tv.setText(date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_delete_medication) {
            mFirestore.collection("Users").document(mFirebaseUser.getUid())
                    .collection("Medications").document(mId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MedicationDetails.this, "Successfully deleted " + name, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MedicationDetails.this, DashBoardActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MedicationDetails.this, "Error occurred while deleting", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }

}
