package tkuo.sportsmate.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import tkuo.sportsmate.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userPassword, userConfirmPassword;
    private Button createAccountButton;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        userName = (EditText) findViewById(R.id.register_username);
        userPassword = (EditText) findViewById(R.id.register_password);
        userConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        createAccountButton = (Button) findViewById(R.id.register_create_account);
        loadingBar = new ProgressDialog(this);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        Object currentUser = null;

        // If user is already authenticated, switch straight to main activity
        if(currentUser != null) {
            sendUserToMainActivity();
        }
    }


    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to Register activity unless clicking logout
        startActivity(mainIntent);
        finish();
    }

    // Create new account
    private void createNewAccount() {
        String username = userName.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = userConfirmPassword.getText().toString();

        // If the username is empty, show the error message
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please write your username...", Toast.LENGTH_SHORT).show();
        }
        // If the password is empty, show the error message
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        // if the confirm password is empty, show the error message
        else if(TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        // If the password and confirm password are different, show the error message
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Your password do not match with your confirm password...", Toast.LENGTH_SHORT).show();
        }

        // If input data are all correctly typed in, send them to Firebase to validate the authentication
        else {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            /*
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If authenticated successfully, send the user to setup activity
                            if(task.isSuccessful()) {
                                sendUserToSetupActivity();

                                Toast.makeText(RegisterActivity.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    }); */
        }
    }
    // Switch to Setup Activity and clear any other activities on top of it
    private void sendUserToSetupActivity() {
        /*
        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish(); */
    }


}
