package com.cadproject.hackdroid.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.OrderData;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.PlanViewerAdmin;
import com.cadproject.hackdroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.security.Key;
import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyHolder>  {
    List<PlanData> listdata;
    Context context;
    Query query;
    DatabaseReference databaseReference , widhlistref;
   FirebaseDatabase firebaseDatabase;
    public PlanRecyclerViewAdapter(List<PlanData> listdata, Context context ,DatabaseReference databaseReference) {
        this.listdata = listdata;
        this.context = context;
       // this.query = query;
        this.databaseReference = databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        widhlistref = firebaseDatabase.getReference().child("Wishlist");

    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_plan_with_del,viewGroup,false);

      MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
       // databaseReference =
        final PlanData data = listdata.get(i);
        myHolder.planTitle.setText(data.getPlanTitle());
        myHolder.plotSize.setText(data.getPlanPlotSize());
        myHolder.dimen.setText(data.getPlanDimen());
        Glide.with(context).load(data.getPlanImage()).placeholder(R.drawable.cardinal_point).into(myHolder.imageView);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context , PlanViewerAdmin.class);
                Constant.image = data.getPlanImage();
                view.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(view);
            }
        });
        myHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String KeyId=Constant.currentPlan[i];
                  //  Toast.makeText(context , ""+KeyId , Toast.LENGTH_SHORT).show();
                widhlistref.removeValue();
                    databaseReference.child(KeyId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(context , "Reomved" , Toast.LENGTH_SHORT).show();
                        }
                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context , "Errror" , Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        myHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String KeyId=Constant.currentPlan[i];
                databaseReference.child(KeyId).child("PlanFeatured").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context , "success" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        myHolder.ftrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String KeyId=Constant.currentPlan[i];
                databaseReference.child(KeyId).child("PlanFeatured").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context , "success" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView ;
        TextView planTitle ,dimen , plotSize;
        Button button ,ftrBtn , delBtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image_layout);
            planTitle = (TextView)itemView.findViewById(R.id.title);
            dimen = (TextView)itemView.findViewById(R.id.dimen);
            plotSize = (TextView)itemView.findViewById(R.id.plotsize);
            button = (Button)itemView.findViewById(R.id.deletePlan);
           // button.setOnClickListener(e);
            ftrBtn = (Button)itemView.findViewById(R.id.makefeature);
            delBtn = (Button)itemView.findViewById(R.id.delfeature);

        }
    }
}
