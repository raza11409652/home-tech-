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

import static com.cadproject.hackdroid.VastuCalculator.q10;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_ten extends Fragment {
    View view;
    CardView cardView;
    Animation animation;
    RadioGroup radioGroup;

    public Vastu_ten() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_vastu_ten, container, false);
        cardView = (CardView) view.findViewById(R.id.card);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up );
        cardView.startAnimation(animation);
        radioGroup = (RadioGroup)view .findViewById(R.id.radio_answer);
        q10 = Float.valueOf(0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_regualr:
                        q10 = Float.valueOf(5);
                        break;

                    case    R.id.radio_non_regular:
                        q10 = Float.valueOf(2);
                        break;
                }
                Toast.makeText(getContext() , ""+q10 , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
