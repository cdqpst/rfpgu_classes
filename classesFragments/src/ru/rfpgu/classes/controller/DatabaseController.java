package ru.rfpgu.classes.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import ru.rfpgu.classes.model.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseController {

    public static int getMaxId(Context context, Uri uri, String columnName)  {
        String []projection =  new String[]{"MAX("+ columnName +")"};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static int getLastUpdate(Context context, Uri uri)  {
        String []projection =  new String[]{"MAX("+ TableModel.Classes.KEY_LAST_UPDATE +")"};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static ArrayList<Integer> getIds(Context context, Uri uri) {
        ArrayList<Integer> idList = null;
        String []projection =  new String[]{"_id"};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()) {

            int id = cursor.getColumnIndex("_id");
            idList = new ArrayList<Integer>();
            do{
                idList.add(cursor.getInt(id));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return idList;
    }

    public static ArrayList<Integer> getGroups(Context context, Uri uri) {
        ArrayList<Integer> groupList = null;
        String []projection =  new String[]{"DISTINCT " + TableModel.Classes.KEY_GROUP_ID};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, TableModel.Classes.KEY_GROUP_ID);
        if(cursor.moveToFirst()) {

            int group = cursor.getColumnIndex(TableModel.Classes.KEY_GROUP_ID);
            groupList = new ArrayList<Integer>();
            do{
                groupList.add(cursor.getInt(group));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return groupList;
    }

    public static ArrayList<String> getTeachersIds(Context context, Uri uri) {
        ArrayList<String> teacherList = null;
        String []projection =  new String[]{"DISTINCT " + TableModel.Teachers.KEY_ROW_ID};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, TableModel.Teachers.KEY_TEACHER_NAME);
        if(cursor.moveToFirst()) {

            int teacher = cursor.getColumnIndex(TableModel.Teachers.KEY_ROW_ID);
            teacherList = new ArrayList<String>();
            do{
                teacherList.add(cursor.getString(teacher));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return teacherList;
    }

    public static int getTeacherId (Context context, String teacherName) {

        String selection = TableModel.Teachers.KEY_TEACHER_NAME + "=?";
        String selectionArgs[] = new String[] { teacherName };
        int teacherId = -1;

        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI_TEACHERS, null, selection, selectionArgs, null);

        if(cursor.moveToFirst()) {
            int teacherColumnName = cursor.getColumnIndex(TableModel.Teachers.KEY_ROW_ID);
            teacherId = cursor.getInt(teacherColumnName);
        }
        cursor.close();
        return teacherId;
    }

    public static ArrayList<String> getTeachersNames(Context context, Uri uri) {
        ArrayList<String> teacherNameList = null;
        String []projection =  new String[]{"DISTINCT " + TableModel.Teachers.KEY_TEACHER_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, TableModel.Teachers.KEY_TEACHER_NAME);
        if(cursor.moveToFirst()) {

            int teacherName = cursor.getColumnIndex(TableModel.Teachers.KEY_TEACHER_NAME);
            teacherNameList = new ArrayList<String>();
            do{
                teacherNameList.add(cursor.getString(teacherName));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return teacherNameList;
    }

    public static ArrayList<ObjectModel.Teacher> getTeachers(Context context, Uri uri) {


        ArrayList<ObjectModel.Teacher> contentList = null;
        ArrayList<String> teacherNameList = null;

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if(cursor.moveToFirst()) {
            contentList = new ArrayList<ObjectModel.Teacher>();

            int teacherName = cursor.getColumnIndex(TableModel.Teachers.KEY_TEACHER_NAME);
            int teacherId = cursor.getColumnIndex(TableModel.Teachers.KEY_ROW_ID);
            int teacherLastUpdate = cursor.getColumnIndex(TableModel.Teachers.KEY_LAST_UPDATE);
            teacherNameList = new ArrayList<String>();
            do{
                ObjectModel.Teacher teacher = new ObjectModel.Teacher();
                teacher.setTeacherId(cursor.getInt(teacherId));
                teacher.setTeacherName(cursor.getString(teacherName));
                teacher.setLastUpdate(cursor.getString(teacherLastUpdate));
                contentList.add(teacher);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return contentList;
    }


    public static ArrayList<String> getGroupForSettings(Context context, Uri uri) {
        ArrayList<String> groupList = null;
        String []projection =  new String[]{"DISTINCT " + TableModel.Classes.KEY_GROUP_ID};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, TableModel.Classes.KEY_GROUP_ID);
        if(cursor.moveToFirst()) {

            int group = cursor.getColumnIndex(TableModel.Classes.KEY_GROUP_ID);
            groupList = new ArrayList<String>();
            do{
                groupList.add(String.valueOf(cursor.getInt(group)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return groupList;
    }

    public static void deleteRows(Context context, Uri tableUri, ArrayList<Integer> delList) {
        Uri uri = null;
        for(int i = 0; i < delList.size(); i++) {
            uri = Uri.parse(tableUri + "/" + delList.get(i) + "");
            context.getContentResolver().delete(uri, null, null);
        }
    }

    public static ArrayList<ObjectModel.Classes> getClasses (Context context) {

        ArrayList<ObjectModel.Classes> contentList = null;

        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI_CLASSES, null, null, null, " _id DESC");

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(TableModel.Classes.KEY_ROW_ID);
            int subject = cursor.getColumnIndex(TableModel.Classes.KEY_SUBJECT);
            int teacherId = cursor.getColumnIndex(TableModel.Classes.KEY_TEACHER_ID);
            int groupId = cursor.getColumnIndex(TableModel.Classes.KEY_GROUP_ID);
            int roomNumber = cursor.getColumnIndex(TableModel.Classes.KEY_ROOM_NUMBER);
            int lessonNumber = cursor.getColumnIndex(TableModel.Classes.KEY_LESSON_NUMBER);
            int day = cursor.getColumnIndex(TableModel.Classes.KEY_DAY);
            int week = cursor.getColumnIndex(TableModel.Classes.KEY_WEEK);
            int lastUpdate = cursor.getColumnIndex(TableModel.Classes.KEY_LAST_UPDATE);

            contentList = new ArrayList<ObjectModel.Classes>();
            do{

                ObjectModel.Classes classes = new ObjectModel.Classes();

                classes.setId(cursor.getInt(id));
                classes.setSubject(cursor.getString(subject));
                classes.setTeacherId(cursor.getInt(teacherId));
                classes.setGroupId(cursor.getInt(groupId));
                classes.setRoomNumber(cursor.getString(roomNumber));
                classes.setLessonNumber(cursor.getInt(lessonNumber));
                classes.setDay(cursor.getInt(day));
                classes.setWeek(cursor.getInt(week));
                classes.setLastUpdate(cursor.getString(lastUpdate));
                contentList.add(classes);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return contentList;
    }

    public static ArrayList<ObjectModel.Classes> getClasses (Context context, int group, int needDay, int needWeek) {

        ArrayList<ObjectModel.Classes> contentList = null;

        String selection = TableModel.Classes.KEY_GROUP_ID + "=? AND " + TableModel.Classes.KEY_DAY + "=? AND " + TableModel.Classes.KEY_WEEK + "=?";
        String selectionArgs[] = new String[] { String.valueOf(group), String.valueOf(needDay), String.valueOf(needWeek)};

        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI_CLASSES, null, selection, selectionArgs, TableModel.Classes.KEY_LESSON_NUMBER);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(TableModel.Classes.KEY_ROW_ID);
            int subject = cursor.getColumnIndex(TableModel.Classes.KEY_SUBJECT);
            int teacherId = cursor.getColumnIndex(TableModel.Classes.KEY_TEACHER_ID);
            int groupId = cursor.getColumnIndex(TableModel.Classes.KEY_GROUP_ID);
            int roomNumber = cursor.getColumnIndex(TableModel.Classes.KEY_ROOM_NUMBER);
            int lessonNumber = cursor.getColumnIndex(TableModel.Classes.KEY_LESSON_NUMBER);
            int day = cursor.getColumnIndex(TableModel.Classes.KEY_DAY);
            int week = cursor.getColumnIndex(TableModel.Classes.KEY_WEEK);
            int lastUpdate = cursor.getColumnIndex(TableModel.Classes.KEY_LAST_UPDATE);

            contentList = new ArrayList<ObjectModel.Classes>();
            do{

                ObjectModel.Classes classes = new ObjectModel.Classes();

                classes.setId(cursor.getInt(id));
                classes.setSubject(cursor.getString(subject));
                classes.setTeacherId(cursor.getInt(teacherId));
                classes.setGroupId(cursor.getInt(groupId));
                classes.setRoomNumber(cursor.getString(roomNumber));
                classes.setLessonNumber(cursor.getInt(lessonNumber));
                classes.setDay(cursor.getInt(day));
                classes.setWeek(cursor.getInt(week));
                classes.setLastUpdate(cursor.getString(lastUpdate));
                contentList.add(classes);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return contentList;
    }

    public static ArrayList<ObjectModel.Classes> getTeacherClasses (Context context, int teacher, int needDay, int needWeek) {

        ArrayList<ObjectModel.Classes> contentList = null;

        String selection = TableModel.Classes.KEY_TEACHER_ID + "=? AND " + TableModel.Classes.KEY_DAY + "=? AND " + TableModel.Classes.KEY_WEEK + "=?";
        String selectionArgs[] = new String[] { String.valueOf(teacher), String.valueOf(needDay), String.valueOf(needWeek)};

        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI_CLASSES, null, selection, selectionArgs, TableModel.Classes.KEY_LESSON_NUMBER);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(TableModel.Classes.KEY_ROW_ID);
            int subject = cursor.getColumnIndex(TableModel.Classes.KEY_SUBJECT);
            int groupId = cursor.getColumnIndex(TableModel.Classes.KEY_GROUP_ID);
            int roomNumber = cursor.getColumnIndex(TableModel.Classes.KEY_ROOM_NUMBER);
            int lessonNumber = cursor.getColumnIndex(TableModel.Classes.KEY_LESSON_NUMBER);
            int day = cursor.getColumnIndex(TableModel.Classes.KEY_DAY);
            int week = cursor.getColumnIndex(TableModel.Classes.KEY_WEEK);
            int lastUpdate = cursor.getColumnIndex(TableModel.Classes.KEY_LAST_UPDATE);

            contentList = new ArrayList<ObjectModel.Classes>();
            do{

                ObjectModel.Classes classes = new ObjectModel.Classes();

                classes.setId(cursor.getInt(id));
                classes.setSubject(cursor.getString(subject));
                classes.setGroupId(cursor.getInt(groupId));
                classes.setRoomNumber(cursor.getString(roomNumber));
                classes.setLessonNumber(cursor.getInt(lessonNumber));
                classes.setDay(cursor.getInt(day));
                classes.setWeek(cursor.getInt(week));
                classes.setLastUpdate(cursor.getString(lastUpdate));
                contentList.add(classes);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return contentList;
    }

    public static String getTeacherById(Context context, int teacherId) {

        String selection = TableModel.Teachers.KEY_ROW_ID + "=?";
        String selectionArgs[] = new String[] { String.valueOf(teacherId),};
        String teacherName = "";

        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI_TEACHERS, null, selection, selectionArgs, null);

        if(cursor.moveToFirst()) {
            int teacherColumnId = cursor.getColumnIndex(TableModel.Teachers.KEY_TEACHER_NAME);
            teacherName = cursor.getString(teacherColumnId);
        }
        cursor.close();
        return teacherName;
    }


    public static ArrayList<ObjectModel.Updates> getUpdates (Context context, Uri uri, String columnId, String columnLastUpdate ) {

        ArrayList<ObjectModel.Updates> updateList = null;
        String projection[] = new String[]{columnId, columnLastUpdate};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(columnId);
            int lastUpdate = cursor.getColumnIndex(columnLastUpdate);

            updateList = new ArrayList<ObjectModel.Updates>();
            do{

                ObjectModel.Updates updates = new ObjectModel.Updates();

                updates.id = cursor.getInt(id);
                updates.lastUpdate = cursor.getInt(lastUpdate);
                updateList.add(updates);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return updateList;
    }

    public static void insertClasses(Context context, ContentValues contentValues) {
        context.getContentResolver().insert(MyContentProvider.CONTENT_URI_CLASSES, contentValues);
    }

    public static void insertTeachers(Context context, ContentValues contentValues) {
        context.getContentResolver().insert(MyContentProvider.CONTENT_URI_TEACHERS, contentValues);
    }
}
