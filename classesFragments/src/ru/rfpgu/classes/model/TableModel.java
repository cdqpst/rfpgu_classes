package ru.rfpgu.classes.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ru.rfpgu.classes.MyActivity;


public class TableModel {

    public static class Classes {

        public static final String KEY_ROW_ID = "_id";
        public static final String KEY_SUBJECT = "subject";
        public static final String KEY_TEACHER_ID = "teacher_id";
        public static final String KEY_ROOM_NUMBER = "room_number";
        public static final String KEY_LESSON_NUMBER = "lesson_number";
        public static final String KEY_GROUP_ID = "group_id";
        public static final String KEY_DAY = "day_of_week";
        public static final String KEY_WEEK= "odd_week";
        public static final String KEY_LAST_UPDATE = "last_update";

        public static final String SQLITE_TABLE = "classes";

        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROW_ID + " integer PRIMARY KEY," +
                        KEY_TEACHER_ID + " integer," +
                        KEY_SUBJECT + " text," +
                        KEY_ROOM_NUMBER + " varchar(100)," +
                        KEY_LESSON_NUMBER + " integer(4)," +
                        KEY_GROUP_ID + " integer(10)," +
                        KEY_DAY + " integer(2)," +
                        KEY_WEEK+ " integer(1)," +
                        KEY_LAST_UPDATE + " varchar(15));";


        public static void onCreate(SQLiteDatabase db) {
            Log.w(MyActivity.LOG_TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MyActivity.LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public static class Teachers {

        public static final String KEY_ROW_ID = "_id";
        public static final String KEY_TEACHER_NAME = "teacher_name";
        public static final String KEY_LAST_UPDATE = "last_update";

        public static final String SQLITE_TABLE = "teachers";


        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROW_ID + " integer PRIMARY KEY," +
                        KEY_LAST_UPDATE + " varchar(15)," +
                        KEY_TEACHER_NAME + " varchar(255));";

        public static void onCreate(SQLiteDatabase db) {
            Log.w(MyActivity.LOG_TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MyActivity.LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }
}
