package tkuo.sportsmate.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tkuo.sportsmate.R;
import tkuo.sportsmate.model.PersonalMatch;

public class PersonalMatchAdapter extends BaseAdapter {
    private Context context;
    private List<PersonalMatch> list;

    public PersonalMatchAdapter(Context context, List<PersonalMatch> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);  // returns list item at the specified position
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row, parent, false);
        ImageView gameTypeImages = convertView.findViewById(R.id.pmatch_image);
        TextView textViewLocation = convertView.findViewById(R.id.pmatch_location);
        TextView textViewAddress = convertView.findViewById(R.id.pmatch_address);
        TextView textViewDate = convertView.findViewById(R.id.pmatch_game_date);
        TextView textViewTime = convertView.findViewById(R.id.pmatch_game_time);
        TextView textViewPeopleGoing = convertView.findViewById(R.id.pmatch_people_going);

        PersonalMatch personalMatch = list.get(position);

        // Show location in the TextView
        textViewLocation.setText(personalMatch.getLocation());

        // Show address of the gum in the TextView
        String address = "";
        switch (personalMatch.getLocation()) {
            case "BU FitRec":
                address = "915 Commonwealth Avenue, Boston, MA 02215";
                break;

            case "MIT Recreation":
                address = "120 Vassar St, Cambridge, MA 02139";
                break;

            case "Cambridge Athletic Club":
                address = "215 First St, Cambridge, MA 02142";
                break;

            case "Huntington Avenue YMCA":
                address = "316 Huntington Ave, Boston, MA 02115";
                break;

            case "Boston Ski and Sports Club":
                address = "51 Water St, Watertown, MA 02472";
                break;

            case "BSC Allston":
                address = "15 Gorham St, Allston, MA 02134";
                break;
        }
        textViewAddress.setText(address);

        // Show date in the TextView
        textViewDate.setText(personalMatch.getGameDate());

        // Show time in the TextView
        textViewTime.setText(personalMatch.getStartAt() + " - " + personalMatch.getEndAt());

        // Show the people joined in the TextView
        int numPeopleGoing = personalMatch.getNumInitialPlayers() +
                personalMatch.getNumPlayersJoined();
        String strNumPeopleGoing;
        if(numPeopleGoing == 1) {
            strNumPeopleGoing = String.valueOf(numPeopleGoing) + " person is going";
        } else {
            strNumPeopleGoing = String.valueOf(numPeopleGoing) + " people are going";
        }
        textViewPeopleGoing.setText(strNumPeopleGoing);

        // Set images
        int image = R.drawable.pmatch_1on1;

        switch (personalMatch.getGameType()) {
            case "1 on 1":
                image = R.drawable.pmatch_1on1;
                break;

            case "2 on 2":
                image = R.drawable.pmatch_2on2;
                break;

            case "3 on 3":
                image = R.drawable.pmatch_3on3;
                break;

            case "4 on 4":
                image = R.drawable.pmatch_4on4;
                break;

            case "5 on 5":
                image = R.drawable.pmatch_5on5;
                break;
        }
        gameTypeImages.setImageResource(image);



        return convertView;

    }

    @Override
    public int getCount() {
        return this.list.size();  // returns total of items in the list
    }





}
