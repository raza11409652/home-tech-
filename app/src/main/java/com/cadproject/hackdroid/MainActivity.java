/*
 * hackdroidbykhan 24th feb 2019
 * */

package com.cadproject.hackdroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {

    //check for user logged in status and redirect to login screen if not
    //else show button to go to login screen
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
    Button getStarted , loginWithEmail;
    ProgressDialog progressDialog;
    Animation  animation;
    CardView cardView;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ... verifying credential");
        progressDialog.setTitle("CAD");
        //progressDialog.show();
       // cardView = (CardView) findViewById(R.id.card);
        logo = (ImageView)findViewById(R.id.card);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in );
       // cardView.setAnimation(animation);
        logo.setAnimation(animation);
        getStarted = (Button)findViewById(R.id.get_started);
       loginWithEmail=(Button) findViewById(R.id.loginWithEmail);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (firebaseUser !=null){
                    isAdmin(firebaseUser);
                }else{
                    progressDialog.dismiss();
                    Intent login_admin = new Intent(getApplicationContext() , LoginUser.class);
                    startActivity(login_admin);
                }

            }
        });
        loginWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login_admin = new Intent(getApplicationContext() , Login.class);
                startActivity(login_admin);
            }
        });
    }

    private void isAdmin(FirebaseUser firebaseUser) {
        String user=firebaseUser.getUid();
        databaseReference.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String , String> result  =new HashMap<>();
                result= (Map<String, String>) dataSnapshot.getValue();
                // showChocoToast(""+result);
                String isAdminstatus= result.get("isAdmin");
                //showChocoToast(isAdminstatus);
                if (isAdminstatus.equalsIgnoreCase("false")){
                    progressDialog.dismiss();
                    Intent dash = new Intent(getApplicationContext()  , DashUser.class);
                    dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dash);
                    finish();
                }else {
                    firebaseAuth.signOut();
                    Intent login_admin = new Intent(getApplicationContext() , LoginUser.class);
                    startActivity(login_admin);
                    //finish();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
