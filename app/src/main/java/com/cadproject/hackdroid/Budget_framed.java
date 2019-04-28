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
import android.widget.TextView;

import static com.cadproject.hackdroid.BudgetCalc.formationType;
import static com.cadproject.hackdroid.BudgetCalc.result;


/**
 * A simple {@link Fragment} subclass.
 */
public class Budget_framed extends Fragment {
View view;
TextView val   , type;
Animation animation;
CardView cardView;
Button btnHome;
    public Budget_framed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_budget_framed, container, false);
        cardView = (CardView)view.findViewById(R.id.card);

        animation= AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in);
        cardView.setAnimation(animation);
        val = (TextView)view.findViewById(R.id.value);
        type = (TextView)view.findViewById(R.id.formationType);
        val .setText("Rs."+result +" Approx.");
        type.setText(formationType);
        btnHome = (Button)view.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getContext() , DashUser.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(home);
            }
        });
        return  view;
    }

}
