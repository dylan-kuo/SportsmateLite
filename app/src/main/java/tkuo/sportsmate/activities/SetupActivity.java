package tkuo.sportsmate.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class SetupActivity extends AppCompatActivity implements Serializable, View.OnClickListener {

    private final AppCompatActivity activity = SetupActivity.this;
    private EditText firstName, lastName;
    private RadioGroup gender;
    private Button saveInformationButton;
    private CircleImageView profileImage;
    private ProgressDialog loadingBar;
    private RadioButton radioButton;

    final static int GALLERY_PICK = 1;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    //private String currentUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        /*
        // Connects to Firebase
        mAuth = FirebaseAuth.getInstance();

        // Connects to current user
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        */

        initViews();
        initObjects();
        initListeners();


        /*
        // Place the image in the circleImageView
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("profileimage")) {
                    String image = dataSnapshot.child("profileimage").getValue().toString();
                    // Put the image to the profile picture in Setup Activity

                    Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.profile)
                            .into(ProfileImage);
                }
                else {
                    Toast.makeText(SetupActivity.this, "Please select profile image first.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  */
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
        //user = new User();
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("current_user_obj"); // Get the user object passed from register activity
        //currentUserName = getIntent().getStringExtra("current_userName"); // Get the current user's username from last activity (RegisterActivity)
        Toast.makeText(this, user.getUsername(), Toast.LENGTH_SHORT).show();
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
                sendUserToMainActivity();
                break;

            case R.id.setup_profile_image:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_PICK);
                break;
        }
    }

    /*
    // Let the user crop the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Some conditions for the image
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK && data!= null) {
            Uri ImageUri = data.getData();
            // Crop the image
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        // Get the cropped image
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) { // Store the image into result
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK) {
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait, while we are updating your profile image...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);


                final Uri resultUri = result.getUri();

                // Create file path to the Firebase storage
                final StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                // Put the file inside the profile image folder and store the image in it
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SetupActivity.this, "Profile Image stored successfully...", Toast.LENGTH_SHORT).show();

                            // Get the image uri from Firebase storage
                            final String downloadUri = task.getResult().getStorage().getDownloadUrl().toString();

                            filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final String downloadUri = uri.toString();
                                            UsersRef.child("profileimage").setValue(downloadUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SetupActivity.this, "Image Stored", Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }
                                                    else {
                                                        String message = task.getException().getMessage();
                                                        Toast.makeText(SetupActivity.this,"Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Error Occurred: Image can't be cropped. Try again.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }
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
        if (!inputValidation.isEditTextValid(firstName)) {
            Toast.makeText(this, "Please enter valid first password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // CHeck if last name is empty
        if(!inputValidation.isEditTextFilled(lastName)) {
            Toast.makeText(this, "Please enter your full name...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if last name format is valid
        if (!inputValidation.isEditTextValid(lastName)) {
            Toast.makeText(this, "Please enter valid last password...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if radio button is empty
        if (!inputValidation.isRadioButtonFilled(radioButton)) {
            Toast.makeText(this, "Please Select your gender...", Toast.LENGTH_SHORT).show();
            return;
        }
        else {  // If every thing is fine, add new user to SQLite db
            user.setFirstName(firstName.getText().toString().trim());
            user.setLastName(lastName.getText().toString().trim());
            user.setGender(radioButton.getText().toString().trim());
            databaseHelper.addUser(user);
            Toast.makeText(this, "Welcome to Sportsmate!  " + user.getFirstName(), Toast.LENGTH_SHORT).show();
        }
    }

    // Switch to Main Activity and clear any other activities on top of it
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Do this to prevent user from going back to Register activity unless clicking logout
        mainIntent.putExtra("current_user_obj", user);  // Pass user object to main activity
        startActivity(mainIntent);
        finish();
    }
}
