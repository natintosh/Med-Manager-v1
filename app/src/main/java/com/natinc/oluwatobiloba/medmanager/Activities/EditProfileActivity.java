package com.natinc.oluwatobiloba.medmanager.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import lib.kingja.switchbutton.SwitchMultiButton;

public class EditProfileActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER_INTENT = 1001;
    private static final String LOG_TAG = EditProfileActivity.class.getSimpleName();
    // Member varibles
    String mName;
    String mEmail;
    String mGender;
    String mPhoneNumber;

    ImageView mEditProfileImage;
    SwitchMultiButton mSwitchMultiButton;
    TextView mEditNameTextView, mEditEmailTextView;
    EditText mNameTextEditText, mPhoneNumberTextEditText;
    FloatingActionButton mContinueFab;
    SpotsDialog mProgressDialog;

    // Firebase memeber variables
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseStorage mFirebaseStorage;
    FirebaseFirestore mFirebaseFirestore;
    StorageReference mStorageRef;
    UploadTask mUploadTask;

    Uri mLocalImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        intantiatingVarible();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mStorageRef = mFirebaseStorage.getReference();

        if (mFirebaseUser.getDisplayName() != null) {
            mName = mFirebaseUser.getDisplayName();
            mEditEmailTextView.setVisibility(View.VISIBLE);
            mNameTextEditText.setVisibility(View.INVISIBLE);
        } else {
            mEditEmailTextView.setVisibility(View.INVISIBLE);
            mNameTextEditText.setVisibility(View.VISIBLE);
        }

        loadProfileImage();

        mEditProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RC_PHOTO_PICKER_INTENT);
            }
        });

        mEmail = mFirebaseUser.getEmail();

        mEditNameTextView.setText(mName);
        mEditEmailTextView.setText(mEmail);

        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                mGender = tabText;
            }
        });

        onNameEditTextChange();
        onPhoneNumberEditTextChange();

        mContinueFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInstanceVarible();
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);
                updateAndStoreUser();
                startActivity(new Intent(EditProfileActivity.this, DashBoardActivity.class));
            }
        });

    }

    private void updateInstanceVarible() {
        if (mFirebaseUser.getDisplayName() == null) {
            mName = mNameTextEditText.getText().toString();
        }
        if (mPhoneNumberTextEditText.getText().toString().equals(getResources().getText(R.string.phone_number_default_value))) {
            mPhoneNumber = null;
        } else {
            mPhoneNumber = mPhoneNumberTextEditText.getText().toString();
        }
    }

    private void updateAndStoreUser() {
        String imageLabel = "IMG_" + mFirebaseUser.getUid();
        StorageReference profileImagesRef = mStorageRef.child("images/" + imageLabel);

//        mEditProfileImage.setDrawingCacheEnabled(true);
//        mEditProfileImage.buildDrawingCache();
//        Bitmap bitmap = mEditProfileImage.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        mUploadTask = profileImagesRef.putBytes(data);

        mUploadTask = profileImagesRef.putFile(mLocalImageUri);


        // Register observers to listen for when the download is done or if it fails
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                mProgressDialog.cancel();
                Toast.makeText(EditProfileActivity.this, "Error occured while updating profile", Toast.LENGTH_LONG)
                        .show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                updateUserProfile(mFirebaseUser, downloadUrl);
            }
        });
    }

    private void updateUserProfile(FirebaseUser user, final Uri profileImageUri) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .setPhotoUri(profileImageUri)
                .build();

        mFirebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            User user = new User(mName, mEmail, mGender, mPhoneNumber, profileImageUri.toString());
                            mFirebaseFirestore.collection("Users").document(mFirebaseUser.getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(LOG_TAG, "DocumentSnapshot successfully written!");
                                            Toast.makeText(EditProfileActivity.this, "Profile registration completed", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(LOG_TAG, "Error writing document", e);
                                }
                            });
                        }
                    }
                });

        mProgressDialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_PHOTO_PICKER_INTENT:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        mLocalImageUri = imageUri;
                        Picasso.get().load(imageUri).into(mEditProfileImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap imageBitmap = ((BitmapDrawable) mEditProfileImage.getDrawable()).getBitmap();
                                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                imageDrawable.setCircular(true);
                                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                mEditProfileImage.setImageDrawable(imageDrawable);
                            }

                            @Override
                            public void onError(Exception e) {
                                mEditProfileImage.setImageResource(R.drawable.default_profile);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "onActivityResult: ", e);
                    }
                }
        }
    }

    private void loadProfileImage() {
        if (mFirebaseUser.getPhotoUrl() != null) {
            Picasso.get().load(mFirebaseUser.getPhotoUrl())
                    .resize(100, 100)
                    .into(mEditProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) mEditProfileImage.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            mEditProfileImage.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {
                            mEditProfileImage.setImageResource(R.drawable.default_profile);
                        }
                    });
        } else {
            Picasso.get().load(R.drawable.default_profile)
                    .resize(100, 100)
                    .into(mEditProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) mEditProfileImage.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            mEditProfileImage.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {
                            mEditProfileImage.setImageResource(R.drawable.default_profile);
                        }
                    });
        }
    }

    private void intantiatingVarible() {
        mEditProfileImage = findViewById(R.id.edit_profile_image);
        mPhoneNumberTextEditText = findViewById(R.id.edit_phone_number_text_edit_text);
        mNameTextEditText = findViewById(R.id.edit_name_text_edit_text);
        mSwitchMultiButton = findViewById(R.id.switchmultibutton);
        mEditNameTextView = findViewById(R.id.edit_name_text_view);
        mEditEmailTextView = findViewById(R.id.edit_email_text_view);
        mContinueFab = findViewById(R.id.continue_fab);
        mProgressDialog = new SpotsDialog(EditProfileActivity.this);
    }

    private void onNameEditTextChange() {
        mNameTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mName = mNameTextEditText.getText().toString();
            }
        });
    }

    private void onPhoneNumberEditTextChange() {
        mPhoneNumberTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                mPhoneNumber = mPhoneNumberTextEditText.getText().toString();
            }
        });
    }

}
