package tkuo.sportsmate.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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


    private String[] mTitle = {"Facebook", "Whatsapp", "Twitter", "Instagram", "Youtube"};
    private String[] mDescription = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};


    private int[] images = {R.drawable.pmatch_1on1, R.drawable.pmatch_2on2, R.drawable.pmatch_3on3, R.drawable.pmatch_4on4, R.drawable.pmatch_5on5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_match_list);

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
        //PersonalMatchAdapter adapter = new PersonalMatchAdapter(this, mTitle, mDescription, images);
        //listView.setAdapter(adapter);
    }


    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText( PersonalMatchListActivity.this, "Facebook Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText( PersonalMatchListActivity.this, "Whatsapp Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText( PersonalMatchListActivity.this, "Twitter Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText( PersonalMatchListActivity.this, "Instagram Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText( PersonalMatchListActivity.this, "Youtube Description", Toast.LENGTH_SHORT).show();
                }
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

        //PersonalMatch p1 = personalMatchList.get(0);
        //Toast.makeText(this, String.valueOf(p1.getPmatchId()), Toast.LENGTH_SHORT).show();
    }




    /*
    class PersonalMatchAdapter extends ArrayAdapter<String> {

        Context context;
        private String[] locations;
        private String[] dates;
        private String[] gameTypes;
        private String[] startTimes;
        private String[] endTimes;

        String rTitle[];
        String rDescription[];
        int rImgs[];

        PersonalMatchAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.pmatch_location, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.pmatch_image);
            TextView myTitle = row.findViewById(R.id.pmatch_location);
            TextView myDescription = row.findViewById(R.id.pmatch_address);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    } */
}
