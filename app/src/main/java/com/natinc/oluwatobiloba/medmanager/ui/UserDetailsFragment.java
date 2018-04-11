package com.natinc.oluwatobiloba.medmanager.ui;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.activities.EditProfileActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class UserDetailsFragment extends Fragment {

    private static final String TAG = UserDetailsFragment.class.getSimpleName();

    FirebaseUser mFirebaseUser;

    ImageView mUserProfileImageView;
    TextView mUsernameTextView;
    TextView mUserEmailTextView;
    ImageView mUserEditprofileImageView;

    public UserDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_details, container, false);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserProfileImageView = rootView.findViewById(R.id.user_details_profile_image);
        mUsernameTextView = rootView.findViewById(R.id.user_details_username);
        mUserEmailTextView = rootView.findViewById(R.id.user_details_email);
        mUserEditprofileImageView = rootView.findViewById(R.id.edit_profile_image);

        mUsernameTextView.setText(mFirebaseUser.getDisplayName());
        mUserEmailTextView.setText(mFirebaseUser.getEmail());

        if (mFirebaseUser.getPhotoUrl() != null) {
            Picasso.get().load(this.mFirebaseUser.getPhotoUrl())
                    .resize(120, 120)
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

        }

        mUserEditprofileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra(Intent.EXTRA_REFERRER_NAME, "Details");
                startActivity(intent);

            }
        });
        return rootView;
    }
}
