package tkuo.sportsmate.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;



import java.io.Serializable;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import tkuo.sportsmate.R;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.sql.DatabaseHelper;
import tkuo.sportsmate.utility.InputValidation;


public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SetupActivity.this;
    private EditText firstName, lastName;
    private RadioGroup gender;
    private Button saveInformationButton;
    private CircleImageView profileImage;
    private ProgressDialog loadingBar;
    private RadioButton radioButton;

    final static int IMAGE_PICKER = 1;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User currentUser;


    private String UsersRef;


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
        gender = (RadioGroup) findViewById(R.id.setup_gender);
        saveInformationButton = (Button) findViewById(R.id.setup_information_button);
        profileImage = (CircleImageView) findViewById(R.id.setup_profile_image);
        loadingBar = new ProgressDialog(this);

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

        // Below is the old method using Serializable
        //Intent i = getIntent();
        //currentUser = (User) i.getSerializableExtra("current_user_obj"); // Get the user object passed from register activity

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
            //String realUri = getRealPathFromURI(fullPhotoUri);
        }
    }


    /**
     * This method check if the input is valid and create a new instance to user table, then switch to main activity
     *
     */
    private void saveAccountSetupInformation() {

        // Get the selected radio button (male/female)
        int radioButtonID = gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) gender.findViewById(radioButtonID);

        // Check if first name is empty
        if(!inputValidation.isEditTextFilled(firstName)) {
            Toast.makeText(this, "First name cannot be empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if first name format is valid
        else if (!inputValidation.isEditTextValid(firstName)) {
            Toast.makeText(this, "Please enter valid first password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // CHeck if last name is empty
        else if(!inputValidation.isEditTextFilled(lastName)) {
            Toast.makeText(this, "Please enter your last name...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if last name format is valid
        else if (!inputValidation.isEditTextValid(lastName)) {
            Toast.makeText(this, "Please enter valid last password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if radio button is empty
        else if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select your gender...", Toast.LENGTH_SHORT).show();
        }

        else {  // If every thing is fine, add new user to SQLite db
            currentUser.setFirstName(firstName.getText().toString().trim());
            currentUser.setLastName(lastName.getText().toString().trim());
            currentUser.setGender(radioButton.getText().toString().trim());
            databaseHelper.addUser(currentUser);
            Toast.makeText(this, "Welcome " + currentUser.getFirstName(), Toast.LENGTH_SHORT).show();
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

        mainIntent.putExtra("current_user_obj", currentUser);  // Pass user object to main activity
        startActivity(mainIntent);
        finish();
    }


    /**
     * This method returns the real URI (path) in android (no need for use)
     */
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
