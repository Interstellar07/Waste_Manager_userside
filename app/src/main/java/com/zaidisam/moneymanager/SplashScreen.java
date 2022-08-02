package com.zaidisam.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zaidisam.moneymanager.activities.LoginActivity;
import com.zaidisam.moneymanager.activities.MainActivity;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }, 00);
        }
        else {
            new Handler().postDelayed(() -> {
                //ami tomake bhalo baashi
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }, 00);


        }
    }
}