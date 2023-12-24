package com.example.medimove;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingUp extends AppCompatActivity {
    EditText signupPhone,signupEmail,signupUsername,signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference reference;
    String phone,email,username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        signupPhone =findViewById(R.id.signup_phone);
        signupEmail =findViewById(R.id.signup_email);
        signupUsername =findViewById(R.id.signup_username);
        signupPassword =findViewById(R.id.signup_password);
        signupButton =findViewById(R.id.signup_button);
        loginRedirectText =findViewById(R.id.log_in_Redirect_Text);

        auth= FirebaseAuth.getInstance();
//        String name,email,username,password;

        signupButton.setOnClickListener(v -> {

//            String name =signupName.getText().toString();
//            String email =signupEmail.getText().toString();
//            String username =signupUsername.getText().toString();
//            String password =signupPassword.getText().toString();
            phone = signupPhone.getText().toString();
            email = signupEmail.getText().toString();
            username = signupUsername.getText().toString();
            password = signupPassword.getText().toString();


            if (phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty())
            {
                if (phone.isEmpty()) {
                    signupPhone.setError("Phone cannot be empty");
                }

                if (email.isEmpty()) {
                    signupEmail.setError("Phone cannot be empty");
                }

                if (username.isEmpty()) {
                    signupUsername.setError("Phone cannot be empty");
                }

                if (password.isEmpty()) {
                    signupPassword.setError("Phone cannot be empty");
                }
            }
            else
            {

                HelperClass helperClass = new HelperClass(username,email,phone,password);
//                database = FirebaseDatabase.getInstance();
//                reference = database.getReference("users");

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SingUp.this,"You have signup Successfully!!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SingUp.this, Log.class);
                       startActivity(intent);
                       finish();
                    }
                    else
                    {
                        Toast.makeText(SingUp.this,"Failed to signup!!,Try Again",Toast.LENGTH_SHORT).show();
                    }
                });
                // without authenticator
//                reference.child(username).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        Toast.makeText(SingUp.this,"You have signup Successfully!!",Toast.LENGTH_SHORT).show();
//
//                        signupPhone.getText().clear();
//                        signupEmail.getText().clear();
//                        signupUsername.getText().clear();
//                        signupPassword.getText().clear();
//
//                        Intent intent = new Intent(SingUp.this, Log.class);
//                        startActivity(intent);
//                    }
//                });
            }
        });

        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(SingUp.this,LogIn.class);
            startActivity(intent);
        });
    }
}