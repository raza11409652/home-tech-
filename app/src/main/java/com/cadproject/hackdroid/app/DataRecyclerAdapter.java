package com.cadproject.hackdroid.app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cadproject.hackdroid.R;

public class DataRecyclerAdapter extends RecyclerView.ViewHolder {
    View view;

    public DataRecyclerAdapter(View itemView) {

        super(itemView);

        view=itemView;


    }
    public void SetTile(String  title){
        TextView fetchData=(TextView)view.findViewById(R.id.title);
        fetchData.setText(title);
        //return Date;
    }

}
