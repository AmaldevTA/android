package com.amal.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ParseException;

import com.amal.sqlitedatabase.model.Work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amal on 23/1/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORK = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WORK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," +  KEY_DATE + " DATE" +")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK);
        onCreate(sqLiteDatabase);
    }

    public void addWork(Work work){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, work.getTitle());
        values.put(KEY_DESCRIPTION, work.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String datetime = dateFormat.format(work.getDateCreated());
        values.put(KEY_DATE, datetime);

        db.insert(TABLE_WORK, null, values);
        db.close();
    }

   public Work getWork(String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORK, new String[] { KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_DATE }, KEY_TITLE + "=?",
                new String[] { String.valueOf(title) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Work work = null;
        try {
            work = new Work(cursor.getString(1),
                    cursor.getString(2),  format.parse(cursor.getString(3)));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return work;
    }

    public List<Work> getAllWorks() {
        List<Work> workList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_WORK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        if (cursor.moveToFirst()) {
            do {
                try {
                    Work work = new Work();
                    work.setTitle(cursor.getString(1));
                    work.setDescription(cursor.getString(2));
                    work.setDateCreated(format.parse(cursor.getString(3)));
                    workList.add(work);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return workList;
    }


    public int updateContact(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, work.getTitle());
        values.put(KEY_DESCRIPTION, work.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String datetime = dateFormat.format(work.getDateCreated());
        values.put(KEY_DATE, datetime);

        // updating row
        return db.update(TABLE_WORK, values, KEY_TITLE + " = ?",
                new String[] { String.valueOf(work.getTitle()) });
    }


    public void deleteContact(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORK, KEY_TITLE + " = ?",
                new String[] { String.valueOf(work.getTitle()) });
        db.close();
    }

    public List<Work> getAllWorkByTitle() {
        List<Work> workList = new ArrayList<>();
        //String selectQuery = "SELECT  * FROM " + TABLE_WORK+" ORDER BY " + KEY_TITLE + " DESC";
        String selectQuery = "SELECT  * FROM " + TABLE_WORK+" ORDER BY " + KEY_TITLE + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_WORK, null, null, null, null, null, KEY_TITLE + " DESC", null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        if (cursor.moveToFirst()) {
            do {
                try {
                    Work work = new Work();
                    work.setTitle(cursor.getString(1));
                    work.setDescription(cursor.getString(2));
                    work.setDateCreated(format.parse(cursor.getString(3)));
                    workList.add(work);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return workList;
    }
}
