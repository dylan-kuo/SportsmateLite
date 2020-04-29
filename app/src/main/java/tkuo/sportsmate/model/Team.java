package tkuo.sportsmate.model;
import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {
    // This is used for Parcelable
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    private long team_id;
    private long admin_id;
    private String team_name;

    public Team() {
        // Constructor
    }

    public long getTeamId() {
        return team_id;
    }

    public void setTeamId(long team_id) {
        this.team_id = team_id;
    }

    public long getAdminId() {
        return admin_id;
    }

    public void setAdminId(long admin_id) {
        this.admin_id = admin_id;
    }

    public String getTeamName() {
        return team_name;
    }

    public void setTeamName(String team_name) {
        this.team_name = team_name;
    }

    // Parcelling part
    public Team(Parcel in) {
        this.team_id = in.readLong();
        this.admin_id = in.readLong();
        this.team_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.team_id);
        dest.writeLong(this.admin_id);
        dest.writeString(this.team_name);
    }

    @Override
    public String toString() {
        return "Team{" + "team_id='" + team_id + "\'" + ", admin_id='" + admin_id + "\'" +
                ", team_name='" + team_name + "\'" + "}";
    }
}
