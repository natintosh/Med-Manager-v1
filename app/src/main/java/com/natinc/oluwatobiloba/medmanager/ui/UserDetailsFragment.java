package com.natinc.oluwatobiloba.medmanager.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.natinc.oluwatobiloba.medmanager.R;

public class UserDetailsFragment extends Fragment {

    FirebaseUser mFirebaseUser;

    ImageView mUserProfileImageView;
    TextView mUsernameTextView;
    TextView mUserEmailTextView;

    public UserDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_details, container, false);
        mUserProfileImageView = rootView.findViewById(R.id.user_details_profile_image);
        mUsernameTextView = rootView.findViewById(R.id.user_details_username);
        mUserEmailTextView = rootView.findViewById(R.id.user_details_email);

        return rootView;
    }
}
