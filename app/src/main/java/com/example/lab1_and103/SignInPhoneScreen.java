package com.example.lab1_and103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInPhoneScreen extends AppCompatActivity {
    private Button btnGetOTP, btnLoginWithOTP;
    private EditText edtPhone, edtOTP;

    FirebaseAuth auth;

    private String verificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_phone_screen);
        Mapping();

        auth = FirebaseAuth.getInstance();
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                edtOTP.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = s;
            }
        };

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOTP(edtPhone.getText().toString());
            }
        });

        btnLoginWithOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifiOTP(edtOTP.getText().toString());
            }
        });
    }

    public void getOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber("+84"+phoneNumber)
                                    .setTimeout(120L, TimeUnit.SECONDS)
                                    .setActivity(this)
                                    .setCallbacks(callbacks).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifiOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignInPhoneScreen.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = task.getResult().getUser();
                    startActivity(new Intent(SignInPhoneScreen.this, HomePageScreen.class));
                } else {
                    Toast.makeText(SignInPhoneScreen.this, "Đăng Nhập Thâts Bại"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Mapping() {
        btnGetOTP = findViewById(R.id.btnGetOTP);
        btnLoginWithOTP = findViewById(R.id.btnLoginWithOTP);
        edtPhone = findViewById(R.id.edtPhone);
        edtOTP = findViewById(R.id.edtOTP);
    }
}