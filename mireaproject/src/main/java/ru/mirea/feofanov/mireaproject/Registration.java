package ru.mirea.feofanov.mireaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.feofanov.mireaproject.databinding.ActivityRegistrationBinding;


public class Registration extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();
    // START declare_auth
    public static FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Initialization views
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// [START initialize_auth] Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
// [END initialize_auth]
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(String.valueOf(binding.EmailField.getText()), String.valueOf(binding.PasswordField.getText()));
            }
        });
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(String.valueOf(binding.EmailField.getText()), String.valueOf(binding.PasswordField.getText()));
            }
        });
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        binding.verifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
            }
        });
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //Intent intent = new Intent(Registration.this, MainActivity.class);
            //startActivity(intent);
            //finish();
//            binding.statusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail(), user.isEmailVerified()));
//            binding.detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//            binding.button2.setVisibility(View.GONE);
//            binding.PasswordField.setVisibility(View.GONE);
//            binding.signIn.setVisibility(View.GONE);
//            binding.signOut.setVisibility(View.VISIBLE);
//            binding.verifyEmailButton.setVisibility(View.VISIBLE);
//            binding.verifyEmailButton.setEnabled(!user.isEmailVerified());
        } else {
            binding.statusTextView.setText(R.string.signed_out);
            binding.detailTextView.setText(null);
            binding.PasswordField.setVisibility(View.VISIBLE);
            binding.button2.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setVisibility(View.GONE);
            binding.EmailField.setVisibility(View.VISIBLE);
            binding.signIn.setVisibility(View.VISIBLE);
            binding.signOut.setVisibility(View.GONE);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
// [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user'sinformation
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
// If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure",
                            task.getException());
                    Toast.makeText(Registration.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
// [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
// [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user'sinformation
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Registration.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            updateUI(user);
                        } else {
// If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
// [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            binding.statusTextView.setText(R.string.auth_failed);
                        }
// [END_EXCLUDE]
                    }
                });
// [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
// Disable button
        binding.verifyEmailButton.setEnabled(false);
// Send verification email
// [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// [START_EXCLUDE]
// Re-enable button
                        binding.verifyEmailButton.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(Registration.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Registration.this,
                                    "Failed to send verification email.",

                                    Toast.LENGTH_SHORT).show();
                        }
// [END_EXCLUDE]
                    }
                });
// [END send_email_verification]
    }
}