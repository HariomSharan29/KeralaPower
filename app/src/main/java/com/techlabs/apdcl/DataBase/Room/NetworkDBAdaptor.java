package com.techlabs.apdcl.DataBase.Room;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.techlabs.apdcl.models.NetworkListResponse;

import java.util.ArrayList;

public class NetworkDBAdaptor {

    public static final int Network_DATABASE_VERSION = 1;
    public static final String Network_DATABASE_NAME = "DBNetwork.db";

    public static final String TABLE_NAME = "Networklist";
    public static final String COL_ID = "_ID";
    public static final String NetworkId = "NetworkId";
    public static final String Group1 = "Group1";
    public static final String Group2 = "Group2";
    public static final String Group3 = "Group3";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY," + NetworkId + " TEXT," + Group1 + " TEXT," + Group2 + " TEXT," + Group3 + " TEXT)";
    private static NetworkDBAdaptor mDBAdepter;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    public NetworkDBAdaptor(Context context) {
        mDBHelper = new DBHelper(context, Network_DATABASE_NAME, null, Network_DATABASE_VERSION);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public static NetworkDBAdaptor getInstance(Context context) {
        if (null == mDBAdepter) {
            mDBAdepter = new NetworkDBAdaptor(context);
        }
        return mDBAdepter;
    }

    public long insertNetwork(NetworkListResponse list) {

        long rowID = 0;

        ContentValues values = new ContentValues();
        //  values.put(SubstationName,list.getSubstationName());
        values.put(NetworkId, list.getNetworkId());
        values.put(Group1, list.getGroup1());
        values.put(Group2, list.getGroup2());
        values.put(Group3, list.getGroup3());

        rowID = mDatabase.insert(TABLE_NAME, null, values);
        Log.d("TAG", "ROW ID:" + rowID);
        return rowID;
    }

    public int deletedataforNetwork() {
        int count = mDatabase.delete(TABLE_NAME, "1", null);
        return count;
    }

    public ArrayList<String> getdistinctgroup3() {
        String[] columnNames = new String[]{Group3};

        //  Log.d("TAG","WHERE:"+whereClause);
        Cursor cursor = mDatabase.query(true, TABLE_NAME, columnNames, null, null, null, null, null, null);

        int cursorCount = cursor.getCount();
        ArrayList<String> list = new ArrayList<>();
        Log.d("TAG", "DATA FOUND:" + cursorCount);

        while (cursor.moveToNext()) {
            int colIndexGroup3 = cursor.getColumnIndex(Group3);

            String Group3 = cursor.getString(colIndexGroup3);

            list.add(Group3);

        }
        return list;
    }

    public ArrayList<String> getdistinctgroup2(String group3) {
        String[] columnNames = new String[]{Group2};

        Cursor cursor = mDatabase.rawQuery("select DISTINCT " + Group2 + " from " + TABLE_NAME + " where " + Group3 + " = '" + group3 + "'", null);

        int cursorCount = cursor.getCount();
        ArrayList<String> list = new ArrayList<>();
        Log.d("TAG", "DATA FOUND:" + cursorCount);

        while (cursor.moveToNext()) {
            int colIndexGroup3 = cursor.getColumnIndex(Group2);

            String Group3 = cursor.getString(colIndexGroup3);

            list.add(Group3);

        }
        return list;
    }

    public ArrayList<String> getdistinctgroup1(String group3, String group2) {
        String[] columnNames = new String[]{Group1};

        //  Log.d("TAG","WHERE:"+whereClause);
        Cursor cursor = mDatabase.rawQuery("select DISTINCT " + Group1 + " from " + TABLE_NAME + " where " + Group3 + " = '" + group3 + "' and " + Group2 + " = '" + group2 + "'", null);

        int cursorCount = cursor.getCount();
        ArrayList<String> list = new ArrayList<>();
        Log.d("TAG", "DATA FOUND:" + cursorCount);
        while (cursor.moveToNext()) {
            int colIndexGroup1 = cursor.getColumnIndex(Group1);
            String Group1 = cursor.getString(colIndexGroup1);
            list.add(Group1);
        }
        return list;
    }

    public ArrayList<String> getdistinctNetworkId(String group3, String group2, String group1) {
        String[] columnNames = new String[]{Group1};

        Cursor cursor = mDatabase.rawQuery("select DISTINCT " + NetworkId + " from " + TABLE_NAME + " where " + Group3 + " = '" + group3 + "' and " + Group2 + " = '" + group2 + "' and " + Group1 + " = '" + group1 + "'", null);

        int cursorCount = cursor.getCount();
        ArrayList<String> list = new ArrayList<>();
        Log.d("TAG", "DATA FOUND:" + cursorCount);

        while (cursor.moveToNext()) {
            int colIndexNetworkId = cursor.getColumnIndex(NetworkId);

            String NetworkId = cursor.getString(colIndexNetworkId);

            list.add(NetworkId);

        }

        return list;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(SQL_CREATE_TABLE);

        }
    }

}

