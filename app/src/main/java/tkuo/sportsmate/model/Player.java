package tkuo.sportsmate.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Player implements Parcelable {
    // This is used for Parcelable
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    private long player_id;
    private long user_id;

    public Player()
    {
        // Constructor
    }

    public long getPlayerId() {
        return player_id;
    }

    public void setPlayerId(long player_id) {
        this.player_id = player_id;
    }

    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    // Parcelling part
    public Player(Parcel in) {
        this.player_id = in.readLong();
        this.user_id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.player_id);
        dest.writeLong(this.user_id);
    }

    @Override
    public String toString() {
        return "Player{" + "player_id='" + player_id + "\'" + ", user_id='" + user_id + "\'"  + "}";
    }
}