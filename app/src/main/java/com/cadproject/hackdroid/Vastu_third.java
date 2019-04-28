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

import static com.cadproject.hackdroid.VastuCalculator.q2;
import static com.cadproject.hackdroid.VastuCalculator.q3;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_third extends Fragment {

    Animation animSlide;
    View view;
    CardView cardView;
    RadioGroup radioGroup;
    public Vastu_third() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_vastu_third, container, false);
        cardView = (CardView)view.findViewById(R.id.card);
        //load the animation
        animSlide = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in);
        cardView.startAnimation(animSlide);
        //Radio Group
        /*
        *  <OPTION VALUE="10">Bed Room</OPTION>
        <OPTION VALUE="10">Drawing Room</OPTION>
        <OPTION VALUE="15">Kitchen</OPTION>
        <OPTION VALUE="5">Master Bed Room</OPTION>
        <OPTION VALUE="10">Puja Room</OPTION>
        <OPTION VALUE="10">Staircase</OPTION>
        <OPTION VALUE="5">Toilet</OPTION></SELECT></TD>

        * */
        q3  =   Float.valueOf(0);
        radioGroup = view.findViewById(R.id.radio_answer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_10 :
                        //Bed Room is selected
                        q3= Float.valueOf(10);
                        break;
                    case R.id.radio_20:
                        //drawning room
                        q3 = Float.valueOf(10);
                        break;
                    case R.id.radio_5_Neg :
                        //kitchen
                        q3 = Float.valueOf(15);
                        break;
                    case R.id.radio_0:
                        //master bed room
                        q3 = Float.valueOf(5);
                        break;
                    case    R.id.radio_20_puja :
                        q3 = Float.valueOf(10);
                        break;
                    case   R.id.radio_0_staircase:
                        q3 = Float.valueOf(10);
                        break;
                    case  R.id.radio_5_neg :
                        //Toilet
                        q3 = Float.valueOf(5);
                        break;
                    default:
                        q3=Float.valueOf(0);
                        break;
                }
                Toast.makeText(getContext() , ""+q3, Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

}
