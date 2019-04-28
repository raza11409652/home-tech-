package com.cadproject.hackdroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.app.Constant;
import com.cadproject.hackdroid.app.DataRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.cadproject.hackdroid.app.Constant.Dimensions;

public class Plan extends AppCompatActivity {
Intent intent;
String dimen , w,h;
RecyclerView data;
    public List<PlanData> planData;
    FirebaseDatabase firebaseDatabase;
   public   DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Query query;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        intent = getIntent();
        dimen = Dimensions;
        w= Constant.width;
        h=Constant.height;
        data = (RecyclerView)findViewById(R.id.data);
    //Toast.makeText(getApplicationContext()  , ""+dimen , Toast.LENGTH_SHORT).show();
    //connect firabse database
    firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference =firebaseDatabase.getReference().child("Plan");
//orderByChild("startTime_endTime")
//.startAt("1490353200_1490353200")
//.endAt("1490353250_1490353250")

     query = databaseReference.orderByChild("PlanDimen").equalTo(dimen) ;
    data .setHasFixedSize(true);
    data.setLayoutManager(new LinearLayoutManager(this));
 planData = new ArrayList<>();
    loadData();

}

    private void loadData() {
    FirebaseRecyclerAdapter<PlanData , DataRecycler >adapter = new FirebaseRecyclerAdapter<PlanData , DataRecycler>(
                PlanData.class ,
                R.layout.single_layout_plan,
                DataRecycler.class,
                query
        ){


        @Override
        protected void populateViewHolder(final DataRecycler viewHolder, final PlanData model, final int position) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String KeyId=getRef(position).getKey().toString();

                    //DatabaseReference  databaseReference = query.getRef();
                    Intent gallery = new Intent(getApplicationContext() , GalleryHolder.class);
                    gallery.putExtra("url" , KeyId);
                    startActivity(gallery);
                    //Toast.makeText(getApplicationContext() , ""+ query.getRef() , Toast.LENGTH_SHORT).show();

                }
            });
        viewHolder.SetTile(model.getPlanTitle());
        viewHolder.setPlotSize(model.getPlanPlotSize());
        viewHolder.setDimen(model.getPlanDimen());
        viewHolder.setImage(model.getPlanImage() , getApplicationContext());
        }
    };
    data.setAdapter(adapter);
    }
    public static class DataRecycler extends RecyclerView.ViewHolder {
        View view;

        public DataRecycler(View itemView) {

            super(itemView);

            view=itemView;


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
