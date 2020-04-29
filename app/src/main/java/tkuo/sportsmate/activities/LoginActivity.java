package tkuo.sportsmate.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import tkuo.sportsmate.R;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.utility.InputValidation;
import tkuo.sportsmate.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText userName, userPassword;
    private TextView needNewAccountLink;
    private ProgressDialog loadingBar;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        needNewAccountLink = (TextView) findViewById(R.id.register_account_link);
        userName = (EditText) findViewById(R.id.login_username);
        userPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        loadingBar = new ProgressDialog(this);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();
            }
        });

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegisterActivity();
            }
        });
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(LoginActivity.this);
        inputValidation = new InputValidation(LoginActivity.this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {

        // Strings of input
        String str_userName = userName.getText().toString().trim();
        String str_userPassword = userPassword.getText().toString().trim();

        // Check if username is empty
        if (inputValidation.isValueNotFilled(str_userName)) {
            Toast.makeText(this, "Username cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password is empty
        if (inputValidation.isValueNotFilled(str_userPassword)) {
            Toast.makeText(this, "Password cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the user
        if (databaseHelper.checkUser(str_userName, str_userPassword)) {

            user = databaseHelper.getSingleUser(str_userName).get(0);

            // store username to shared preference
            storeToSharedPreference();

            sendUserToMainActivity();
            emptyInputEditText();
        } else {
            Toast.makeText(this, "Wrong username or password...", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This method is to switch the user to main activity
     */
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to login activity unless clicking logout

        // This 3 line code below is for sending current logged user object to main activity
        //String username = userName.getText().toString().trim();
        //user = databaseHelper.getSingleUser(username).get(0);
        //mainIntent.putExtra("current_user_obj", user);  // Pass user object to main activity

        startActivity(mainIntent);
        Toast.makeText(this, "Welcome back " + user.getFirstName(), Toast.LENGTH_SHORT).show();
        finish();
    }


    /**
     * This method is to store username to shared preference object
     */
    private void storeToSharedPreference() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Logged", true);
        editor.putString("Username", user.getUsername());
        editor.apply();
    }


    /**
     * This method is to switch the user to register activity
     */
    private void sendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        userName.setText(null);
        userPassword.setText(null);
    }

}
