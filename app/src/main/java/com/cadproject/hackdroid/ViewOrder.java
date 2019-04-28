package com.cadproject.hackdroid;

import android.content.Context;
import android.content.Intent;
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
import com.cadproject.hackdroid.Model.OrderData;
import com.cadproject.hackdroid.Model.PlanData;
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

public class ViewOrder extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView data;
    Query query;
    List<OrderData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        setTitle("Order view");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference().child("Order");
    //   query = databaseReference.orderByChild("email") ;
        data = (RecyclerView)findViewById(R.id.data);
        data.setHasFixedSize(true);
       // data.setLayoutManager(new LinearLayoutManager(this));
        loadOrder();
    }


    private void loadOrder() {
   databaseReference.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        list = new ArrayList<>();
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
        {
           // Toast.makeText(getApplicationContext() , ""+dataSnapshot1 , Toast.LENGTH_SHORT).show();
            OrderData orderData = dataSnapshot1.getValue(OrderData.class);
            String email  = orderData.getEmail();
            String height=orderData.getHeight();
            String width = orderData.getHeight();
            String mobile = orderData.getMobile();
            String req = orderData.getRequirement();
            String user = orderData.getUser();
            list.add(orderData);
        }
           RecyclerviewAdapter recycler = new RecyclerviewAdapter(list , getApplicationContext());
           RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
           data.setLayoutManager(layoutmanager);
          data.setItemAnimator( new DefaultItemAnimator());
           data.setAdapter(recycler);

         //  Toast.makeText(getApplicationContext() , ""+list , Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });
    }

    public static class DataRecycler extends RecyclerView.ViewHolder {
        View view;

        public DataRecycler(View itemView) {

            super(itemView);

            view = itemView;


        }
    }
}
