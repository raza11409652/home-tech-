package com.cadproject.hackdroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pd.chocobar.ChocoBar;

import java.util.HashMap;

public class Order extends AppCompatActivity {
    EditText mobilenumber , email , height , width , requirment;
    String mobile , emailVAl , heightVal , widthVal , requirementVal;
    Button upload;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Order");
        upload   =(Button)findViewById(R.id.save);
        mobilenumber = (EditText) findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email) ;
        height = (EditText)findViewById(R.id.height);
        width = (EditText)findViewById(R.id.width);
        requirment = (EditText)findViewById(R.id.requirement);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = mobilenumber.getText().toString();
                emailVAl = email.getText().toString();
                heightVal = height.getText().toString();
                widthVal = width.getText().toString();
                requirementVal = requirment.getText().toString();
                if(TextUtils.isEmpty(mobile))
                {
                    mobilenumber.setError("Required");
                    return;
                }else if(TextUtils.isEmpty(emailVAl))
                {
                    email.setError("Required");
                    return;
                }else if(TextUtils.isEmpty(widthVal))
                {
                    width.setError("Required");
                }
                else if(TextUtils.isEmpty(heightVal))
                {
                    height.setError("Required");
                }else if(TextUtils.isEmpty(requirementVal))
                {
                    requirment.setError("Required");
                }else {
                    startInit(mobile , emailVAl , heightVal , widthVal , requirementVal);
                }

            }

        });
    }

    private void startInit(String mobile, String emailVAl, final String heightVal, final String widthVal, String requirementVal) {
        String currentUser=null;
             currentUser   = firebaseUser.getPhoneNumber().toString();
        HashMap<String , String>map = new HashMap<>();
        map.put("mobile" , mobile);
        map.put("email" , emailVAl);
        map.put("height" , heightVal);
        map.put("width" , widthVal);
        map.put("requirement"  ,requirementVal);
        map.put("user" , currentUser);
        databaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                showChocoToast("Your Order has been send  , Thankyou");
                mobilenumber.setText("");
                email.setText("");
                width.setText("");
                height.setText("");
                requirment.setText("");
                   // Intent reload =  new Intent(getApplicationContext() , Order.class);
                    //reload.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(reload);
                    //finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private  void   showChocoToast(String Msg){
        ChocoBar.builder().setActivity(Order.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .green()
                .show();
    }
}
