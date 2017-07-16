package com.example.admin.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button bSignIn;
    Button bSignUp;
    Button bForgetPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        bForgetPassword = (Button) findViewById(R.id.bForgetPass);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        bForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });


    }


    public void signIn() {

        String sEmail = etEmail.getText().toString();
        String sPassword = etPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            getUserDetails();
                        } else {
                            Toast.makeText(SignInActivity.this,
                                    "Authentification Failed ", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    private void getUserDetails() {

        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = fbDatabase
                .getReference()
                .child("users")
                .child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);

                SPUserDetails save = new SPUserDetails(SignInActivity.this);
                save.setKEY_USER_NAME(user.getName());

                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                i.putExtra("INTENT_NAME",user.getName());
                startActivity(i);
                finish();

                Log.d("Sign In", "onDataChange:" + user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
