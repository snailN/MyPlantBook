package com.snail.myplantbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "plantDB.db";
    public static final String TABLE_PLANTS = "plants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLANTNAME = "plantname";
    public static final String COLUMN_PLANTINFO = "plantinfo";
    public static final String COLUMN_PLANTIMAGE = "plantimage";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PLANTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLANTNAME + " TEXT, " +
                COLUMN_PLANTINFO + " TEXT, " +
                COLUMN_PLANTIMAGE + " TEXT " +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
        onCreate(db);
        /*if(oldVersion < 3){
            db.execSQL("ALTER TABLE " + TABLE_PLANTS + " ADD " + COLUMN_PLANTIMAGE + " TEXT;");
        }*/
    }

    public void addPlant(Plants plant) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLANTNAME, plant.get_plantname());
        values.put(COLUMN_PLANTINFO, plant.get_plantinfo());
        values.put(COLUMN_PLANTIMAGE, plant.get_plantimage());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PLANTS, null, values);
        db.close();
    }

    public void deletePlant(String plantID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PLANTS + " WHERE " + COLUMN_ID + "=\"" + plantID + "\";");
    }

    public Cursor databaseToPlantDetails(String i) {

        ArrayList<String> testarray = new ArrayList<String>();
        Plants plantToPrint = new Plants();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.query(TABLE_PLANTS, null, "_id= ?", new String[] {"" + i}, null, null, null);

        return c;

        /*if(c!=null){
            c.moveToFirst();
            String[] columnNames = c.getColumnNames();
            for(String name: columnNames){

                if(c.getBlob(c.getColumnIndex(name)) instanceof byte[]){
                    byte[] tempbyte = c.getBlob(c.getColumnIndex(name));
                    String s = new String(tempbyte);
                    testarray.add(s);
                }else {
                    testarray.add(c.getString(c.getColumnIndex(name)));
                }

                /*if(name == COLUMN_PLANTNAME){
                    plantToPrint.set_plantname(c.getString(c.getColumnIndex(name)));
                    testarray.add(plantToPrint.get_plantname());
                }else if(name == COLUMN_PLANTINFO){
                    plantToPrint.set_plantinfo(c.getString(c.getColumnIndex(name)));
                    testarray.add(plantToPrint.get_plantinfo());
                }else if(name == COLUMN_PLANTIMAGE){
                    byte[] tempbyte = c.getBlob(c.getColumnIndex(name));
                    plantToPrint.set_plantimage(tempbyte);
                }else{
                    plantToPrint.set_id(c.getInt(c.getColumnIndex(name)));
                }*/

                /*if(c.getBlob(c.getColumnIndex(name)) instanceof byte[]){
                    String byteToString = new String(c.getBlob(c.getColumnIndex(name)));
                    dbString.add(byteToString);
                }else{
                    dbString.add(c.getString(c.getColumnIndex(name)));
                }

            }
        }*/

        //db.close();
        //return dbString;

        /*while(!c.isAfterLast()){

            String[] columnNames = c.getColumnNames();
            for(String name: columnNames){
                dbString += c.getString(c.getColumnIndex(name));
                dbString += "\t";
            }
            dbString += "\n";*/
            /*if(c.getString(c.getColumnIndex("plantname")) != null){
                dbString += c.getString(c.getColumnIndex("plantname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
       return dbString;*/
    }

    public Cursor searchPlantFromDatabase(String j) {

        SQLiteDatabase db = getWritableDatabase();
        String[] selectedColumns = {COLUMN_ID, COLUMN_PLANTNAME, COLUMN_PLANTINFO, COLUMN_PLANTIMAGE};
        Cursor c = db.query(TABLE_PLANTS, selectedColumns, "plantname= ?", new String[]{"" + j}, null, null, COLUMN_ID + " DESC");
        return c;
    }

    public Cursor plantTableReturn() {

        SQLiteDatabase db = getWritableDatabase();
        String[] selectedColumns = {COLUMN_ID, COLUMN_PLANTNAME, COLUMN_PLANTINFO, COLUMN_PLANTIMAGE};
        Cursor c = db.query(TABLE_PLANTS, selectedColumns, null, null, null, null, COLUMN_ID + " DESC");
        return c;

    }
}
