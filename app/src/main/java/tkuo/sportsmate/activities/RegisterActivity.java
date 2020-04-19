package tkuo.sportsmate.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import tkuo.sportsmate.R;
import tkuo.sportsmate.utility.InputValidation;
import tkuo.sportsmate.sql.DatabaseHelper;
import tkuo.sportsmate.model.User;



public class RegisterActivity extends AppCompatActivity implements Serializable {

    private final AppCompatActivity activity = RegisterActivity.this;

    private EditText userName, userPassword, userConfirmPassword;
    private Button createAccountButton;
    private TextView loginLink;
    private ProgressDialog loadingBar;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        userName = (EditText) findViewById(R.id.register_username);
        userPassword = (EditText) findViewById(R.id.register_password);
        userConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        loginLink = (TextView) findViewById(R.id.register_login_link);
        createAccountButton = (Button) findViewById(R.id.register_create_account);
        loadingBar = new ProgressDialog(this);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRegisterInformation();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void saveRegisterInformation() {

        // Strings of input
        String str_userName = userName.getText().toString().trim();
        String str_userPassword = userPassword.getText().toString().trim();
        String str_userConfirmPassword = userConfirmPassword.getText().toString().trim();

        // Check if username is empty
        if (inputValidation.isValueNotFilled(str_userName)) {
            Toast.makeText(this, "Username cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if username format is valid (No special characters)
        if (inputValidation.isSpecialChar(str_userName)) {
            Toast.makeText(this, "Username cannot contain special characters...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if password is empty
        if (inputValidation.isValueNotFilled(str_userPassword)) {
            Toast.makeText(this, "Password cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if password format is valid
        if (!inputValidation.isPasswordValid(str_userPassword)) {
            Toast.makeText(this, "Password must be at least 6 characters (at least 1 digit, 1 uppercase & 1 lowercase character)", Toast.LENGTH_LONG).show();
            return;
        }
        // Check if confirm password is empty
        if (inputValidation.isValueNotFilled(str_userConfirmPassword)) {
            Toast.makeText(this, "Confirm password cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if confirm password matches password
        if (!inputValidation.isPasswordMatched(str_userPassword, str_userConfirmPassword)) {
            Toast.makeText(this, "Password does not match...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!databaseHelper.checkUser(str_userName)) {

            user.setUsername(str_userName);
            user.setPassword(str_userPassword);

            //emptyInputEditText();
            sendUserToSetupActivity(user);  // This will pass User object to setup activity to finish filling in all data for the user instance in SQLite db

        }
        else {
            Toast.makeText(this, "Username already exists...", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * NOTE: I use this method because it passes the User object to setup intent which is more convenient to fill in data in db
     * This method is to switch and pass the current User class object (user) to setup activity
     * @param currentUserObj
     */
    private void sendUserToSetupActivity(User currentUserObj) {

        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.putExtra("current_user_obj", currentUserObj);
        startActivity(setupIntent);
        finish();
    }

    /**
     * This method is to switch the user to login activity
     */
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        userName.setText(null);
        userPassword.setText(null);
        userConfirmPassword.setText(null);
    }
}
