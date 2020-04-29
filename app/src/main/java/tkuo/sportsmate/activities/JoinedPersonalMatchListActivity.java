package tkuo.sportsmate.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tkuo.sportsmate.R;
import tkuo.sportsmate.adapters.PersonalMatchAdapter;
import tkuo.sportsmate.model.PersonalMatch;
import tkuo.sportsmate.model.Player;
import tkuo.sportsmate.sql.DatabaseHelper;

public class JoinedPersonalMatchListActivity extends AppCompatActivity {
    private final AppCompatActivity activity = JoinedPersonalMatchListActivity.this;
    private DatabaseHelper databaseHelper;
    private String currentUsername;
    private List<PersonalMatch> personalMatchList;
    private ListView listView;
    private PersonalMatchAdapter personalMatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_personal_match_list);

        initViews();
        initObjects();
        loadMatchInListView();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {

        // set up listview for personal matches joined
        listView = (ListView) findViewById(R.id.joinedPersonalMatchListView);
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

        //step #1 - get the corresponding player object
        Player player = databaseHelper.getSinglePlayer(currentUsername).get(0);

        //step #2 - get joined match_id
        List<Long> match_id_list = databaseHelper.getJoinedPersonalMatchId(player.getPlayerId());

        //step #3 - get all personal matches joined or created by the player
        List<PersonalMatch> pmatchList = new ArrayList<>(); // create a list for storing joined personal matches

        for (int i = 0; i < match_id_list.size(); i++) {
            long matchId = match_id_list.get(i); // match_id
            PersonalMatch personalMatch = databaseHelper.getPersonalMatchesByMatchId(matchId).get(0);
            pmatchList.add(personalMatch);
        }

        personalMatchAdapter = new PersonalMatchAdapter(this, pmatchList);
        listView.setAdapter(personalMatchAdapter);
        personalMatchAdapter.notifyDataSetChanged();
    }

}
