package tkuo.sportsmate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import tkuo.sportsmate.R;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.sql.DatabaseHelper;


public class SplashActivity extends AppCompatActivity  {

    private final AppCompatActivity activity = SplashActivity.this;
    Boolean logged;
    String username;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseHelper = new DatabaseHelper(SplashActivity.this);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        logged = sharedPref.getBoolean("Logged", false);
        username = sharedPref.getString("Username", "");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logged) // if user is already registered, direct straight to main activity
                {
                    //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    User user = databaseHelper.getSingleUser(username).get(0);
                    mainIntent.putExtra("current_user_obj", user);
                    startActivity(mainIntent);

                }else { // if user is not logged, direct him to login activity
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }

                finish();
            }
        }, 2000);


    }

}
