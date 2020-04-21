package tkuo.sportsmate.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tkuo.sportsmate.model.PersonalMatch;
import tkuo.sportsmate.model.Player;
import tkuo.sportsmate.model.User;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_PERSONAL_MATCH = "personal_match";
    private static final String TABLE_PLAYER = "player";
    private static final String TABLE_PERSONAL_MATCH_PLAYERS = "personal_match_players";

    // User Table Column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_IMAGE_URI = "user_image_uri";

    // personal_match table column names
    private static final String COLUMN_PERSONAL_MATCH_ID = "pmatch_id";
    private static final String COLUMN_PERSONAL_MATCH_PLAYER_ID = "host_player_id";
    private static final String COLUMN_PERSONAL_MATCH_LOCATION = "location";
    private static final String COLUMN_PERSONAL_MATCH_DATE = "date";
    private static final String COLUMN_PERSONAL_MATCH_START_AT = "start_at";
    private static final String COLUMN_PERSONAL_MATCH_END_AT = "end_at";
    private static final String COLUMN_PERSONAL_MATCH_GAME_TYPE = "game_type";
    private static final String COLUMN_PERSONAL_MATCH_INIT_PLAYERS = "num_initial_players";
    private static final String COLUMN_PERSONAL_MATCH_NUM_PLAYER_JOINED = "num_players_joined";

    // player Table Column names
    private static final String COLUMN_PLAYER_PLAYER_ID = "player_id";
    private static final String COLUMN_PLAYER_USER_ID = "user_id";

    // personal_match_players Table Column names
    private static final String COLUMN_PERSONAL_MATCH_PLAYERS_MATCH_ID = "match_id";
    private static final String COLUMN_PERSONAL_MATCH_PLAYERS_PLAYER_ID = "p_id";


    // *** USER TABLE ***
    /** Create user table sql query */
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRST_NAME
            + " TEXT," + COLUMN_USER_LAST_NAME + " TEXT," + COLUMN_USER_GENDER + " TEXT," +
            COLUMN_USER_USERNAME + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_IMAGE_URI
            + " TEXT" + ")";

    /** Drop user table sql query */
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    // *** PLAYER TABLE ***
    /** Create player table sql query */
    private String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
            + COLUMN_PLAYER_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PLAYER_USER_ID
            + " INTEGER,"
            +  " FOREIGN KEY (" + COLUMN_PLAYER_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    /** Drop player table sql query */
    private String DROP_PLAYER_TABLE = "DROP TABLE IF EXISTS " + TABLE_PLAYER;


    // *** PERSONAL_MATCH TABLE ***
    /** Create personal_match table sql query */

    private String CREATE_PERSONAL_MATCH_TABLE = "CREATE TABLE " + TABLE_PERSONAL_MATCH + "("
            + COLUMN_PERSONAL_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PERSONAL_MATCH_PLAYER_ID
            + " INTEGER," + COLUMN_PERSONAL_MATCH_LOCATION + " TEXT," + COLUMN_PERSONAL_MATCH_DATE + " TEXT," +
            COLUMN_PERSONAL_MATCH_START_AT + " TEXT," + COLUMN_PERSONAL_MATCH_END_AT + " TEXT," + COLUMN_PERSONAL_MATCH_GAME_TYPE
            + " TEXT," + COLUMN_PERSONAL_MATCH_INIT_PLAYERS + " INTEGER,"
            + COLUMN_PERSONAL_MATCH_NUM_PLAYER_JOINED + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_PERSONAL_MATCH_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER + "(" + COLUMN_PLAYER_PLAYER_ID + "));";


    /** Drop personal_match table sql query */
    private String DROP_PERSONAL_MATCH_TABLE = "DROP TABLE IF EXISTS " + TABLE_PERSONAL_MATCH;


    // *** PERSONAL_MATCH_PLAYER TABLE ***
    /** Create personal_match_players table sql query */
    private String CREATE_PERSONAL_MATCH_PLAYER_TABLE = "CREATE TABLE " + TABLE_PERSONAL_MATCH_PLAYERS + "("
            + COLUMN_PERSONAL_MATCH_PLAYERS_MATCH_ID + " INTEGER," + COLUMN_PERSONAL_MATCH_PLAYERS_PLAYER_ID
            + " INTEGER,"
            + "PRIMARY KEY (" + COLUMN_PERSONAL_MATCH_PLAYERS_MATCH_ID + ", " + COLUMN_PERSONAL_MATCH_PLAYERS_PLAYER_ID + "),"
            +  " FOREIGN KEY (" + COLUMN_PERSONAL_MATCH_PLAYERS_MATCH_ID + ") REFERENCES " + TABLE_PERSONAL_MATCH + "(" + COLUMN_PERSONAL_MATCH_ID + "),"
            +  " FOREIGN KEY (" + COLUMN_PERSONAL_MATCH_PLAYERS_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER + "(" + COLUMN_PLAYER_PLAYER_ID + "));";

    /** Drop player table sql query */
    private String DROP_PERSONAL_MATCH_PLAYER_TABLE = "DROP TABLE IF EXISTS " + TABLE_PERSONAL_MATCH_PLAYERS;


    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Table
        db.execSQL(CREATE_USER_TABLE);
        // Create Player Table
        db.execSQL(CREATE_PLAYER_TABLE);
        // Create Personal Match Table
        db.execSQL(CREATE_PERSONAL_MATCH_TABLE);
        // Create Personal Match Players Table
        db.execSQL(CREATE_PERSONAL_MATCH_PLAYER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop User Table if exists
        db.execSQL(DROP_USER_TABLE);
        // Drop Player Table if exists
        db.execSQL(DROP_PLAYER_TABLE);
        // Drop Personal Match Table if exists
        db.execSQL(DROP_PERSONAL_MATCH_TABLE);
        // Drop Personal Match Player Table if exists

        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to add user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_IMAGE_URI, user.getImageUri());

        // Inserting Row
        long insertedUserId = db.insert(TABLE_USER, null, values);  // auto incremented id
        db.close();

        addPlayer(insertedUserId);
    }


    public void addPlayer(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYER_USER_ID, userId);
        db.insert(TABLE_PLAYER, null, values);
        db.close();
    }


    /**
     * This method is to add user record
     *
     * @param personalMatch
     */
    public void addPersonalMatch(PersonalMatch personalMatch) {

        SQLiteDatabase db = this.getWritableDatabase();

        /*
        String addPersonalMatch = "INSERT INTO" + TABLE_PERSONAL_MATCH + "(" +
                COLUMN_PERSONAL_MATCH_PLAYER_ID  + ", " + COLUMN_PERSONAL_MATCH_LOCATION  + ", " +
                COLUMN_PERSONAL_MATCH_DATE  + ", " + COLUMN_PERSONAL_MATCH_START_AT + ", " +
                COLUMN_PERSONAL_MATCH_END_AT  + ", " + COLUMN_PERSONAL_MATCH_GAME_TYPE + ", " +
                COLUMN_PERSONAL_MATCH_INIT_PLAYERS  + ", " + COLUMN_PERSONAL_MATCH_NUM_PLAYER_JOINED + ")" +
                "VALUES((SELECT " + COLUMN_PLAYER_PLAYER_ID +
                " FROM " + TABLE_PLAYER +
                " WHERE " + COLUMN_PLAYER_USER_ID + "=" + currentUser.getId() + ")," +
                personalMatch.getLocation() + ", " + personalMatch.getGameDate() + ", " +
                personalMatch.getStartAt() + ", " + personalMatch.getEndAt() + ", " +
                personalMatch.getGameType() + ", " + personalMatch.getNumInitialPlayers() + ", " +
                personalMatch.getNumPlayersJoined() + ");";

        db.execSQL(addPersonalMatch);
        */



        // **** NOTE ****
        // Below is the original approach but it cannot handle foreign key,
        // since "ContentValues" supports only plain values, not SQL expressions.

        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSONAL_MATCH_PLAYER_ID, personalMatch.getHostPlayerId());
        values.put(COLUMN_PERSONAL_MATCH_LOCATION, personalMatch.getLocation());
        values.put(COLUMN_PERSONAL_MATCH_DATE, personalMatch.getGameDate());
        values.put(COLUMN_PERSONAL_MATCH_START_AT, personalMatch.getStartAt());
        values.put(COLUMN_PERSONAL_MATCH_END_AT, personalMatch.getEndAt());
        values.put(COLUMN_PERSONAL_MATCH_GAME_TYPE, personalMatch.getGameType());
        values.put(COLUMN_PERSONAL_MATCH_INIT_PLAYERS, personalMatch.getNumInitialPlayers());
        values.put(COLUMN_PERSONAL_MATCH_NUM_PLAYER_JOINED, personalMatch.getNumPlayersJoined());

        // Inserting Row
        db.insert(TABLE_PERSONAL_MATCH, null, values);
        db.close();

    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // Array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_GENDER,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_IMAGE_URI,
        };
        // Sorting orders
        String sortOrder =
                COLUMN_USER_FIRST_NAME + " ASC";
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_first_name,user_last_name,user_gender,user_password FROM user ORDER BY user_first_name;
         */
        Cursor cursor = db.query(TABLE_USER,
                columns,          // columns to return
                null,    // columns for the WHERE clause
                null, // the values for the WHERE clause
                null,     // group the rows
                null,      // filter by row groups
                sortOrder);        // the sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setImageUri(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE_URI)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Return user list
        return userList;
    }


    /**
     * This method is to fetch the user info and return the all the user records
     * @param username
     * @return list
     */
    public List<User> getSingleUser(String username) {
        // Array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_GENDER,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_IMAGE_URI
        };

        // Selection criteria
        String selection = COLUMN_USER_USERNAME + " = ?";

        // Selection argument
        String[] selectionArgs = {username};

        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_first_name,user_last_name,user_gender,user_password FROM user WHERE user_username = 'demoUsername';
         */
        Cursor cursor = db.query(TABLE_USER,
                columns,          // columns to return
                selection,    // columns for the WHERE clause
                selectionArgs, // the values for the WHERE clause
                null,     // group the rows
                null,      // filter by row groups
                null);        // the sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setImageUri(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE_URI)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Return user list
        return userList;
    }


    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_IMAGE_URI, user.getImageUri());

        // Updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @return true/false
     */
    public boolean checkUser(String username) {

        // Array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria
        String selection = COLUMN_USER_USERNAME + " = ?";

        // Selection argument
        String[] selectionArgs = {username};

        // Query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_username = 'demoUsername';
         */
        Cursor cursor = db.query(TABLE_USER,  // Table to query
                columns,                      // Columns to return
                selection,                    // Columns for the WHERE clause
                selectionArgs,                // The values for the WHERE clause
                null,                // Group the row
                null,                 // Filter by row groups
                null);               // The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {

        // Array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // Selection criteria
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // Selection arguments
        String[] selectionArgs = {username, password};

        // Query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_username = 'demoUsername' AND user_password = 'demoPassword';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                     // columns to return
                selection,                    // columns for the WHERE clause
                selectionArgs,                 // the values for the WHERE clause
                null,                  //  group the rows
                null,                   //filter by row groups
                null);                  //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method is to fetch the user info and return the all the player records
     * @param user_id
     * @return list
     */
    public List<Player> getSinglePlayer(Long user_id) {
        // Array of columns to fetch
        String[] columns = {
                COLUMN_PLAYER_PLAYER_ID,
                COLUMN_PLAYER_USER_ID
        };

        // Selection criteria
        String selection = COLUMN_PLAYER_USER_ID + " = ?";

        // Selection argument
        String[] selectionArgs = {Long.toString(user_id)};

        List<Player> playerList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Query the user table
        /**
         * Here query function is used to fetch records from player table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT player_id, user_id FROM player WHERE user_id = 2;
         */
        Cursor cursor = db.query(TABLE_PLAYER,
                columns,          // columns to return
                selection,    // columns for the WHERE clause
                selectionArgs, // the values for the WHERE clause
                null,     // group the rows
                null,      // filter by row groups
                null);        // the sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setPlayerId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_PLAYER_ID))));
                player.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_USER_ID))));
                // Adding user record to list
                playerList.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Return player list
        return playerList;
    }


}




