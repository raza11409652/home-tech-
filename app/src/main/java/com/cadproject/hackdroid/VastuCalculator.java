package com.cadproject.hackdroid;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VastuCalculator extends AppCompatActivity {
   FrameLayout mainFrame;
   public static FloatingActionButton floatingActionButton;
    public static Integer MaxUser=0;
    TextView question_no;
    //public  static String q1=null , q2=null , q3=null , q4=null , q5=null , q6= null  , q7=null  ,q8=null ,q9=null ,q10=null;
    public  static Float q1=null , q2=null , q3=null , q4=null , q5=null , q6= null  , q7=null  ,q8=null ,q9=null ,q10=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vastu_calculator);
        mainFrame = (FrameLayout)findViewById(R.id.mainFrame);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        question_no = (TextView)findViewById(R.id.question_no);
        loadFragment(new Vastu_first());
        MaxUser =0;
        question_no.setText("1/10");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
            MaxUser = MaxUser+1;
                switch(MaxUser){

                    case 0:
                        question_no.setText(MaxUser+1 + "/10");
                        loadFragment(new Vastu_first());
                        break;
                    case 1:
                        loadFragment(new Vastu_second());
                        question_no.setText(MaxUser+1 + "/10");
                        break;
                    case 2:
                        question_no.setText(MaxUser+1 + "/10");
                        loadFragment(new Vastu_third());
                        break;
                    case 3 :
                        question_no.setText(MaxUser+1 + "/10");
                        loadFragment(new Vastu_four());
                        break;
                    case  4:
                        question_no.setText(MaxUser+1 + "/10");
                        loadFragment(new Vastu_fifth());
                        break;
                    case  5:
                        question_no.setText(MaxUser+1 + "/10");
                        loadFragment(new Vastu_sixth());
                        break;
                    case 6 :
                        question_no.setText(MaxUser+1 +"/10");
                        loadFragment(new Vastu_seven());
                        break;
                        case 7:
                        question_no.setText(MaxUser+1 +"/10");
                        loadFragment(new Vastu_eight());
                        break; case 8:
                        question_no.setText(MaxUser+1 +"/10");
                        loadFragment(new Vastu_nine());
                        break;case 9:
                        question_no.setText(MaxUser+1 +"/10");
                        loadFragment(new Vastu_ten());
                        break;
                    case 10 :
                        question_no.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.GONE);
                        loadFragment(new Vastu_result());
                        break;

                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
