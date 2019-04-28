package com.cadproject.hackdroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.app.CustomAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pd.chocobar.ChocoBar;

import java.util.HashMap;

public class GalleryHolder extends AppCompatActivity {
Intent intent;
String string;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
ImageView imageView;
PhotoView photoView;
Button calc , wishlist;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
String CurrentUser =null;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_holder);
        intent = getIntent();
        string = intent.getStringExtra("url");
        //Toast.makeText(getApplicationContext() , ""+string , Toast.LENGTH_SHORT).show();
        firebaseDatabase  =FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Plan").child(string);
        //imageView = (ImageView)findViewById(R.id.image);
        photoView = (PhotoView)findViewById(R.id.image);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        CurrentUser  =firebaseUser.getUid();
        calc = (Button)findViewById(R.id.budgetCalc);
        calc .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext()  ,"calculate" , Toast.LENGTH_SHORT).show();
                Intent calc = new Intent(getApplicationContext() , BudgetCalc.class);
                calc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(calc);
               finish();
            }
        });
        wishlist = (Button)findViewById(R.id.wish);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishlist(CurrentUser , string);
            }
        });

        loadDataFeature();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private void addToWishlist(final String currentUser, final String string) {
        final HashMap<String , String>wishlist =new HashMap<String, String>();
        final DatabaseReference databaseReferenceWishlist = firebaseDatabase.getReference().child("Wishlist");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext() , ""+dataSnapshot , Toast.LENGTH_SHORT).show();
                final String planTitle = dataSnapshot.child("PlanTitle").getValue().toString();
                String PlanImage = dataSnapshot.child("PlanImage").getValue().toString();
                String planPlotSize = dataSnapshot.child("PlanPlotSize").getValue().toString();
                String PlanDimen = dataSnapshot.child("PlanDimen").getValue().toString();
                String planId = string;
                wishlist.put("user" , currentUser);
                wishlist.put("title" , planTitle);
                wishlist.put("image" , PlanImage);
                wishlist.put("dimen" ,PlanDimen);
                wishlist.put("size" , planPlotSize);
                wishlist.put("id" , planId);
                //Toast.makeText(getApplicationContext() , ""+wishlist , Toast.LENGTH_SHORT).show();
                databaseReferenceWishlist.push().setValue(wishlist) .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
    showChocoToast(planTitle + " has been added to wishlist");
                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void   showChocoToast(String Msg){
        ChocoBar.builder().setActivity(GalleryHolder.this)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .green()
                .show();
    }
    private void loadDataFeature() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // Toast.makeText(getApplicationContext() , ""+dataSnapshot , Toast.LENGTH_SHORT).show();
                Glide.with(getApplicationContext()).load(dataSnapshot.child("PlanImage").getValue().toString()).placeholder(R.drawable.cardinal_point).into(photoView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            //imageView.setScaleX(mScaleFactor);
            //imageView.setScaleY(mScaleFactor);
            return true;
        }
    }

}
