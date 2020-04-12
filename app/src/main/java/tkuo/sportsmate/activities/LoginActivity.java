package tkuo.sportsmate.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

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
                //allowUserToLogin();   [old one]
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

        // ******** set mAuth here **********
        /*
        Object currentUser = null; // testing
        if(currentUser != null) {
            sendUserToMainActivity();
        } */
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        // Check if username is empty
        if (!inputValidation.isEditTextFilled(userName)) {
            Toast.makeText(this, "Username Cannot Be Empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password is empty
        if (!inputValidation.isEditTextFilled(userPassword)) {
            Toast.makeText(this, "Password Cannot Be Empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the user
        if (databaseHelper.checkUser(userName.getText().toString().trim(),
                userPassword.getText().toString().trim())) {
            sendUserToMainActivity();
            emptyInputEditText();
        } else {
            Toast.makeText(this, "Wrong Username or Password...", Toast.LENGTH_SHORT).show();
        }
    }
    /*   **************   OLD CODE   *******************************
    private void allowUserToLogin() {
        String username = userName.getText().toString();
        String password = userPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter your username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while we are allowing you to login into your Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendUserToMainActivity();

                                Toast.makeText(LoginActivity.this, "Logged In Successfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error occurred:" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }

    } **********************************************************************  */

    /**
     * This method is to switch the user to main activity
     */
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to login activity unless clicking logout

        String username = userName.getText().toString().trim();
        User user = databaseHelper.getSingleUser(username).get(0);
        mainIntent.putExtra("current_user_obj", user);  // Pass user object to main activity

        startActivity(mainIntent);
        Toast.makeText(this, "Welcome back ~  " + user.getFirstName(), Toast.LENGTH_SHORT).show();
        finish();
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
