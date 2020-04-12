package tkuo.sportsmate.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tkuo.sportsmate.model.User;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRST_NAME
            + " TEXT," + COLUMN_USER_LAST_NAME + " TEXT," + COLUMN_USER_GENDER + " TEXT," +
            COLUMN_USER_USERNAME + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // Drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


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
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop User Table if exists
        db.execSQL(DROP_USER_TABLE);

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

        // Inserting Row
        db.insert(TABLE_USER, null, values);
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
                COLUMN_USER_PASSWORD
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
                COLUMN_USER_PASSWORD
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
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
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
}




