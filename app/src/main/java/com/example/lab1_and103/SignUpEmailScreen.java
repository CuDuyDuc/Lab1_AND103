package com.example.lab1_and103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class SignUpEmailScreen extends AppCompatActivity {
    private EditText edtEmailSignUp, edtPasswordSignUp, edtConfirmPasswordSignUp;
    private Button btnSignUp;
    private TextView txtLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email_screen);
        Mapping();

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmailSignUp.getText().toString().trim();
                String pass = edtPasswordSignUp.getText().toString().trim();
                String confirmPass = edtConfirmPasswordSignUp.getText().toString().trim();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();

                if(email.equals("") || pass.equals("") || confirmPass.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpEmailScreen.this);
                    builder.setTitle("Warning");
                    builder.setMessage("The data field cannot be Empty");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if(!pass.equals(confirmPass)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpEmailScreen.this);
                    builder.setTitle("Warning");
                    builder.setMessage("Password and Confirm Password are not the same.\n Please Check Again!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUpEmailScreen.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // get info
                                FirebaseUser user = auth.getCurrentUser();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUpEmailScreen.this, "Authentication Successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpEmailScreen.this, SignInEmailScreen.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpEmailScreen.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpEmailScreen.this, SignInEmailScreen.class));
            }
        });
    }

    public void Mapping() {
        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtConfirmPasswordSignUp = findViewById(R.id.edtConfirmPasswordSignUp);
        txtLogin = findViewById(R.id.txtLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
}