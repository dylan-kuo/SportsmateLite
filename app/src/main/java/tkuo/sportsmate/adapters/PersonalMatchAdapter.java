package tkuo.sportsmate.adapters;

import android.content.Context;
import java.util.ArrayList;
import tkuo.sportsmate.model.PersonalMatch;

public class PersonalMatchAdapter {
    private Context context;
    private ArrayList<PersonalMatch> arrayList;

    public PersonalMatchAdapter(Context context, ArrayList<PersonalMatch> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


}
