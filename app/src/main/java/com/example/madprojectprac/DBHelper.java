package com.example.madprojectprac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "UserDB.db";


    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, email TEXT, password TEXT, birthdate TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
    }


    public boolean insertData(String username, String email, String password, String birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("birthdate", birthdate);
        long result = db.insert("users", null, values);

        Log.d("DEBUG_DB_INSERT", "Insert result: " + result + " for username: " + username);

        return result != -1;  // Return true if data is inserted successfully
    }


    // Check if username or email already exists
    public boolean checkUsernameOrEmail(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? OR email=?", new String[]{username, email});
        return cursor.getCount() > 0;  // Return true if exists
    }


    // Validate login
    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // Retrieve user data by username
    public Cursor getAllUsers(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("DEBUG_ALL_USERS", "Username: " + cursor.getString(0) +
                        ", Email: " + cursor.getString(1) +
                        ", Birthdate: " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return cursor;
    }




//    // Retrieve user data by username
//    public Cursor getUserData(String username) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
//    }


}

