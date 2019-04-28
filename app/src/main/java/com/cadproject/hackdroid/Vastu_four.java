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

import static com.cadproject.hackdroid.VastuCalculator.q4;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_four extends Fragment {

    Animation animSlide;
    View view;
    CardView cardView;
    RadioGroup radioGroup;
    public Vastu_four() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_vastu_four, container, false);
        cardView = (CardView)view.findViewById(R.id.card);
        // load the animation
        animSlide = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        cardView.startAnimation(animSlide);
        radioGroup = view.findViewById(R.id.radio_answer);
        q4 = Float.valueOf(0);
        /*
        *  <OPTION VALUE="15">Bed Room</OPTION>
        <OPTION VALUE="15">Drawing Room</OPTION>
        <OPTION VALUE="10">Kitchen</OPTION>
        <OPTION VALUE="0">Master Bed Room</OPTION>
        <OPTION VALUE="15">Puja Room</OPTION>
        <OPTION VALUE="10">Staircase</OPTION>
        <OPTION VALUE="15">Toilet</OPTION></SELECT>
        * */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_bed_10:
                        q4 = Float.valueOf(15);
                        break;
                    case R.id.radio_drawing_room_10:
                        q4 = Float.valueOf(15);
                        break;
                    case R.id.radio_kitchen_15:
                        q4 = Float.valueOf(10);
                       break;
                    case R.id.radio_master_5:
                        q4 = Float.valueOf(0);
                        break;
                    case    R.id.radio_puja_10:
                        q4 = Float.valueOf(15);
                        break;
                    case R.id.radio_0_staircase_10 :
                        q4 = Float.valueOf(10);
                        break;
                    case    R.id.radio_toilet_5 :
                        q4 = Float.valueOf(15);
                        break;
                        default:
                            q4  =Float.valueOf(0);
                            break;
                }
                Toast.makeText(getContext() , ""+q4 , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
