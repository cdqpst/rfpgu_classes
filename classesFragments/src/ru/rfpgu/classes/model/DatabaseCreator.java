package ru.rfpgu.classes.model;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "db_classes.db";
    private static final int DATABASE_VERSION = 1;

    DatabaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableModel.Classes.onCreate(db);
        TableModel.Teachers.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TableModel.Classes.onUpgrade(db, oldVersion, newVersion);
        TableModel.Teachers.onUpgrade(db, oldVersion, newVersion);
    }

}