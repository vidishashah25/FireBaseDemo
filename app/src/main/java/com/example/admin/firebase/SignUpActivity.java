package com.example.admin.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmail;
    EditText etPassword;
    Button bSignUp;

    FirebaseAuth mAuth;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignUp = (Button) findViewById(R.id.bSignUp);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    public void signUp(){

        String sName = etName.getText().toString();
        String sEmail = etEmail.getText().toString();
        String sPassword = etPassword.getText().toString();
        userModel = new UserModel(sName,sEmail);

        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            Intent i = new Intent(SignUpActivity.this,MainActivity.class);
//                            startActivity(i);
//                            finish();

                            String key = task.getResult().getUser().getUid();
                            addUserDetails(key);

                        }
                        else {
                            Toast.makeText(SignUpActivity.this,
                                    "Authentification Failed ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void addUserDetails(String key){

        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = fbDatabase.getReference();
        databaseReference.child("users")
                .child(key)
                .setValue(userModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                            Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                }
                else {


                    Toast.makeText(SignUpActivity.this,
                            "Database Failed ",Toast.LENGTH_SHORT).show();
                    mAuth.getCurrentUser().delete();
                }

            }
        });
    }

    @Override


    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(i);
        finish();



    }
}
