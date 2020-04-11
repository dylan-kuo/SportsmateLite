package tkuo.sportsmate.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                postDataToSQLite();
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
    private void postDataToSQLite() {

        // Check if username is empty
        if (!inputValidation.isEditTextFilled(userName)) {
            Toast.makeText(this, "Username cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if username format is valid
        if (!inputValidation.isEditTextValid(userName)) {
            Toast.makeText(this, "Please enter valid username...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if password is empty
        if (!inputValidation.isEditTextFilled(userPassword)) {
            Toast.makeText(this, "Password cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if password format is valid
        if (!inputValidation.isEditTextValid(userPassword)) {
            Toast.makeText(this, "Please enter valid password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if confirm password is empty
        if (!inputValidation.isEditTextFilled(userConfirmPassword)) {
            Toast.makeText(this, "Confirm password cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if confirm password format is valid
        if (!inputValidation.isEditTextValid(userConfirmPassword)) {
            Toast.makeText(this, "Please enter valid confirm password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if confirm password matches password
        if (!inputValidation.isPasswordMatched(userPassword, userConfirmPassword)) {
            Toast.makeText(this, "Password does not match...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!databaseHelper.checkUser(userName.getText().toString().trim())) {

            user.setUsername(userName.getText().toString().trim());
            user.setPassword(userPassword.getText().toString().trim());

            //databaseHelper.addUser(user);  // Create user instance to Sqlite based on only username and password

            emptyInputEditText();

            sendUserToSetupActivity(user);  // This will pass User object to setup activity to finish filling in all data for the user instance in SQLite db
            //String currentUserName = user.getUsername();
            //sendUserToSetupActivity(currentUserName);  // Pass the current username to next activity
        }
        else {
            Toast.makeText(this, "Username already exists...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * This method is to switch the user to setup activity
     */
    private void sendUserToSetupActivity() {

        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    /**
     * NOTE: If you are going to pass user to setup intent by username, use this method
     * This method is to switch and pass the current user's username to setup activity
     * @param currentUserName
     */
    private void sendUserToSetupActivity(String currentUserName) {

        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.putExtra("current_userName", currentUserName);
        startActivity(setupIntent);
        finish();
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
