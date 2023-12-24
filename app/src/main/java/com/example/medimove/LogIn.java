package com.example.medimove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn extends AppCompatActivity {
     EditText loginusername,loginPassword;

     FirebaseAuth auth;
     Button loginButton;
     TextView signupRedirectText,forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginusername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.Sign_up_Redirect_Text);
        loginButton = findViewById(R.id.login_button);
        forgotPassword = findViewById(R.id.forgot_password);

        auth = FirebaseAuth.getInstance();

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SingUp.class);
                startActivity(intent);
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = emailBox.getText().toString();

                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LogIn.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogIn.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LogIn.this, "Unable to send email, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });


        loginButton.setOnClickListener(view -> {
            // not used authenticator
//            if(!validateusername() | !validatePassword())
//            {
//
//            }
//            else
//            {
//                checkUser();
//            }

            String username = loginusername.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            {
                if (username.isEmpty()) {
                    loginusername.setError("Username cannot be empty");
                }
                if (password.isEmpty()) {
                    loginPassword.setError("Password cannot be empty");
                }
            }
            else
            {
                auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LogIn.this,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LogIn.this,"Failed to Log In",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user!= null)
        {
            Intent intent = new Intent(LogIn.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        user.getUid();

    }
    public Boolean validateusername()
    {
        String val =loginusername.getText().toString();
        if(val.isEmpty())
        {
            loginusername.setError("Username cannot be empty");
            return  false;
        }
        else
        {
            loginusername.setError(null);
            return  true;
        }
    }

    public Boolean validatePassword()
    {
        String val =loginPassword.getText().toString();
        if(val.isEmpty())
        {
            loginPassword.setError("Password cannot be empty");
            return  false;
        }
        else
        {
            loginPassword.setError(null);
            return  true;
        }
    }

    public void checkUser() {
        String userUsername = loginusername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    loginusername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginusername.setError(null);
                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginusername.setError("User does not exist");
                    loginusername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}