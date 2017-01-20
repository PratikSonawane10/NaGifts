package com.nagifts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nagifts.SessionManager.SessionManager;

public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;
    ImageView image1;
    ImageView image2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        image1 = (ImageView)findViewById(R.id.logoHeader);
        image2 = (ImageView) findViewById(R.id.logoFooter);
        image2.setVisibility(View.INVISIBLE);
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Animation animation2=AnimationUtils.loadAnimation(this, R.anim.fadein);
        image1.startAnimation(animationFadeIn);

        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Animation animationFadeInAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fadein);
                image2.setVisibility(View.VISIBLE);
                image2.startAnimation(animation2);

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SessionManager sessionManager = new SessionManager(SplashScreen.this);
                sessionManager.checkLogin();
//                Intent indexIntent = new Intent(SplashScreen.this, Login.class);
//                startActivity(indexIntent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}