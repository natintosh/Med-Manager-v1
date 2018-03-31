package com.natinc.oluwatobiloba.medmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import lib.kingja.switchbutton.SwitchMultiButton;

public class EditProfileActivity extends AppCompatActivity {

    // Member varibles
    String mName;
    String mEmail;
    String mGender;
    String mPhoneNumber;

    SwitchMultiButton mSwitchMultiButton;
    TextView mEditNameTextView, mEditEmailTextView;
    TextInputLayout mPhoneNumberTextInputLayout;
    TextInputEditText mPhoneNumberTextInputEditText;
    FloatingActionButton mContinueFab;
    CircleImageView mProfilePictureCircleImageView;

    // Firebase memeber variables
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mPhoneNumberTextInputLayout = findViewById(R.id.edit_phone_number_text_input);
        mPhoneNumberTextInputEditText = findViewById(R.id.edit_phone_number_text_edit_text);
        mSwitchMultiButton = findViewById(R.id.switchmultibutton);
        mEditNameTextView = findViewById(R.id.edit_name_text_view);
        mEditEmailTextView = findViewById(R.id.edit_email_text_view);
        mProfilePictureCircleImageView = findViewById(R.id.edit_profile_image);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser.getDisplayName() != null) {
            mName = mFirebaseUser.getDisplayName();
        }
        mEmail = mFirebaseUser.getEmail();

        mEditNameTextView.setText(mName);
        mEditEmailTextView.setText(mEmail);

        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                mGender = tabText;
            }
        });

        mProfilePictureCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Clicked", Toast.LENGTH_SHORT);
            }
        });


        mPhoneNumberTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                mPhoneNumber = mPhoneNumberTextInputEditText.getText().toString();
            }
        });
    }

    public void clickedCircleImageView(View view) {
        Toast.makeText(EditProfileActivity.this, "Clicked", Toast.LENGTH_SHORT);
    }
}
