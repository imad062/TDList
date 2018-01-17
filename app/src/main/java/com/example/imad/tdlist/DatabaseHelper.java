package com.example.imad.tdlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String databaseName = "TDList";
    public static final String databaseTableName = "Tasks";
    public static final String databaseCol = "id";
    public static final String databaseColTask = "task";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + databaseTableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "TASK TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + databaseTableName);
    }

    public boolean insertData(String task)
    {
        long result = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseColTask, task);

        result = db.insert(databaseTableName, null, contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Integer deleteData(String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(databaseTableName, " task = ? ",new String[]{task});
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery(" SELECT * FROM " + databaseTableName, null);
        return data;
    }

}
