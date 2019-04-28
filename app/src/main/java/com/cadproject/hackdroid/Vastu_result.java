package com.cadproject.hackdroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.cadproject.hackdroid.VastuCalculator.q1;
import static com.cadproject.hackdroid.VastuCalculator.q10;
import static com.cadproject.hackdroid.VastuCalculator.q2;
import static com.cadproject.hackdroid.VastuCalculator.q3;
import static com.cadproject.hackdroid.VastuCalculator.q4;
import static com.cadproject.hackdroid.VastuCalculator.q5;
import static com.cadproject.hackdroid.VastuCalculator.q6;
import static com.cadproject.hackdroid.VastuCalculator.q7;
import static com.cadproject.hackdroid.VastuCalculator.q8;
import static com.cadproject.hackdroid.VastuCalculator.q9;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vastu_result extends Fragment {
View view;
CardView cardView;
Animation animation;
Button btn_home;
RatingBar ratingBar;
TextView vastuResult ;

    public Vastu_result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_vastu_result, container, false);
        cardView = (CardView)view.findViewById(R.id.card);
        animation = AnimationUtils.loadAnimation(getContext() , R.anim.fade_in);

        ratingBar = (RatingBar)view.findViewById(R.id.rating);
        cardView.startAnimation(animation);
        //button
        btn_home = (Button)view.findViewById(R.id.btnHome);
        //Text view
        vastuResult = (TextView)view.findViewById(R.id.resultView);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getContext() , DashUser.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                getActivity().finish();
            }
        });
        //ratingBar.setRating((float) q1);
        Float result = q1+q2+q3+q4+q5 + q6+q7+q8+q9 + q10;
        setRating(result);
        vastuResult.setText(""+result+"/100");
        //setRating(String val)
        return view;
    }
    private void setRating (Float val){
        /*
        * if val == 100 than its equals to 5
        * 1 = 5/100
        * x = 5/100 * x
        * */
       double res = 0.05*val;
        //Toast.makeText(getContext() , ""+res , Toast.LENGTH_SHORT).show();
        ratingBar.setRating((float) res);
    }
}
