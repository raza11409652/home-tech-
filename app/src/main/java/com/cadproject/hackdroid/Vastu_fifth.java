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

import static com.cadproject.hackdroid.VastuCalculator.q5;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_fifth extends Fragment {

View view;
CardView cardView;
Animation animation;
RadioGroup radioGroup;
    public Vastu_fifth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view= inflater.inflate(R.layout.fragment_vastu_fifth, container, false);
      cardView = (CardView) view.findViewById(R.id.card);
      animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in );
      cardView.startAnimation(animation);
      /*
      * <OPTION VALUE="5">North</OPTION>
        <OPTION VALUE="5">North East</OPTION>
        <OPTION VALUE="5">East</OPTION>
        <OPTION VALUE="3">South East</OPTION>
        <OPTION VALUE="2">South</OPTION>
        <OPTION VALUE="0">South West</OPTION>
        <OPTION VALUE="2">West</OPTION>
        <OPTION VALUE="3">North West</OPTION></SELECT>
      * */
      q5=Float.valueOf(0);
      radioGroup=(RadioGroup)view.findViewById(R.id.radio_answer);
      radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId){
                  case R.id.radio_north :
                      q5 = Float.valueOf(5);
                      break;
                  case  R.id.radio_north_east :
                      q5 = Float.valueOf(5);
                      break;
                  case R.id.radio_east :
                      q5 = Float.valueOf(5);
                      break;

                  case R.id.radio_south_east :
                      q5 = Float.valueOf(3);
                      break;
                  case R.id.radio_south :
                      q5 = Float.valueOf(2);
                      break;
                  case R.id.radio_south_west :
                      q5 = Float.valueOf(0);
                      break;
                  case R.id.radio_west :
                      q5 = Float.valueOf(2);
                      break;
                  case R.id.radio_north_west:
                      q5 = Float.valueOf(3);
                      break;
              }
              Toast.makeText(getContext() , ""+q5 , Toast.LENGTH_SHORT).show();
          }
      });
      return view;
    }

}
