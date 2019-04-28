/*
 * hackdroidbykhan 24th feb 2019
 * */

package com.cadproject.hackdroid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.Model.PlanData;
import com.cadproject.hackdroid.app.Constant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pd.chocobar.ChocoBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cadproject.hackdroid.app.Constant.Dimensions;
import static com.cadproject.hackdroid.app.Constant.height;

public class DashUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    private ToggleButton toggleButton;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    TextView name , email;
    String userName  , userEmail;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;
    Button btnSearch;
    EditText width   , length;
    AlertDialog alertDialog;
    RecyclerView featuredPlan;
    public List<PlanData> planData;
    Query query;
    ViewFlipper viewFlipper;
   // FirebaseDatabase firebaseDatabasePlan;
    public   DatabaseReference databaseReferencePlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_user);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        //textview name and email
        View header=navigationView.getHeaderView(0);
        name = (TextView)header.findViewById(R.id.username);
        email = (TextView)header.findViewById(R.id.useremail);
        int  images[] ={R.drawable.slider_1 , R.drawable.slider_2 , R.drawable.slider_3 , R.drawable.slider_4 , R.drawable.slider_5};
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
        for (int image:images){
            flipperImage(image);
        }
        //button
        btnSearch = (Button)findViewById(R.id.search) ;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase  =FirebaseDatabase.getInstance();

        //Edit text
        width = (EditText)findViewById(R.id.width);
        length = (EditText)findViewById(R.id.length);
      //  width.setSelected(false);
        //length.setSelected(false);
        findViewById(R.id.mainLayout).requestFocus();
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReferencePlan = firebaseDatabase.getReference().child("Plan");
        query = databaseReferencePlan.orderByChild("PlanFeatured") .equalTo("true");
        //Alert Dialog
        featuredPlan = (RecyclerView)findViewById(R.id.featured_plan);
        featuredPlan.setHasFixedSize(true);
        featuredPlan.setLayoutManager( new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
       // recyclerViewInclusion.setAdapter(inclusionAdapter);
        //featuredPlan.setLayoutManager(new La);
       // alertDialog =new AlertDialog()
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are You sure you will be logout from this device.");
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        try{
            firebaseUser = firebaseAuth.getCurrentUser();
            uid=firebaseUser.getUid();
            getDetails(uid , firebaseUser);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logout(firebaseAuth);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String widthVal , lengthVal ;
                widthVal  = width.getText().toString().trim();
                lengthVal = length.getText().toString().trim();
                if (TextUtils.isEmpty(widthVal)){
                    //width.setError("Required");
                    //showChocoToast("Width Required" , v);
                    craeteAlertMsg("Width Required");
                    width.setError("Required");

                }else if (TextUtils.isEmpty(lengthVal)){
                    //showChocoToast("Length Required" ,v) ;
                    craeteAlertMsg("Length Required");
                    length.setError("Required");

                }else{
                    searchForCad(widthVal , lengthVal);
                }


            }
        });
loadDataFeature();
    }

    private void flipperImage(int image) {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3500);
        viewFlipper.setAutoStart(true);
        //animation
        viewFlipper.setOutAnimation(getApplicationContext() , android.R.anim.slide_out_right);
        viewFlipper.setInAnimation(getApplicationContext() , android.R.anim.slide_in_left);
    }

    private void loadDataFeature() {
        FirebaseRecyclerAdapter<PlanData , Plan.DataRecycler > adapter = new FirebaseRecyclerAdapter<PlanData , Plan.DataRecycler>(
                PlanData.class ,
                R.layout.single_ftr,
                Plan.DataRecycler.class,
                query
        ){


            @Override
            protected void populateViewHolder(Plan.DataRecycler viewHolder, PlanData model, final int position) {
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
        featuredPlan.setAdapter(adapter);
    }

    private  void craeteAlertMsg(String msg){
        alertDialog = new AlertDialog.Builder(this).create();
       // alertDialog.setTitle("Logout");
        alertDialog.setMessage(msg);

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void logout(FirebaseAuth firebaseAuth) {
        firebaseAuth.signOut();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            Intent mainActivity   =  new Intent(getApplicationContext() , MainActivity.class);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);
            finish();
        }
    }

    private void searchForCad(String widthVal, String lengthVal) {
        /*
        * strat Intent with dimension and  fetch details()
        * */
        Integer width = Integer.valueOf(widthVal);
        Integer length = Integer.valueOf(lengthVal);
        Integer Dimen = width * length;
        //Toast.makeText(getApplicationContext() , ""+Dimen,  Toast.LENGTH_SHORT).show(); ;
        Intent intent = new Intent(getApplicationContext() , Plan.class);
       // intent.putExtra("dimension" , Dimen.toString());
        Dimensions = Dimen.toString();
        Constant.width=width.toString();
        Constant.height = lengthVal;
        startActivity(intent);
        overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );


    }

    private void getDetails(String uid , FirebaseUser firebaseUser) {
        final String mobile = firebaseUser.getPhoneNumber();
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String , String> result  =new HashMap<>();
                result= (Map<String, String>) dataSnapshot.getValue();
                // showChocoToast(""+result);
               // String isAdminstatus= result.get("isAdmin");
                //name = result.get("name");
                userName = result.get("name");
                userEmail = result.get("email");
                //Toast.makeText(getApplicationContext() , ""+userName + userEmail +mobile , Toast.LENGTH_SHORT ).show();
                name.setText(userName);
              email.setText(mobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.home:
                Intent goto_dash= new Intent(getApplicationContext() , DashUser.class);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                goto_dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(goto_dash);
                finish();
                break;
            case  R.id.logout :
                drawerLayout.closeDrawer(navigationView);
                alertDialog.show();
                break;
            case R.id.profile :
                drawerLayout.closeDrawer(navigationView);
                Intent Profile= new Intent(getApplicationContext() , Profile.class);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(Profile);
                break;
                //finish();
            case R.id.vastu :
                //drawerLayout.closeDrawer(navigationView);
                Intent vastu = new Intent(getApplicationContext() , VastuCalculator.class);
                vastu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(vastu);
                break;
            case R.id.wish:
                Intent wishlist= new Intent(getApplicationContext() , Wishlist.class);
                wishlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(wishlist);
                break;
            case R.id.order:
                Intent order= new Intent(getApplicationContext() , Order.class);
                order.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(order);
                break;
            case R.id.about :
                Intent about = new Intent(getApplicationContext() , About.class);
                about.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(about);
                break;
            case R.id.budgetCalc :
                Intent budget = new Intent(getApplicationContext() , BudgetCalc.class);
                budget.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom ,android.support.design.R.anim.abc_slide_in_top  );
                startActivity(budget);
                break;
        }
        return false;
    }
    private  void   showChocoToast(String Msg , View v){
        ChocoBar.builder().setView(v)
                .setText(Msg)
                .setDuration(ChocoBar.LENGTH_SHORT)
                .orange()
                .show();
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
