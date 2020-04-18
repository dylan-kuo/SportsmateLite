package tkuo.sportsmate.model;
import android.net.Uri;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {
    // This is used for Parcelable
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String imageUri;

    public User() {
        // Constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    // Parcelling part
    public User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.gender = in.readString();
        this.imageUri = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.gender);
        dest.writeString(this.imageUri);
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + "\'" + ", username='" + username + "\'" +
                ", firstName='" + firstName + "\'" + ", lastName='" + lastName + "\'" +
                ", gender='" + gender + "\'" + "}";
    }
}