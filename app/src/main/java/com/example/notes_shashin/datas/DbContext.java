package com.example.notes_shashin.datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {
    public static SQLiteDatabase sqLiteDb;
    public DbContext(Context context) {
        super(context, "DbNotes", null, 1);
        sqLiteDb = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Notes (" +
                "Id integer primary key autoincrement," +
                "Title text," +
                "Text text," +
                "Date text," +
                "Color text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
