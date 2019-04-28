package com.cadproject.hackdroid;

import android.app.ProgressDialog;
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
import com.pd.chocobar.ChocoBar;

public class ForgetPassword extends AppCompatActivity {
    EditText emailInput;
    Button reset;
    String email ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        emailInput = (EditText)findViewById(R.id.email);
        reset = (Button) findViewById(R.id.reset);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // progressDialog.
              email = emailInput.getText().toString();
              if (TextUtils.isEmpty(email))
              {
                  emailInput.setError("Reqiured");
              }else {
                  progressDialog.setMessage("Please wait while password reset  email is being send to " + email);
                  progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            showChocoToast("Email has been send to "+email);
                        }else{
                           // showChocoToastError();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        showChocoToastError(email + "is not registered with us");
                    }
                });
              }

            }
        });

    }
    private  void   showChocoToast(String Msg){
        ChocoBar.builder().setActivity(ForgetPassword.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .green()
                .show();
    }
    private  void   showChocoToastError(String Msg){
        ChocoBar.builder().setActivity(ForgetPassword.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .red()
                .show();
    }
}
