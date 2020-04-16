package tkuo.sportsmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import tkuo.sportsmate.R;

public class ChooseMatchActivity extends AppCompatActivity {
    private Button personalButton, teamButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_match);

    personalButton = (Button)findViewById(R.id.choose_personal_match);
    teamButton =(Button)findViewById(R.id.choose_team_match);

    personalButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendUserToCreatePersonalMatchActivity();
        }
    });
//    teamButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            sendUserToCreateTeamMatchActivity();
//        }
//    });
    }

//    private void sendUserToCreateTeamMatchActivity() {
//        Intent setupIntent = new Intent(ChooseMatchActivity.this, CreateTeamMatchActivity.class);
//        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(setupIntent);
//        finish();
//    }

    private void sendUserToCreatePersonalMatchActivity() {
        Intent personalMatchIntent = new Intent(ChooseMatchActivity.this, CreatePersonalMatchActivity.class);
        personalMatchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(personalMatchIntent);
        //finish();
    }
}
