package tkuo.sportsmate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import tkuo.sportsmate.R;
import tkuo.sportsmate.model.User;
import tkuo.sportsmate.sql.DatabaseHelper;


public class MainActivity extends AppCompatActivity  {

    private final AppCompatActivity activity = MainActivity.this;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private CircleImageView headerProfileImage;
    private TextView name;
    private Toolbar mToolbar;
    private User currentUser;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        // Set up main page toolbar
        mToolbar = (Toolbar) findViewById((R.id.main_page_toolbar));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        // Set up the toggle navigation
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(
                        MainActivity.this, drawerLayout,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set up the navigation header
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        // Set up image on the navigation header
        headerProfileImage = navView.findViewById(R.id.img_logo);

        // Set up user's name on the navigation header
        name = navView.findViewById(R.id.nav_user_first_name);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);

        // Receive user object from either login activity or setup activity
        Intent i = getIntent();
        currentUser = i.getParcelableExtra("current_user_obj");

        // Below is the old method using Serializable
        //Intent i = getIntent();
        //currentUser = (User) i.getSerializableExtra("current_user_obj");

    }


    @Override
    protected void onStart() {
        super.onStart();


        // Check if user is authorized or not. If not, send it to login page
        if(currentUser == null) {
            sendUserToLoginActivity();
        }

        else {
            // Set up navigation header picture
            if (currentUser.getImageUri() != null) {
                Glide.with(this).load(currentUser.getImageUri()).into(headerProfileImage);
            }
            // set up navigation header user's name
            name.setText(currentUser.getFirstName()+ " " + currentUser.getLastName());
        }
    }

    /**
     * This method is to switch to Login Activity and clear any other activities on top of it
     *
     */
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


    /**
     * This method is used for selected options of drawer navigation
     *
     * @param item
     * @return true/false
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is to temporarily send toast message to test each menu item
     *
     * @param item
     */
    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_create_match:
                sendUserToCreateMatchActivity();
                break;

            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_personal_match:
                Toast.makeText(this, "Personal Match", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_team_match:
                Toast.makeText(this, "Team Match", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                sendUserToLoginActivity();
                break;
        }
    }



    /**
     * This method is to switch to CreateMatchActivity
     *
     */
    private void sendUserToCreateMatchActivity() {
        Intent createMatchIntent = new Intent(MainActivity.this, ChooseMatchActivity.class);
        //createMatchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(createMatchIntent);
        //finish();
    }
}
