package com.example.footx.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    //change version when upgraded
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Form.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.COLUMN_NAME + " TEXT," +
                DBContract.Form.COLUMN_MDP + " TEXT," +
                DBContract.Form.COLUMN_TEAMID + " TEXT)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        onCreate(db);
    }

    public void insertUser(String pseudo, String mdp, String teamid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.Form.COLUMN_NAME, pseudo);
        values.put(DBContract.Form.COLUMN_MDP, mdp);
        values.put(DBContract.Form.COLUMN_TEAMID, teamid);


        db.insert(DBContract.Form.TABLE_NAME, null, values);
    }

    public String recupEquipe(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+DBContract.Form.COLUMN_TEAMID+" FROM "+DBContract.Form.TABLE_NAME+
                " WHERE "+DBContract.Form.COLUMN_NAME+" = ?";

        Cursor  cursor = db.rawQuery(query, new String[]{username});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        @SuppressLint("Range")
        String tid = cursor.getString(cursor.getColumnIndex(DBContract.Form.COLUMN_TEAMID));
        return tid;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String s;
        Cursor c = db.query(DBContract.Form.TABLE_NAME, null, DBContract.Form.COLUMN_NAME+"=? AND "+DBContract.Form.COLUMN_MDP+"=?",
                new String[]{username,password}, null, null, null);

        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return false;
        } else {
            c.close();
            db.close();
            return true;
        }
    }
}