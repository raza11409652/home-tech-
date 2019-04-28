package com.cadproject.hackdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.cadproject.hackdroid.Model.PlanData;

public class DashAdminPanel extends AppCompatActivity {
CardView upload , list , order ,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_admin_panel);
        upload = (CardView)findViewById(R.id.upload);
        list = (CardView)findViewById(R.id.list);
        order = (CardView)findViewById(R.id.order);
        logout = (CardView)findViewById(R.id.logout);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(getApplicationContext() , UploadPlan.class);
                upload.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(upload);

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(getApplicationContext() , ListPlan.class);
                list.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(list);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(getApplicationContext() , ViewOrder.class);
                order.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(order);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lgout = new Intent(getApplicationContext() , MainActivity.class);
                lgout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lgout);
                finish();
            }
        });
    }
}
