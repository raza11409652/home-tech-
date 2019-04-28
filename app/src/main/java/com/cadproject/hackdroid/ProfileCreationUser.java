/*
 * hackdroidbykhan 24th feb 2019
 * */

package com.cadproject.hackdroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pd.chocobar.ChocoBar;

import java.util.HashMap;
import java.util.Map;

public class ProfileCreationUser extends AppCompatActivity {
Button save ;
EditText name , email ;
String nameVal , emailVal;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
FirebaseUser firebaseUser;
Map<String  , String> user= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation_user);
        //Button
        save = (Button)findViewById(R.id.save);
        //Edit Text
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameVal=name.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                //check for valid email
                if (TextUtils.isEmpty(nameVal)){
                    showChocoToast("Please Enter Name");
                    name.setError("Required");
                }else if(TextUtils.isEmpty(emailVal)){
                    showChocoToast("Please Enter Email");
                    email.setError("Required");
                }else if(isValidEmail(emailVal) ==false){
                    showChocoToast("Please Enter Valid Email");
                    email.setError("Invalid Email");
                }else{
                    saveDeatils(nameVal , emailVal);
                }

            }
        });

    }

    private void saveDeatils(String nameVal, String emailVal) {
        String currentId = firebaseUser.getUid();
                user.put("name" , nameVal);
                user.put("email" , emailVal);
                user.put("isAdmin" , "false");
                DatabaseReference reference;
                reference=databaseReference.child(currentId);
                reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext()  , "Write Success" , Toast.LENGTH_SHORT).show();
                        //send to dash for search query

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showChocoToast(e.getLocalizedMessage());
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                       showChocoToast("Error !!!!");
                    }
                });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private  void   showChocoToast(String Msg){
        ChocoBar.builder().setActivity(ProfileCreationUser.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .orange()
                .show();
    }
}
