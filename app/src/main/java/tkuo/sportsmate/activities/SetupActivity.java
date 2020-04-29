package tkuo.sportsmate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import de.hdodenhof.circleimageview.CircleImageView;
import tkuo.sportsmate.R;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.sql.DatabaseHelper;
import tkuo.sportsmate.utility.InputValidation;


public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SetupActivity.this;
    private EditText firstName, lastName;
    private RadioGroup radioGroup;
    private Button saveInformationButton;
    private CircleImageView profileImage;
    private RadioButton selectedGender;


    final static int IMAGE_PICKER = 1;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initViews();
        initObjects();
        initListeners();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        firstName = (EditText) findViewById(R.id.setup_first_name);
        lastName = (EditText) findViewById(R.id.setup_last_name);
        radioGroup = (RadioGroup) findViewById(R.id.setup_gender);
        saveInformationButton = (Button) findViewById(R.id.setup_information_button);
        profileImage = (CircleImageView) findViewById(R.id.setup_profile_image);
    }


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        saveInformationButton.setOnClickListener(SetupActivity.this);
        profileImage.setOnClickListener(this);

    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

        // Receive user object from setup activity
        Intent i = getIntent();
        currentUser = i.getParcelableExtra("current_user_obj");

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.setup_information_button:
                saveAccountSetupInformation();
                break;

            case R.id.setup_profile_image:

                Intent getImageIntent = new Intent(Intent.ACTION_PICK);
                getImageIntent.setType("image/*");
                startActivityForResult(getImageIntent, IMAGE_PICKER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK && null != data) {

            Uri fullPhotoUri = data.getData();
            profileImage.setImageURI(fullPhotoUri);

            currentUser.setImageUri(fullPhotoUri.toString());
        }
    }


    /**
     * This method check if the input is valid and create a new instance to user table, then switch to main activity
     *
     */
    private void saveAccountSetupInformation() {

        // Get the selected radio button (male/female)
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        selectedGender = (RadioButton) radioGroup.findViewById(radioButtonID);

        // Strings of input
        String str_firstName = firstName.getText().toString().trim();
        String str_lastName = lastName.getText().toString().trim();
        String str_selected_gender = selectedGender.getText().toString().trim();


        // Check if first name is empty
        if(inputValidation.isValueNotFilled(str_firstName)) {
            Toast.makeText(this, "First name cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if first name format is valid (No digit and special characters allowed)
        if ((inputValidation.isNumberChar(str_firstName)) || (inputValidation.isSpecialChar(str_firstName))) {
            Toast.makeText(this, "Please enter a valid first name...", Toast.LENGTH_SHORT).show();
            return;
        }

        // CHeck if last name is empty
        if(inputValidation.isValueNotFilled(str_lastName)) {
            Toast.makeText(this, "Please enter your last name...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if last name format is valid (No digit and special characters allowed)
        if (inputValidation.isNumberChar(str_lastName) || inputValidation.isSpecialChar(str_lastName)) {
            Toast.makeText(this, "Please enter a valid last name...", Toast.LENGTH_SHORT).show();
            return;
        }

        // If every thing is fine, add new user to SQLite db
        else {
            currentUser.setFirstName(str_firstName);
            currentUser.setLastName(str_lastName);
            currentUser.setGender(str_selected_gender);
            databaseHelper.addUser(currentUser);
            Toast.makeText(this, "Welcome " + currentUser.getFirstName(), Toast.LENGTH_SHORT).show();

            storeToSharedPreference();
            sendUserToMainActivity();
        }
    }


    /**
     * Pass the user object to main activity
     */
    // Switch to Main Activity and clear any other activities on top of it
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to Register activity unless clicking logout

        //mainIntent.putExtra("current_user_obj", currentUser);  // Pass user object to main activity
        startActivity(mainIntent);
        finish();
    }


    /**
     * This method is to store username to shared preference object
     */
    private void storeToSharedPreference() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Logged", true);
        editor.putString("Username", currentUser.getUsername());
        editor.apply();
    }
}
