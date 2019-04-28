package com.cadproject.hackdroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.app.Constant;
import com.cadproject.hackdroid.app.PlanRecyclerViewAdapter;
import com.cadproject.hackdroid.app.RecyclerviewAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPlan extends AppCompatActivity {
RecyclerView recyclerView;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
PlanRecyclerViewAdapter recycler;
Query query;
    public List<PlanData> planDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plan);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Plan");
        query = databaseReference.orderByChild("PlanDimen") ;
        recyclerView = (RecyclerView)findViewById(R.id.data);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.setItemAnimator( new DefaultItemAnimator());
       // planData = new ArrayList<>();
        loadPlan();



    }

    private void loadPlan() {
      query.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              planDataList = new ArrayList<>();
              int i=0;
            long size=  dataSnapshot.getChildrenCount();
              Constant.currentPlan =new String[(int) size];
              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                 // Toast.makeText(getApplicationContext() , ""+dataSnapshot1.getValue() , Toast.LENGTH_SHORT).show();
                    PlanData planData = dataSnapshot1.getValue(PlanData.class);
                Constant.currentPlan[i]=   dataSnapshot1.getKey();
                   planDataList.add(planData);
                   i++;
              }
              recycler = new PlanRecyclerViewAdapter(planDataList    , getApplicationContext() , databaseReference);
              RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
              recyclerView.setLayoutManager(layoutmanager);
              recyclerView.setItemAnimator( new DefaultItemAnimator());
              recyclerView.setAdapter(recycler);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }

}
