package com.example.lab1_and103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class SignInEmailScreen extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private TextView txtForgotPass, txtSignUp;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email_screen);
        Mapping();

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();

                if(email.equals("") || password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInEmailScreen.this);
                    builder.setTitle("Warning");
                    builder.setMessage("Username and Password Cannot Be Empty");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInEmailScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // get info
                            FirebaseUser user = auth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInEmailScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInEmailScreen.this, HomePageScreen.class));
                            finishAffinity(); // Đóng tất cả các tab trước Main_Activity
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInEmailScreen.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInEmailScreen.this, SignUpEmailScreen.class));
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogForgot();
            }
        });

    }

    private void ShowDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.forgot_password, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        EditText edtSendEmail = view.findViewById(R.id.edtSendEmail);
        Button btnSendEmail = view.findViewById(R.id.btnSendEmail);
        Button btnCancel = view.findViewById(R.id.btnBackToLogin);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                alertDialog.dismiss(); // đóng alertdialog
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                progressDialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = edtSendEmail.getText().toString().trim();

                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInEmailScreen.this, "Password Sent To Email Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void Mapping() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);
    }
}