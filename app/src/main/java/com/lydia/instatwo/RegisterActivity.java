package com.lydia.instatwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mReference;
    ProgressDialog mDialog;

    Button mRegister;
    TextView mMember;
    EditText mUsername, mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        init();

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Please wait");
                mDialog.show();

                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (!username.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                            if (password.length() >= 6) {
                                register(username, email, password);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Minimum password length should be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            mDialog.dismiss();
                            mPassword.setError("password cannot be empty");
                            mPassword.requestFocus();
                        }

                    } else {
                        mDialog.dismiss();
                        mEmail.setError("Email cannot be empty");
                        mEmail.requestFocus();
                    }

                } else {
                    mDialog.dismiss();
                    mUsername.setError("Username cannot be empty");
                    mUsername.requestFocus();
                }
            }
        });
    }

    private void register(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            mReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", name);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/instatwo-3efe2.appspot.com/o/IMG_20190527_124401_3.jpg?alt=media&token=c31c3c78-99fe-4d58-8e67-decf326916dd");

                            mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "error" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    private void init() {
        mUsername = findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mRegister = findViewById(R.id.btn_Reg);
        mMember = findViewById(R.id.alreadyAmember);
    }
}



