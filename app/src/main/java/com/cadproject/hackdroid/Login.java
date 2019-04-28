/*
* hackdroidbykhan 24th feb 2019
* */

package com.cadproject.hackdroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    EditText email , password;
    Button btn , forgetPassword;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        firebaseAuth = FirebaseAuth.getInstance();
    progressDialog = new ProgressDialog(this);
        if (firebaseAuth!=null){
            firebaseAuth.signOut();
        }
        forgetPassword = (Button)findViewById(R.id.forgetPassword);
        btn =  (Button)findViewById(R.id.login);
        email = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.password);
           forgetPassword.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent forget = new Intent(getApplicationContext() , ForgetPassword.class);
                   forget.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(forget);

               }
           });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal, passwordVal;
                emailVal =email.getText().toString();
                passwordVal=password.getText().toString();
                if (TextUtils.isEmpty(emailVal)){
                    email.setError("Required");
                }else if (TextUtils.isEmpty(passwordVal)){
                    password.setError("Required");
                }else{
                    progressDialog.setMessage("Please Wait logging in");
                    loginAdmin(emailVal , passwordVal);
                }
            }
        });


    }

    private void loginAdmin(String emailVal, String passwordVal) {
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailVal , passwordVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent login_admin = new Intent(getApplicationContext() , DashAdminPanel.class);
                    startActivity(login_admin);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext() , ""+e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        }) .addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext() , "Error" , Toast.LENGTH_SHORT).show();

            }
        });
    }

}
