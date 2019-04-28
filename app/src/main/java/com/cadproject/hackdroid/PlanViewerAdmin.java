package com.cadproject.hackdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cadproject.hackdroid.app.Constant;

public class PlanViewerAdmin extends AppCompatActivity {
    String imge;
    ImageView imageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_viewer_admin);
            imge = Constant.image;
            imageView = (ImageView)findViewById(R.id.image);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
     //   Toast.makeText(getApplicationContext() , ""+imge , Toast.LENGTH_SHORT).show();
        Glide.with(getApplicationContext()).load(imge).into(imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
  public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}
