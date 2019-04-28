package com.cadproject.hackdroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.Model.UserData;
import com.cadproject.hackdroid.app.Constant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Wishlist extends AppCompatActivity {
FirebaseUser firebaseUser;
FirebaseAuth firebaseAuth;
FirebaseDatabase database;
DatabaseReference databaseReference;
String curentUser =null;
RecyclerView recyclerView;

Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser  = firebaseAuth.getCurrentUser();
        curentUser = firebaseUser.getUid();
        setTitle("Wishlist");
        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=database.getReference().child("Wishlist");
        query = databaseReference.orderByChild("user") .equalTo(curentUser);
        loadData();

    }

    private void loadData() {
        FirebaseRecyclerAdapter<UserData, DataRecycler > adapter = new FirebaseRecyclerAdapter<UserData , DataRecycler>(
                UserData.class ,
                R.layout.single_wishlist,
               DataRecycler.class,
                query
        ){


            @Override
            protected void populateViewHolder(DataRecycler viewHolder, final UserData model, final int position) {
                viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(getApplicationContext() , "REmove" , Toast.LENGTH_SHORT).show();
                        final String KeyId=getRef(position).getKey().toString();
                        databaseReference.child(KeyId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext() , "Removed from wishlist" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String KeyId=model.getId();

                        //DatabaseReference  databaseReference = query.getRef();
                        Intent gallery = new Intent(getApplicationContext() , GalleryHolder.class);
                        gallery.putExtra("url" , KeyId);
                        startActivity(gallery);
                        //Toast.makeText(getApplicationContext() , ""+ query.getRef() , Toast.LENGTH_SHORT).show();

                    }
                });
              //  Constant.currentPlanWishlist[i] = model.getId().toString();
                viewHolder.SetTile(model.getTitle());
                viewHolder.setPlotSize(model.getSize());
                viewHolder.setDimen(model.getDimen());
                viewHolder.setImage(model.getImage() , getApplicationContext());
            }
        };
     recyclerView.setAdapter(adapter);
    }
    public static class DataRecycler extends RecyclerView.ViewHolder {
        View view;
        Button remove;

        public DataRecycler(View itemView) {

            super(itemView);

            view=itemView;
            remove = (Button)view.findViewById(R.id.remove);


        }
        public void SetTile(String  title){
            TextView fetchData=(TextView)view.findViewById(R.id.title);
            fetchData.setText(title);
            //return Date;
        }
        public void setImage(String Image , Context ctx)
        {
            ImageView imageView =(ImageView) view.findViewById(R.id.image_layout);
            Glide.with(ctx).load(Image).placeholder(R.drawable.cardinal_point).into(imageView);
        }
        public void setDimen(String Dimen){
            TextView dimen = (TextView)view.findViewById(R.id.dimen);
            dimen.setText(Dimen);
        }
        public void setPlotSize(String PlotSize){
            TextView plotSize = (TextView)view.findViewById(R.id.plotsize);
            plotSize.setText(PlotSize);
        }

    }
}
