package com.cadproject.hackdroid.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cadproject.hackdroid.Model.OrderData;
import com.cadproject.hackdroid.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder>{

    List<OrderData> listdata;
    Context context;

    public RecyclerviewAdapter(List<OrderData> listdata , Context context) {
        this.listdata = listdata;

        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order,parent,false);

        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        final OrderData data = listdata.get(position);
        holder.email.setText( data.getEmail());
        holder.mobile.setText(data.getUser());
        holder.height.setText("Required Legth : "+data.getHeight());
        holder.width.setText("Required Width : " + data.getWidth());
        holder.contactMobile.setText(data.getMobile());
        holder.req.setText("Requirements : "+data.getRequirement());

        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + data.getUser().trim() ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView email ,mobile , contactMobile  , width , height ,req  ;

        public MyHolder(View itemView) {
            super(itemView);
         email = (TextView)itemView.findViewById(R.id.email);
         mobile = (TextView)itemView.findViewById(R.id.mobile);
         contactMobile = (TextView)itemView.findViewById(R.id.contactMobile);
         width  = (TextView)itemView . findViewById(R.id.width);
         height = (TextView)itemView.findViewById(R.id.height);
         req = (TextView)itemView.findViewById(R.id.requirement);

        }
    }


}