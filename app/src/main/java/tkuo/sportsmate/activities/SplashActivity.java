package tkuo.sportsmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import tkuo.sportsmate.R;


public class SplashActivity extends AppCompatActivity  {

    private final AppCompatActivity activity = SplashActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);


    }

}
