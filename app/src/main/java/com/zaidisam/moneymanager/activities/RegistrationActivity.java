package com.zaidisam.moneymanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zaidisam.moneymanager.R;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email,password;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private TextView registerQn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerbtn);
        registerQn = findViewById(R.id.registerQn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        registerQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                if(TextUtils.isEmpty(emailString))
                {
                    email.setError("Email is Required");
                }
                if(TextUtils.isEmpty(passwordString))
                {
                    email.setError("Password is Required");
                }
                else
                {
                    progressDialog.setMessage("Registration In Progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                Toast.makeText(RegistrationActivity.this,"User Created , Log In Now ",Toast.LENGTH_SHORT).show();
                                finish();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();

                            }
                        }

                    });

                }

            }
        });
    }
}