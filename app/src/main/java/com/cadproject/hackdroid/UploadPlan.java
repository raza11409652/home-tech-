package com.cadproject.hackdroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.UUID;

public class UploadPlan extends AppCompatActivity {
String title , width , height , image_url =null, image_first_url =null, image_Second_url=null , floor ;
ImageButton image , image_first , image_second;
EditText editText_title , width_et , height_et , floor_et ;
Button save;
ProgressDialog progressDialog;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    StorageReference storageReference;
public  static int GALLERY_REQ=7777, GALLERY_REQ_FIRST =2222,GALLERY_REQ_SEC=3888 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_plan);

        //button
        save = (Button)findViewById(R.id.save);
        editText_title = (EditText)findViewById(R.id.plan_title);
        width_et = (EditText)findViewById(R.id.width);
        height_et = (EditText)findViewById(R.id.height);
        floor_et = (EditText)findViewById(R.id.floor);
        progressDialog = new ProgressDialog(this);
        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        ///storage = FirebaseStorage.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Plan");
        storageReference =FirebaseStorage.getInstance().getReference();
        image = (ImageButton)findViewById(R.id.image_title);
        image_first = (ImageButton)findViewById(R.id.image_first);
        image_second = (ImageButton)findViewById(R.id.image_second);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery ,GALLERY_REQ );

            }
        });
        image_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery ,GALLERY_REQ_FIRST );
            }
        });
        image_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery ,GALLERY_REQ_SEC );
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = editText_title.getText().toString();
                width = width_et.getText().toString();
                height = height_et.getText().toString();
                floor = floor_et.getText().toString();
                if (image_url == null){
                    Toast.makeText(getApplicationContext() , "please upload image" , Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(title)){
                    editText_title.setError("Required");
                }else if(TextUtils .isEmpty(width)){
                    width_et.setError("Required");
                }else if(TextUtils.isEmpty(height)){
                    height_et.setError("Required");
                }else if(TextUtils.isEmpty(floor)){
                    floor_et.setError("Required");
                }else {
                    progressDialog.setMessage("Please Wait ...");
                    progressDialog.show();
                    initUpload(image_url , image_Second_url , image_first_url , title , width  , height , floor);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.order:
                Intent viewOrder = new Intent(getApplicationContext() , ViewOrder.class);
                viewOrder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(viewOrder);
                break;
            case R.id.logout:
                FirebaseAuth firebaseAuth;
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent main = new Intent(getApplicationContext() , MainActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(main);
                finish();
                break;
            case R.id.plan :
                Intent viewPlan = new Intent(getApplicationContext() , ListPlan.class);
               viewPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(viewPlan);
                break;

        }
return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri uri = data.getData();
        if (requestCode == GALLERY_REQ && resultCode ==RESULT_OK){

          startUploadImage(uri , GALLERY_REQ);
        }else if (requestCode == GALLERY_REQ_FIRST && resultCode == RESULT_OK){
            startUploadImage(uri , GALLERY_REQ_FIRST);
        }else if (requestCode == GALLERY_REQ_SEC && resultCode == RESULT_OK){
            startUploadImage(uri , GALLERY_REQ_SEC);
        }
    }

    private void startUploadImage(final Uri uri, final int galleryReq) {
        progressDialog.setMessage("Image is being uploading");
        progressDialog.show();
        if (uri!=null){
            StorageReference reference= storageReference.child("File/"+ UUID.randomUUID().toString());
            reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    progressDialog.dismiss();
                    int id= getResources().getIdentifier(String.valueOf(R.drawable.success),null , null);
                    if (galleryReq ==GALLERY_REQ ){
                        image.setImageURI(uri);
                        image_url = task.getResult().getDownloadUrl().toString();


                      //  Toast.makeText(getApplicationContext() , ""+image_url , Toast.LENGTH_SHORT).show();
                    }else if (galleryReq == GALLERY_REQ_FIRST){
                    image_first_url = task.getResult().getDownloadUrl().toString();
                        image_first.setImageResource(id);
                        image_first.setEnabled(false);
                       // Toast.makeText(getApplicationContext() , ""+image_first_url , Toast.LENGTH_SHORT).show();

                    }else if (galleryReq == GALLERY_REQ_SEC){
                        image_Second_url  =task.getResult().getDownloadUrl().toString();
                        image_second.setImageResource(id);
                        image_second.setEnabled(false);
                        //Toast.makeText(getApplicationContext() , ""+image_Second_url , Toast.LENGTH_SHORT).show();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext() , ""+e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initUpload(String image_url, String image_second_url, String image_first_url, String title, String width, String height, String floor) {
        HashMap <String , String >map= new HashMap<String , String>();
            int w = Integer.valueOf(width);
            int minW = w-5;
            int maxw = w+5;

            int h = Integer.valueOf(height);
        int minH =h-5;
        int maxH=h+5;
        int val = w*h;
        map.put("PlanDimen" , ""+val) ;
        map.put("PlanFeatured" , "false");
        map.put("PlanFloor" , floor);
        map.put("PlanImage" , image_url);
        map.put("PlanImageSec" , image_first_url);
        map.put("PlanImageThird" , image_second_url);
        map.put("PlanPlotSize" , width+ " x "+height);
        map.put("PlanTitle" , title);
        map.put("minWidth" , ""+minW);
        map.put("maxWidth" , ""+maxw);
        map.put("maxHeight" , ""+minH);
        map.put("minHeight" , ""+maxH);

        databaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext() , "Added" , Toast.LENGTH_SHORT).show();
                    Intent reload =  new Intent(getApplicationContext() , UploadPlan.class);
                    startActivity(reload);
                    finish();
                }
            }
        });

    }
}
