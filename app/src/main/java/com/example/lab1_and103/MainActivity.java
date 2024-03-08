package com.example.lab1_and103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSignUpEmail, btnSignUpPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        btnSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInEmailScreen.class));
            }
        });

        btnSignUpPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInPhoneScreen.class));
            }
        });
    }

    public void Mapping() {
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);
        btnSignUpPhone = findViewById(R.id.btnSignUpPhone);
    }
}