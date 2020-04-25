package tkuo.sportsmate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalMatchPlayers implements Parcelable {
    // This is used for Parcelable
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PersonalMatchPlayers createFromParcel(Parcel in) {
            return new PersonalMatchPlayers(in);
        }

        public PersonalMatchPlayers[] newArray(int size) {
            return new PersonalMatchPlayers[size];
        }
    };

    private long match_id; //  person match id
    private long p_id; // player id

    public long getMatchId() {
        return match_id;
    }

    public void setMatchId(long match_id) {
        this.match_id = match_id;
    }

    public long getPid() {
        return p_id;
    }

    public void setPid(long p_id) {
        this.p_id = p_id;
    }

    // Parcelling part
    public PersonalMatchPlayers(Parcel in) {
        this.match_id = in.readLong();
        this.p_id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.match_id);
        dest.writeLong(this.p_id);
    }

    @Override
    public String toString() {
        return "PersonalMatchPlayers{" + "match_id='" + match_id + "\'" + ", p_id='" + p_id + "\'"  + "}";
    }
}
