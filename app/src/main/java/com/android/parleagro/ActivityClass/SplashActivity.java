package com.android.parleagro.ActivityClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.parleagro.ActivityClass.MainActivity;
import com.android.parleagro.R;


public class SplashActivity extends Activity {

    TextView text1;
    ImageView image;
    private Animation slide_up;
    Runnable csRunnable1 = new Runnable() {
        @Override
        public void run() {
            image.startAnimation(slide_up);
            text1.startAnimation(slide_up);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        text1 = findViewById(R.id.text1);
        image = findViewById(R.id.image);
        Handler handler1 = new Handler();
        handler1.postDelayed(csRunnable1, 0);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                image.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(() -> {
                    image.setVisibility(View.GONE);
                    text1.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}