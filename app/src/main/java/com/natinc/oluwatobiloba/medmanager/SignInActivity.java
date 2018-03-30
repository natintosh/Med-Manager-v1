package com.natinc.oluwatobiloba.medmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    // Field Constants
    private final static int RC_SIGN_IN = 123;
    private final static String LOG_TAG = SignInActivity.class.getSimpleName();

    // Firebase instance variables
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize firebase variable
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (hasUserSignIn(mFirebaseUser)) {
            launchDashBoardIntent();
        } else {
            List<AuthUI.IdpConfig> providerList = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providerList)
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
                            .build(),
                    RC_SIGN_IN);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
//                startActivity(DashBoardActivity.createIntent(this, response));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(LOG_TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Initialize firebase variable
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            Intent dashBoardIntent = new Intent(SignInActivity.this, DashBoardActivity.class);
            dashBoardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(dashBoardIntent);
        }
    }

    private void showSnackbar(int msg) {
        Snackbar.make(findViewById(R.id.sign_in_constraint_layout),
                getResources().getString(msg),
                Snackbar.LENGTH_LONG)
                .show();
    }

    private boolean hasUserSignIn(FirebaseUser mFirebaseUser) {
        return mFirebaseUser != null;
    }

    private void launchDashBoardIntent() {
        Intent dashBoardIntent = new Intent(SignInActivity.this, DashBoardActivity.class);
        dashBoardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dashBoardIntent);
    }
}
