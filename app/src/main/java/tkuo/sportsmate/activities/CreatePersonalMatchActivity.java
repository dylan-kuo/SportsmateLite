package tkuo.sportsmate.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.app.DatePickerDialog;

import tkuo.sportsmate.R;
import tkuo.sportsmate.model.PersonalMatch;
import tkuo.sportsmate.model.Player;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.sql.DatabaseHelper;
import tkuo.sportsmate.utility.InputValidation;

public class CreatePersonalMatchActivity extends AppCompatActivity {

    private final AppCompatActivity activity = CreatePersonalMatchActivity.this;
    private Button createButton;
    private TextView title;
    private TextView date, startTime, endTime;
    private EditText numberOfPlayer;
    private ArrayAdapter<CharSequence> typeAdapter;
    private ArrayAdapter<CharSequence> locationAdapter;
    private Spinner matchType;
    private Spinner location;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String dateSelected;
    private String timeSelected;
    private Calendar calendar;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private PersonalMatch newPersonalMatch;
    private String currentUsername;
    private User currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_personal_match);//create the view of create personal match

        initViews();
        initListeners();
        initObjects();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.create_personal_match_date);
        startTime = (TextView) findViewById(R.id.create_personal_match_start_time);
        endTime = (TextView) findViewById(R.id.create_personal_match_end_time);
        numberOfPlayer = (EditText) findViewById(R.id.create_personal_match_init_player_number);
        createButton = (Button) findViewById(R.id.create_personal_match_create);


        // Spinner for location
        location = (Spinner)findViewById(R.id.create_personal_match_location);
        locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.basketball_court_array, R.layout.support_simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        location.setAdapter(locationAdapter);

        // Spinner for game type
        matchType = (Spinner)findViewById(R.id.create_personal_match_type);
        typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.personal_match_type_array, R.layout.support_simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        matchType.setAdapter(typeAdapter);

        // Date
        mYear= Calendar.getInstance().get(Calendar.YEAR);
        mMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        mDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ;

        // Time
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ;
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {

        // Create Button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonalMatchInformation();
            }
        });

        // Date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Start time
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(startTime);
            }
        });

        // End time
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(endTime);
            }
        });

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        newPersonalMatch = new PersonalMatch();

        // Receive username of current user (logged user) from ChooseMatchActivity
        Intent i = getIntent();
        currentUsername = i.getStringExtra("current_username");

    }


    private void showDatePicker() {
        calendar = Calendar.getInstance();
        int mYearParam = mYear;
        int mMonthParam = mMonth-1;
        int mDayParam = mDay;

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreatePersonalMatchActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mMonth = monthOfYear + 1;
                        mYear=year;
                        mDay=dayOfMonth;

                        dateSelected = String.format("%02d/%02d/%04d", mMonth, mDay, mYear);
                        date.setText(dateSelected);
                    }
                }, mYearParam, mMonthParam, mDayParam);

        datePickerDialog.show();

    }


    private void showTimePicker(final TextView textView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreatePersonalMatchActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int pHour,
                                          int pMinute) {
                        mHour = pHour;
                        mMinute = pMinute;

                        timeSelected = String.format("%02d:%02d", mHour, mMinute);
                        textView.setText(timeSelected);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.show();
    }

    private void savePersonalMatchInformation() {

        // Strings of input
        String str_location = location.getSelectedItem().toString();
        String str_date = date.getText().toString();
        String str_startTime = startTime.getText().toString();
        String str_endTime = endTime.getText().toString();
        String str_gameType = matchType.getSelectedItem().toString();
        String numOfInitPlayer = numberOfPlayer.getText().toString().trim();



        // Check if location is empty
        if(inputValidation.isValueNotFilled(str_location)) {
            Toast.makeText(this, "Please select a location...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if Date is empty
        if(inputValidation.isValueNotFilled(str_date)) {
            Toast.makeText(this, "Please select a date...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if start time is empty
        if(inputValidation.isValueNotFilled(str_startTime)) {
            Toast.makeText(this, "Please select a start time...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if end time is empty
        if(inputValidation.isValueNotFilled(str_endTime)) {
            Toast.makeText(this, "Please select a end time...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if start & end time is legal
        if(!inputValidation.isTimeValid(str_startTime, str_endTime)) {
            Toast.makeText(this, "End time cannot be earlier than start time", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if Date is legal (Date prior to today is not allowed)
        if(!inputValidation.isDateValid(str_date, str_startTime)) {
            Toast.makeText(this, "Date & Time was already expired ...", Toast.LENGTH_SHORT).show();
            return;
        }

        // **** Note: We don't need to check game type since the default value is set.  ****

        // Check if location is empty
        if(inputValidation.isValueNotFilled(numOfInitPlayer)) {
            Toast.makeText(this, "Number of initial player cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // The number of initial players must be at least one
        if(!inputValidation.isNumberChar(numOfInitPlayer)) {
            Toast.makeText(this, "Number of initial player must be a digit (for example: 3)...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the initial number of player is at least one
        if (Integer.parseInt(numOfInitPlayer) == 0) {
            Toast.makeText(this, "Initial number of player must be at least one player...", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            // Get the User object from SQLite db based on the given username
            currentUser = databaseHelper.getSingleUser(currentUsername).get(0);
            long userId = currentUser.getId(); // Get the user id in the user table

            // Get player object from SQLite db based on the user id in User Table
            // Relationship:  user.id (User Table) = player.user_id (Player Table)

            Player player = databaseHelper.getSinglePlayer(userId).get(0);
            Long playerId = player.getPlayerId();

            newPersonalMatch.setHostPlayerId(playerId);
            newPersonalMatch.setLocation(str_location);
            newPersonalMatch.setGameDate(str_date);
            newPersonalMatch.setStartAt(str_startTime);
            newPersonalMatch.setEndAt(str_endTime);
            newPersonalMatch.setGameType(str_gameType);
            newPersonalMatch.setNumInitialPlayers(Integer.parseInt(numOfInitPlayer));

            // Post data to personal_match table in the SQLite db
            databaseHelper.addPersonalMatch(newPersonalMatch);

            // Go back to main activity
            sendUserToMainActivity();

        }
    }



    // Switch to Main Activity and clear any other activities on top of it
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(CreatePersonalMatchActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to Register activity unless clicking logout
        mainIntent.putExtra("current_user_obj", currentUser);  // Pass user object to main activity
        startActivity(mainIntent);
        finish();
    }

}
