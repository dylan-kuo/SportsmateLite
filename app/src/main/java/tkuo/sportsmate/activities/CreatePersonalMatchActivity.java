package tkuo.sportsmate.activities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.Toast;


import java.util.HashMap;
import tkuo.sportsmate.R;

public class CreatePersonalMatchActivity extends AppCompatActivity {
    private Button createButton;
    private TextView textView;
    private TextView date, startTime, endTime;
    private EditText numberOfPlayer;
    private ArrayAdapter<CharSequence> typeAdapter;
    private ArrayAdapter<CharSequence> locationAdapter;
    private Spinner matchType;
    private Spinner location;
    private ProgressDialog loadingBar;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String dateSelected;
    private String timeSelected;
    private Calendar calendar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_personal_match);//create the view of create personal match

        initViews();
        initListeners();


    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textView = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.create_personal_match_date);
        startTime = (TextView) findViewById(R.id.create_personal_match_start_time);
        endTime = (TextView) findViewById(R.id.create_personal_match_end_time);
        numberOfPlayer = (EditText) findViewById(R.id.create_personal_match_init_player_number);
        createButton = (Button) findViewById(R.id.create_personal_match_create);
        loadingBar = new ProgressDialog(this);

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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savePersonalMatchInformation();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(endTime);
            }
        });

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


    /*
    private void savePersonalMatchInformation() {
        String location_s = location.getText().toString();
        String date_s = date.getText().toString();
        String start_from = start.getText().toString();
        String end_to = end.getText().toString();
        String number = numberOfPlayer.getText().toString();

        if(TextUtils.isEmpty(location_s)){
            Toast.makeText(this, "Please write the location of this game...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(date_s)){
            Toast.makeText(this, "Please write the date of this game...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(start_from)){
            Toast.makeText(this, "Please write the start time...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(end_to)){
            Toast.makeText(this, "Please write the end time...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please write the number of players...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating New Personal Match");
            loadingBar.setMessage("Please wait, while we are creating new personal match...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap matchInfo = new HashMap();
            matchInfo.put("host_id", currentUserID);
            matchInfo.put("location", location_s);
            matchInfo.put("date", date_s);
            matchInfo.put("start_time", start_from);
            matchInfo.put("end_time", end_to);
            matchInfo.put("num_of_players", number);

            PersonalMatchRef.child(String.valueOf(personal_id)).updateChildren(matchInfo).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) {
                        // startActivity(new Intent(getApplicationContext(),MainActivity.class));    // This works the same as senUserToMainActivity()
                        sendUserToMainActivity();
                        Toast.makeText(CreatePersonalMatchActivity.this, "Your personal match is created successfully.", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                        personal_id++;
                    }
                    else {
                        String message = task.getException().getMessage();
                        Toast.makeText(CreatePersonalMatchActivity.this, "Error occurred:" + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }




     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    // Switch to Main Activity and clear any other activities on top of it
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(CreatePersonalMatchActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to Register activity unless clicking logout
        startActivity(mainIntent);
        finish();
    }

}
