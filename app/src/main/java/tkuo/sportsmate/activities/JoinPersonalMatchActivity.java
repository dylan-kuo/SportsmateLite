package tkuo.sportsmate.activities;
import tkuo.sportsmate.R;
import tkuo.sportsmate.model.PersonalMatch;
import tkuo.sportsmate.sql.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinPersonalMatchActivity extends AppCompatActivity {
    private final AppCompatActivity activity = JoinPersonalMatchActivity.this;
    private DatabaseHelper databaseHelper;
    private PersonalMatch selected_personal_match;
    private String currentUsername;
    private Button joinButton, cancelButton;
    private TextView pop_location, pop_address, pop_date, pop_time, pop_game_type, pop_num_people_going;
    private ImageView gameTypeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_match_popup);

        initViews();
        initObjects();
        initListeners();
        displayInfo();
        popWindows();


    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        gameTypeImage = (ImageView)findViewById(R.id.personal_pop_image);
        pop_location = (TextView) findViewById(R.id.personal_pop_location);
        pop_address = (TextView) findViewById(R.id.personal_pop_address);
        pop_date = (TextView) findViewById(R.id.personal_pop_game_date);
        pop_time = (TextView) findViewById(R.id.personal_pop_game_time);
        pop_game_type = (TextView) findViewById(R.id.personal_pop_game_type);
        pop_num_people_going = (TextView) findViewById(R.id.personal_pop_people_going);

        joinButton = (Button) findViewById(R.id.personal_pop_join);
        cancelButton = (Button) findViewById(R.id.personal_pop_cancel);
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);

        // Receive personal_match object and logged user's username from PersonalMatchList activity
        Intent i = getIntent();
        selected_personal_match = i.getParcelableExtra("selected_personal_match_obj"); // selected personal match
        currentUsername = i.getStringExtra("current_username"); // logged user's username

    }


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        // click join to join the match
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinPersonalMatch();
            }
        });
        // click cancel to finish this activity.
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * This method is to display the info of the match
     */
    private void displayInfo() {

        // Show Game Type in the TextView
        pop_game_type.setText(selected_personal_match.getGameType());

        // Show location in the TextView
        pop_location.setText(selected_personal_match.getLocation());

        // Show date in the TextView
        pop_date.setText(selected_personal_match.getGameDate());

        // Show time in the TextView
        pop_time.setText(selected_personal_match.getStartAt() + " - " + selected_personal_match.getEndAt());

        // Show address of the gum in the TextView
        String address = "";
        switch (selected_personal_match.getLocation()) {
            case "BU FitRec":
                address = "915 Commonwealth Avenue, Boston, MA 02215";
                break;

            case "MIT Recreation":
                address = "120 Vassar St, Cambridge, MA 02139";
                break;

            case "Cambridge Athletic Club":
                address = "215 First St, Cambridge, MA 02142";
                break;

            case "Huntington Avenue YMCA":
                address = "316 Huntington Ave, Boston, MA 02115";
                break;

            case "Boston Ski and Sports Club":
                address = "51 Water St, Watertown, MA 02472";
                break;

            case "BSC Allston":
                address = "15 Gorham St, Allston, MA 02134";
                break;
        }
        pop_address.setText(address);


        // Show the people joined in the TextView
        int numPeopleGoing = selected_personal_match.getNumInitialPlayers() +
                selected_personal_match.getNumPlayersJoined();
        String strNumPeopleGoing;
        if(numPeopleGoing == 1) {
            strNumPeopleGoing = numPeopleGoing + " person is going";
        } else {
            strNumPeopleGoing = numPeopleGoing + " people are going";
        }
        pop_num_people_going.setText(strNumPeopleGoing);


        // Show image
        int image = R.drawable.pmatch_1on1; // default

        switch (selected_personal_match.getGameType()) {
            case "1 on 1":
                image = R.drawable.pmatch_1on1;
                break;

            case "2 on 2":
                image = R.drawable.pmatch_2on2;
                break;

            case "3 on 3":
                image = R.drawable.pmatch_3on3;
                break;

            case "4 on 4":
                image = R.drawable.pmatch_4on4;
                break;

            case "5 on 5":
                image = R.drawable.pmatch_5on5;
                break;
        }
        gameTypeImage.setImageResource(image);
    }



    /**
     * This method is to initialize the pop-up window
     */
    public void popWindows() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }


    public void joinPersonalMatch() {

    }

    public void updatePersonalMatchTable() {

    }

    public void insertToPersonalMatchPlayersTable() {

    }



}
