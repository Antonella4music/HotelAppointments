package com.example.antonellab.unitatidecazare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AntonellaB on 20-Oct-16.
 */

public class HotelsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Hotels.db";
    private static final int DATABASE_VERSION = 2;

    public HotelsDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + HotelsDBSchema.HotelsTable.HOTEL_TABLE_NAME +
                        "(" + HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        HotelsDBSchema.HotelsTable.HOTEL_COLUMN_NAME + " TEXT, " +
                        HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ADDRESS + " TEXT, " +
                        HotelsDBSchema.HotelsTable.HOTEL_COLUMN_WEBPAGE + " TEXT, " +
                        HotelsDBSchema.HotelsTable.HOTEL_COLUMN_PHONE + " INTEGER)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HotelsDBSchema.HotelsTable.HOTEL_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertHotel(String name, String address, String webpage, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HotelsDBSchema.HotelsTable.HOTEL_COLUMN_NAME, name);
        contentValues.put(HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ADDRESS, address);
        contentValues.put(HotelsDBSchema.HotelsTable.HOTEL_COLUMN_WEBPAGE, webpage);
        contentValues.put(HotelsDBSchema.HotelsTable.HOTEL_COLUMN_PHONE, age);

        db.insert(HotelsDBSchema.HotelsTable.HOTEL_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getHotel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + HotelsDBSchema.HotelsTable.HOTEL_TABLE_NAME + " WHERE " +
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllHotels()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ID,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_NAME,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ADDRESS,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_WEBPAGE,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_PHONE
        };

        String sortOrder = HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ID + " DESC";

        Cursor c = db.query(
                HotelsDBSchema.HotelsTable.HOTEL_TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }

}