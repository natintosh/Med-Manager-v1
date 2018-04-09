package com.natinc.oluwatobiloba.medmanager.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.natinc.oluwatobiloba.medmanager.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        mFirebaseUser = firebaseUser;
        loadProfileImage(mFirebaseUser);
        mUsernameTextView.setText(mFirebaseUser.getDisplayName());
        mUserEmailTextView.setText(mFirebaseUser.getEmail());
    }

    private void loadProfileImage(FirebaseUser mFirebaseUser) {

        if (mFirebaseUser.getPhotoUrl() == null) {
            Picasso.get().load(this.mFirebaseUser.getPhotoUrl())
                    .resize(100, 100)
                    .into(mUserProfileImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) mUserProfileImageView.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            mUserProfileImageView.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {
                            mUserProfileImageView.setImageResource(R.drawable.default_profile);
                        }
                    });
        } else {
            mUserProfileImageView.setImageResource(R.drawable.default_profile);
        }
    }
}