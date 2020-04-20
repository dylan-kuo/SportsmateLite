package tkuo.sportsmate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalMatch implements Parcelable {
    // This is used for Parcelable
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PersonalMatch createFromParcel(Parcel in) {
            return new PersonalMatch(in);
        }

        public PersonalMatch[] newArray(int size) {
            return new PersonalMatch[size];
        }
    };

    private long pmatch_id;
    private long host_player_id;
    private String location;
    private String game_date;
    private String start_at;
    private String end_at;
    private String game_type;
    private int num_initial_players;
    private int num_players_joined;


    public PersonalMatch() {
        // Constructor
    }

    public long getPmatchId() {
        return pmatch_id;
    }

    public void setPmatchId(long pmatch_id) {
        this.pmatch_id = pmatch_id;
    }

    public long getHostPlayerId() {
        return host_player_id;
    }

    public void setHostPlayerId(long host_player_id) {
        this.host_player_id = host_player_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGameDate() {
        return game_date;
    }

    public void setGameDate(String game_date) {
        this.game_date = game_date;
    }

    public String getStartAt() {
        return start_at;
    }

    public void setStartAt(String start_at) {
        this.start_at = start_at;
    }

    public String getEndAt() {
        return end_at;
    }

    public void setEndAt(String end_at) {
        this.end_at = end_at;
    }

    public String getGameType() {
        return game_type;
    }

    public void setGameType(String game_type) {
        this.game_type = game_type;
    }

    public int getNumInitialPlayers() {
        return num_initial_players;
    }

    public void setNumInitialPlayers(int num_initial_players) {
        this.num_initial_players = num_initial_players;
    }

    public int getNumPlayersJoined() {
        return num_players_joined;
    }

    public void setNumPlayersJoined(int num_players_joined) {
        this.num_players_joined = num_players_joined;
    }


    // Parcelling part
    public PersonalMatch(Parcel in) {
        this.pmatch_id = in.readLong();
        this.host_player_id = in.readLong();
        this.location = in.readString();
        this.game_date = in.readString();
        this.start_at = in.readString();
        this.end_at = in.readString();
        this.game_type = in.readString();
        this.num_initial_players = in.readInt();
        this.num_players_joined = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.pmatch_id);
        dest.writeLong(this.host_player_id);
        dest.writeString(this.location);
        dest.writeString(this.game_date);
        dest.writeString(this.start_at);
        dest.writeString(this.end_at);
        dest.writeString(this.game_type);
        dest.writeInt(this.num_initial_players);
        dest.writeInt(this.num_players_joined);
    }

    @Override
    public String toString() {
        return "PersonalMatch{" + "pmatch_id='" + pmatch_id + "\'" + ", host_player_id='" + host_player_id + "\'" +
                ", location='" + location + "\'" + ", game_date='" + game_date + "\'" +
                ", start_at='" + start_at + ", end_at='" + end_at + "\'" +
                ", game_type='" + game_type + ", num_initial_players='" + num_initial_players +
                ", num_players_joined='" + num_players_joined + "}";
    }
}