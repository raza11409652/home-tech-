package com.cadproject.hackdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BudgetCalc extends AppCompatActivity {
FrameLayout frameLayout;
RelativeLayout relativeLayout;
EditText dimen;
Button btn;
String dimenVal;
AlertDialog alertDialog;
int counter =0;
int fomrationType= 0 , dimenInt , value;
public static String formationType=null;
RadioGroup radioGroup;
public static  String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_calc);
        setTitle("Budget Calculator");
        relativeLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        //relativeLayout.setVisibility(View.GONE);
        frameLayout = (FrameLayout)findViewById(R.id.mainFrame);
        //frameLayout.setVisibility(View.VISIBLE);
        //loadFragment(new Vastu_fifth());
        dimen = (EditText)findViewById(R.id.budgetCalcDimen);
        alertDialog = new AlertDialog.Builder(this).create();
        btn  = (Button)findViewById(R.id.budgetCalcBtn);
        radioGroup = (RadioGroup)findViewById(R.id.type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case   R.id.furnished:
                        fomrationType =3;
                        formationType = "Furnished Structure";
                        value = 2200;
                        break;
                    case R.id.finished:
                        fomrationType =2;
                        formationType = "Finished Structure";
                        value = 1600;
                        break;
                    case R.id.framed:
                        fomrationType=1;
                        formationType = "Framed Structure";
                        value = 900;
                        break;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dimenVal = dimen.getText().toString();
              if (TextUtils.isEmpty(dimenVal)){
                  //alert
                  dimen.setError("Required");
              }else if(fomrationType!=0) {
                  dimenInt = Integer.valueOf(dimenVal);
                  result = ""+ dimenInt *value;
                    switch (fomrationType){
                        case  1:
                            alertDialog.setTitle("Are You Sure ");
                            alertDialog.setMessage("You have entered dimension: "+dimenVal + "sq.ft and selected formation type : "+formationType);

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Calculate", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //make calculation and display result
                                    relativeLayout.setVisibility(View.GONE);
                                    frameLayout.setVisibility(View.VISIBLE);
                                    loadFragment(new Budget_framed());
                                 //   alertDialog.show();
                                }
                            });
                            alertDialog.show();
                            break;
                        case 2:
                            alertDialog.setTitle("Are You Sure ");
                            alertDialog.setMessage("You have entered dimension: "+dimenVal + "sq.ft and selected formation type : "+formationType);

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Calculate", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //make calculation and display result
                                    relativeLayout.setVisibility(View.GONE);
                                    frameLayout.setVisibility(View.VISIBLE);
                                    loadFragment(new Budget_finished());
                                   // alertDialog.show();
                                }
                            });
                            alertDialog.show();
                            break;
                        case 3:
                            alertDialog.setTitle("Are You Sure ");
                            alertDialog.setMessage("You have entered dimension: "+dimenVal + "sq.ft and selected formation type : "+formationType);

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Calculate", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //make calculation and display result
                                    relativeLayout.setVisibility(View.GONE);
                                    frameLayout.setVisibility(View.VISIBLE);
                                    loadFragment(new Budget_furnished());

                                }
                            });
                            alertDialog.show();
                            break;
                    }



              }else{
                  alertDialog.setTitle("Are You Sure ");
                  alertDialog.setMessage("Please Select Formation type to get budget for your House");

                  alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          alertDialog.dismiss();
                      }
                  });

                  alertDialog.show();
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

        counter +=1;
        if (counter >= 2){
            Intent home = new Intent(getApplicationContext() , DashUser.class);
            startActivity(home);
            return;
        }else{
            Toast.makeText(getApplicationContext() , "Press 2 time back key to go to Home" , Toast.LENGTH_SHORT).show();
        }
return;

    }
}
