package com.cadproject.hackdroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
FirebaseUser firebaseUser;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
FirebaseAuth firebaseAuth;
String currentUser , nameVal , emailVal , mobileVal ;
TextView nameView , emailView  , mobileView;
ProgressDialog progressDialog;
Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser  = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
        //views
        nameView = (TextView)findViewById(R.id.name);
        mobileView = (TextView)findViewById(R.id.mobile);
        emailView = (TextView)findViewById(R.id.email);
        progressDialog = new ProgressDialog(this);
        logout = (Button)findViewById(R.id.logout);
        progressDialog.setMessage("Profile is being loading .. ");
        progressDialog.show();
        if (firebaseAuth == null){
           /*
           * user session doesn't exist
           * */
           logout();
        }else{
            loadProfile(firebaseUser);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        firebaseAuth.signOut();
        Intent mainActivity   =  new Intent(getApplicationContext() , MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(mainActivity);
        finish();
    }

    private void loadProfile(final FirebaseUser firebaseUser) {
        currentUser = firebaseUser.getUid();
        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    Map<String , String> user  = new HashMap<>();
                    user = (Map<String, String>) dataSnapshot.getValue();
                    nameVal = user.get("name");
                    emailVal  =user.get("email");
                    mobileVal = firebaseUser.getPhoneNumber();
                    nameView.setText(nameVal);
                    emailView.setText(emailVal);
                    mobileView.setText(mobileVal);
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
