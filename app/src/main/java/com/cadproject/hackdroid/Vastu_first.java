package com.cadproject.hackdroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.cadproject.hackdroid.VastuCalculator.q1;



public class Vastu_first extends Fragment {

Animation animation;
CardView cardView;
View view;
RadioGroup radioGroup;
    public Vastu_first() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_vastu_first, container, false);
      animation=  AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
      cardView = (CardView)view.findViewById(R.id.card);
      cardView.startAnimation(animation);
        q1=Float.valueOf(0);
      //Radio Group
        radioGroup = view.findViewById(R.id.radio_answer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_10 :
                        //Bed Room is selected
                        q1 = Float.valueOf(10);
                        break;
                    case R.id.radio_20:
                        q1 = Float.valueOf(20);
                        break;
                    case R.id.radio_5_Neg :
                        q1 = Float.valueOf(-5);
                        break;
                    case R.id.radio_0:
                    q1 = Float.valueOf(0);
                    break;
                    case    R.id.radio_20_puja :
                        q1 = Float.valueOf(20);
                        break;
                    case   R.id.radio_0_staircase:
                        q1 = Float.valueOf(0);
                        break;
                    case  R.id.radio_5_neg :
                        q1 = Float.valueOf(-5);
                        break;
                    default:
                        q1=Float.valueOf(0);
                        break;
                }
                Toast.makeText(getContext() , ""+q1 , Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }

}
