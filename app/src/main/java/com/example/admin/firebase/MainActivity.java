package com.example.admin.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView tvDetails;
    Button bSignOut;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase FireDatabase;

    DatabaseReference DataRefrence;

    String TAG  = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //save data offline

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Intent i =getIntent();

        FireDatabase = FirebaseDatabase.getInstance();

        DataRefrence = FireDatabase.getReference();


        Query q = DataRefrence.child("users")
                .orderByChild("email")
                .equalTo("abc@gmail.com");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                Log.d("MainActivity",dataSnapshot.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        String name = i.getStringExtra("INTENT_NAME");
        tvDetails = (TextView) findViewById(R.id.tvUserDetails);
        bSignOut = (Button) findViewById(R.id.bsignout);

        SPUserDetails save = new SPUserDetails(MainActivity.this);
        tvDetails.setText(save.getKEY_USER_NAME());

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };

        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void signOut(){
        mAuth.signOut();


    }
}
