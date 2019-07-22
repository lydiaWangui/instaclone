package com.lydia.instatwo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    FirebaseUser mUser;


    Button mLogin, mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mLogin = findViewById(R.id.btnLogin);
        mRegister = findViewById(R.id.btnRegister);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirects user to login activity

                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mUser!=null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
}



