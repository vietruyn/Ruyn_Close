package com.example.admin.prclosechat_android.handler;

/**
 * Created by Admin on 1/10/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.prclosechat_android.listener.UserListener;
import com.example.admin.prclosechat_android.model.User;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper implements UserListener {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UserDatabase.db";
    private static final String TABLE_NAME = "user_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";

    String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT)";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, user.getName());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem", e + "");
        }
    }

    @Override
    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> userList = null;
        try{
            userList = new ArrayList<User>();
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    User user = new User();
                    user.setId(cursor.getInt(0));
                    user.setName(cursor.getString(1));
                    userList.add(user);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error", e + "");
        }
        return userList;
    }

    @Override
    public int getUserCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error", e + "");
        }
        return 0;
    }
}