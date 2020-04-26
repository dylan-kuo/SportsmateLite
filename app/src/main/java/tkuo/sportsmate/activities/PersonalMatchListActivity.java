package tkuo.sportsmate.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

import tkuo.sportsmate.R;
import tkuo.sportsmate.adapters.PersonalMatchAdapter;
import tkuo.sportsmate.model.PersonalMatch;
import tkuo.sportsmate.sql.DatabaseHelper;


public class PersonalMatchListActivity extends AppCompatActivity {

    private final AppCompatActivity activity = PersonalMatchListActivity.this;
    private DatabaseHelper databaseHelper;
    private String currentUsername;
    private ListView listView;
    private List<PersonalMatch> personalMatchList;
    private PersonalMatchAdapter personalMatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_match_list);

        initViews();
        initListeners();
        initObjects();
        loadMatchInListView();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        listView = findViewById(R.id.personalMatchListView);
    }


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PersonalMatch selectedMatch = (PersonalMatch) personalMatchAdapter.getItem(position);  // get the selected match object

                Intent joinMatchIntent = new Intent(PersonalMatchListActivity.this, JoinPersonalMatchActivity.class);
                joinMatchIntent.putExtra("selected_personal_match_obj", selectedMatch);  // Pass personal_match object
                joinMatchIntent.putExtra("current_username", currentUsername);
                startActivity(joinMatchIntent);
            }
        });
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        // Receive username of current user (logged user) from MainActivity
        Intent i = getIntent();
        currentUsername = i.getStringExtra("current_username");

        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This method is to load the personal matches in the list view
     */
    public void loadMatchInListView() {
        personalMatchList = databaseHelper.getAllPersonalMatch(); // get all the personal matches created from the database
        personalMatchAdapter = new PersonalMatchAdapter(this, personalMatchList);
        listView.setAdapter(personalMatchAdapter);
        personalMatchAdapter.notifyDataSetChanged();

    }


}
