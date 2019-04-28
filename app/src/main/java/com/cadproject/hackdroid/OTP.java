package com.cadproject.hackdroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pd.chocobar.ChocoBar;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    EditText editText;
    TextView otp_holderView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        String phonenumber = getIntent().getStringExtra("phonenumber");
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        editText=(EditText) findViewById(R.id.pinView);
        progressBar=(ProgressBar)findViewById(R.id.progress_circular_otp);
        otp_holderView = (TextView)findViewById(R.id.otp_holderView);
        try{
            otp_holderView.setText("OTP has been send to you on your mobile number ("+phonenumber+") please enter it below.");
            sendVerificationCode(phonenumber);

        }catch (Exception e){
            e.printStackTrace();
        }

        findViewById(R.id.btn_otp_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();
                if(code.isEmpty() || code.length()<6)
                {
                    // editText.setError("OTP !!!");
                    //editText.requestFocus();
                    //Error message
                    showChocoToast("Please Enter OTP");
                    return;
                }else{
                    verifyCode(code);
                }

            }
        });

    }

    private void sendVerificationCode(String phonenumber) {
        Toast.makeText(getApplicationContext() , phonenumber ,Toast.LENGTH_SHORT).show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber , 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD ,mcallBacks );
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                editText.setText(code);
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
          //  Toast.makeText(OTP.this, e.getMessage(),Toast.LENGTH_LONG).show();
            showChocoToast(e.getMessage().toString());
        }
    };

    private void verifyCode(String code) {
       try  {
           PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
           signInWithCredential(credential);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //database reference
                    //databaseReference
                    //checkUserExist(mAuth.getCurrentUser());
                    progressBar.setVisibility(View.GONE);
                   // Toast.makeText(getApplicationContext() , "done" , Toast.LENGTH_SHORT).show();
                    checkUserExist(mAuth.getCurrentUser());


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
               // Toast.makeText(getApplicationContext() , e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                showChocoToast(e.getLocalizedMessage().toString());
            }
        });
    }

    private void checkUserExist(FirebaseUser currentUser) {
    /*
    * if user exist in database than check whether it is admin or not
    * if user doesn't exist in data base than send them to  create user profile
    * */
        final String user=currentUser.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user)){

                  //  showChocoToast("Done!!!");
                    /*
                    * check whether admin or not
                    *
                    * */
                    isAdmin(user);

                }else{
                /*
                * profile craettion of users
                * */
                    Intent profileCreation = new Intent(getApplicationContext() , ProfileCreationUser.class);
                    profileCreation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(profileCreation);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isAdmin(String user) {

        databaseReference.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String , String> result  =new  HashMap<>();
                result= (Map<String, String>) dataSnapshot.getValue();
               // showChocoToast(""+result);
                String isAdminstatus= result.get("isAdmin");
                showChocoToast(isAdminstatus);
               if (isAdminstatus.equalsIgnoreCase("false")){
                   Intent dash = new Intent(getApplicationContext()  , DashUser.class);
                   dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(dash);
                   finish();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void   showChocoToast(String Msg){
        ChocoBar.builder().setActivity(OTP.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .orange()
                .show();
    }
}
