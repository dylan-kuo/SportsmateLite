package tkuo.sportsmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import tkuo.sportsmate.R;

public class ChooseMatchActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ChooseMatchActivity.this;
    private Button personalMatchButton, teamMatchButton;
    private String currentUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_match);

        initViews();
        initObjects();
        initListeners();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        personalMatchButton = (Button)findViewById(R.id.choose_personal_match);
        teamMatchButton =(Button)findViewById(R.id.choose_team_match);
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        // Receive username of current user (logged user) from MainActivity
        Intent i = getIntent();
        currentUsername = i.getStringExtra("current_username");
    }


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {

        // Redirect to CreatePersonalMatchActivity
        personalMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToCreatePersonalMatchActivity();
            }
        });


        /*
        // Redirect to CreateTeamMatchActivity (** Not Done Yet)
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToCreateTeamMatchActivity();
            }
        });  */
    }


    /*
    // This method is to switch to CreateTeamMatchActivity (** Not Done Yet)
    private void sendUserToCreateTeamMatchActivity() {
        Intent setupIntent = new Intent(ChooseMatchActivity.this, CreateTeamMatchActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    } */

    /**
     * This method is to switch to CreatePersonalMatchActivity
     *
     */
    private void sendUserToCreatePersonalMatchActivity() {

        Intent personalMatchIntent = new Intent(ChooseMatchActivity.this, CreatePersonalMatchActivity.class);
        //personalMatchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        personalMatchIntent.putExtra("current_username", currentUsername);  // Pass username to CreatePersonalMatchActivity
        startActivity(personalMatchIntent);
        //finish();
    }
}
