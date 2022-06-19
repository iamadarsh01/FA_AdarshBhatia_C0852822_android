package com.firstapp.fa_adarshbhatia_c0852822_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "adarshDB";
    public static final String TABLE_NAME = "placestb";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ TABLE_NAME +"(id integer primary key autoincrement, placename text, address text, latitude text, longitude text, visited text, pdate text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insert(Bean b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("placename", b.getPlacename());
        contentValues.put("address", b.getAddress());
        contentValues.put("latitude", b.getLatitude());
        contentValues.put("longitude", b.getLongitude());
        contentValues.put("visited", b.getVisited());
        contentValues.put("pdate", b.getCreatedon());

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public List<Bean> getVisitedPlaces() {
        List<Bean> li = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(5).equalsIgnoreCase("visited"))
                {
                    Bean b = new Bean();

                    b.setId(cursor.getInt(0));
                    b.setPlacename(cursor.getString(1));
                    b.setAddress(cursor.getString(2));
                    b.setLatitude(cursor.getString(3));
                    b.setLongitude(cursor.getString(4));
                    b.setVisited(cursor.getString(5));
                    b.setCreatedon(cursor.getString(6));

                    li.add(b);
                }
            } while (cursor.moveToNext());
        }

        return li;
    }

    public List<Bean> getToVisitedPlaces() {
        List<Bean> li = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(!cursor.getString(5).equalsIgnoreCase("visited"))
                {
                    Bean b = new Bean();

                    b.setId(cursor.getInt(0));
                    b.setPlacename(cursor.getString(1));
                    b.setAddress(cursor.getString(2));
                    b.setLatitude(cursor.getString(3));
                    b.setLongitude(cursor.getString(4));
                    b.setVisited(cursor.getString(5));
                    b.setCreatedon(cursor.getString(6));

                    li.add(b);
                }
            } while (cursor.moveToNext());
        }

        return li;
    }

    public int updateFavPlace(Bean b) {
        Log.e("id...", ""+b.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("placename", b.getPlacename());
        contentValues.put("address", b.getAddress());
        contentValues.put("latitude", b.getLatitude());
        contentValues.put("longitude", b.getLongitude());
        contentValues.put("visited", b.getVisited());
        contentValues.put("pdate", b.getCreatedon());

        return db.update(TABLE_NAME, contentValues, "id" + " = ?",
                new String[] { String.valueOf(b.getId()) });
    }

    public void deleteFavPlace(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}