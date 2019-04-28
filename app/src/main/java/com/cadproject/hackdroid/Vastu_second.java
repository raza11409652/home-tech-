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
import static com.cadproject.hackdroid.VastuCalculator.q2;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_second extends Fragment {
    Animation animSlide;
    View view;
    CardView cardView;
    RadioGroup radioGroup;
    public Vastu_second() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_vastu_second, container, false);
       cardView = (CardView)view.findViewById(R.id.card);
        // load the animation
        animSlide = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        cardView.startAnimation(animSlide);
        q2=Float.valueOf(0);
        //Radio Group
        /*
        *  <OPTION VALUE="" SELECTED="SELECTED">----Select----</OPTION>
        <OPTION VALUE="15">Bed Room</OPTION>
        <OPTION VALUE="10">Drawing Room</OPTION>
        <OPTION VALUE="-5">Kitchen</OPTION>
        <OPTION VALUE="20">Master Bed Room</OPTION>
        <OPTION VALUE="10">Puja Room</OPTION>
        <OPTION VALUE="15">Staircase</OPTION>
        <OPTION VALUE="5">Toilet</OPTION></SELECT></TD>

        * */
        radioGroup = view.findViewById(R.id.radio_answer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_10 :
                        //Bed Room is selected
                        q2= Float.valueOf(15);
                        break;
                    case R.id.radio_20:
                        q2 = Float.valueOf(10);
                        break;
                    case R.id.radio_5_Neg :
                        q2 = Float.valueOf(-5);
                        break;
                    case R.id.radio_0:
                        q2 = Float.valueOf(20);
                        break;
                    case    R.id.radio_20_puja :
                        q2 = Float.valueOf(10);
                        break;
                    case   R.id.radio_0_staircase:
                        q2 = Float.valueOf(15);
                        break;
                    case  R.id.radio_5_neg :
                        q2 = Float.valueOf(5);
                        break;
                    default:
                        q2=Float.valueOf(0);
                        break;
                }
                Toast.makeText(getContext() , ""+q2 , Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }

}
