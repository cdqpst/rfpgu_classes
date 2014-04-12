package ru.rfpgu.classes.controller;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.rfpgu.classes.MyActivity;
import ru.rfpgu.classes.model.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
public class NetworkController {

    private static String SERVER_URL = "http://rfpgu.bl.ee/admin/api/";

    private static String TAG_ID = "id";
    private static String TAG_SUBJECT = "subject";
    private static String TAG_TEACHER_ID = "teacher_id";
    private static String TAG_TEACHER_NAME = "teacher_name";
    private static String TAG_ROOM_NUMBER = "room_number";
    private static String TAG_LESSON_NUMBER = "lesson_number";
    private static String TAG_GROUP_ID = "group_id";
    private static String TAG_DAY = "day_of_week";
    private static String TAG_WEEK= "odd_week";
    private static String TAG_LAST_UPDATE = "last_update";

    private static String TAG_CLASSES = "classes";
    private static String TAG_TEACHERS = "teachers";
    private static String TAG_IDS = "ids";
    private static String TAG_UPDATES = "last_updates";


    public static int addClassesFromServer(Context context){

        int maxId = DatabaseController.getMaxId(context, MyContentProvider.CONTENT_URI_CLASSES, TableModel.Classes.KEY_ROW_ID);
        Log.d(MyActivity.LOG_TAG, "MAX=" + maxId);

        JSONObject jsonObject = JsonReceiver.getJsonObj(SERVER_URL + "get_classes.php?idh=" + maxId);
        JSONArray info = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json");
            return -1;
        }

        try {
            // Getting Array of Contacts
            info = jsonObject.getJSONArray(TAG_CLASSES);

            // looping through All Contacts
            for(int i = 0; i < info.length(); i++){
                JSONObject c = info.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String subject = c.getString(TAG_SUBJECT);
                String teacherId = c.getString(TAG_TEACHER_ID);
                String groupId = c.getString(TAG_GROUP_ID);
                String roomNumber = c.getString(TAG_ROOM_NUMBER);
                String lessonNumber = c.getString(TAG_LESSON_NUMBER);
                String day = c.getString(TAG_DAY);
                String week = c.getString(TAG_WEEK);
                String lastUpdate = c.getString(TAG_LAST_UPDATE);

                Log.d(MyActivity.LOG_TAG, "id = " + id + " subject = " + subject + " teacher= " + teacherId + " last update = " + lastUpdate + "\n\r");

                ContentValues contentValues = new ContentValues();

                contentValues.put(TableModel.Classes.KEY_ROW_ID, id);
                contentValues.put(TableModel.Classes.KEY_SUBJECT, subject);
                contentValues.put(TableModel.Classes.KEY_TEACHER_ID, teacherId);
                contentValues.put(TableModel.Classes.KEY_GROUP_ID, groupId);
                contentValues.put(TableModel.Classes.KEY_ROOM_NUMBER, roomNumber);
                contentValues.put(TableModel.Classes.KEY_LESSON_NUMBER, lessonNumber);
                contentValues.put(TableModel.Classes.KEY_DAY, day);
                contentValues.put(TableModel.Classes.KEY_WEEK, week);
                contentValues.put(TableModel.Classes.KEY_LAST_UPDATE, lastUpdate);

                DatabaseController.insertClasses(context, contentValues);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 200;
    }

    public static ContentValues getClassesFromServer(int selection){

        ContentValues contentValues = null;

        JSONObject jsonObject = JsonReceiver.getJsonObj(SERVER_URL + "get_classes.php?id=" + selection);
        JSONArray info = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json");
            return contentValues;
        }

        try {
            // Getting Array of Contacts
            info = jsonObject.getJSONArray(TAG_CLASSES);

            // looping through All Contacts
            for(int i = 0; i < info.length(); i++){
                JSONObject c = info.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String subject = c.getString(TAG_SUBJECT);
                String teacherId = c.getString(TAG_TEACHER_ID);
                String groupId = c.getString(TAG_GROUP_ID);
                String roomNumber = c.getString(TAG_ROOM_NUMBER);
                String lessonNumber = c.getString(TAG_LESSON_NUMBER);
                String day = c.getString(TAG_DAY);
                String week = c.getString(TAG_WEEK);
                String lastUpdate = c.getString(TAG_LAST_UPDATE);

                Log.d(MyActivity.LOG_TAG, "id = " + id + " subject = " + subject + " teacher= " + teacherId + " last update = " + lastUpdate + "\n\r");

                contentValues = new ContentValues();

                contentValues.put(TableModel.Classes.KEY_SUBJECT, subject);
                contentValues.put(TableModel.Classes.KEY_TEACHER_ID, teacherId);
                contentValues.put(TableModel.Classes.KEY_GROUP_ID, groupId);
                contentValues.put(TableModel.Classes.KEY_ROOM_NUMBER, roomNumber);
                contentValues.put(TableModel.Classes.KEY_LESSON_NUMBER, lessonNumber);
                contentValues.put(TableModel.Classes.KEY_DAY, day);
                contentValues.put(TableModel.Classes.KEY_WEEK, week);
                contentValues.put(TableModel.Classes.KEY_LAST_UPDATE, lastUpdate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;
    }

    public static int addTeachersFromServer(Context context){

        int maxId = DatabaseController.getMaxId(context, MyContentProvider.CONTENT_URI_TEACHERS, TableModel.Teachers.KEY_ROW_ID);
        Log.d(MyActivity.LOG_TAG, "MAX Teacher=" + maxId);

        JSONObject jsonObject = JsonReceiver.getJsonObj(SERVER_URL + "get_teachers.php?idh=" + maxId);
        JSONArray info = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json");
            return -1;
        }

        try {
            // Getting Array of Contacts
            info = jsonObject.getJSONArray(TAG_TEACHERS);

            // looping through All Contacts
            for(int i = 0; i < info.length(); i++){
                JSONObject c = info.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_TEACHER_NAME);
                String lastUpdate = c.getString(TAG_LAST_UPDATE);

                Log.d(MyActivity.LOG_TAG, "id = " + id + " teacher = " + name + " last update = " + lastUpdate + "\n\r");

                ContentValues contentValues = new ContentValues();

                contentValues.put(TableModel.Teachers.KEY_ROW_ID, id);
                contentValues.put(TableModel.Teachers.KEY_TEACHER_NAME, name);
                contentValues.put(TableModel.Teachers.KEY_LAST_UPDATE, lastUpdate);

                DatabaseController.insertTeachers(context, contentValues);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 200;
    }

    public static ContentValues getTeacherFromServer(int selection){

        ContentValues contentValues = null;

        JSONObject jsonObject = JsonReceiver.getJsonObj(SERVER_URL + "get_teachers.php?id=" + selection);
        JSONArray info = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json");
            return contentValues;
        }

        try {
            // Getting Array of Contacts
            info = jsonObject.getJSONArray(TAG_TEACHERS);

            // looping through All Contacts
            for(int i = 0; i < info.length(); i++){
                JSONObject c = info.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String teacherName = c.getString(TAG_TEACHER_NAME);
                String lastUpdate = c.getString(TAG_LAST_UPDATE);

                Log.d(MyActivity.LOG_TAG, "id = " + id + " teacher= " + teacherName + " last update = " + lastUpdate + "\n\r");

                contentValues = new ContentValues();

                contentValues.put(TableModel.Teachers.KEY_TEACHER_NAME, teacherName);
                contentValues.put(TableModel.Teachers.KEY_LAST_UPDATE, lastUpdate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;
    }

    public static ArrayList<Integer> getServerIds(String url, String tagName){

        JSONObject jsonObject = JsonReceiver.getJsonObj(url);
        JSONArray info = null;
        ArrayList<Integer> idList = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json (ids)");
            return idList;
        }

        try {
            // Getting Array of Contacts
            info = jsonObject.getJSONArray(tagName);
            idList = new ArrayList<Integer>();
            // looping through All Contacts
            for(int i = 0; i < info.length(); i++){
                JSONObject c = info.getJSONObject(i);
                int id = Integer.valueOf(c.getString(TAG_ID));
                idList.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return idList;
    }

    public static int getLastUpdate(String url){

        JSONObject jsonObject = JsonReceiver.getJsonObj(url);
        int lastUpdate = 0;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json (last update)");
            return lastUpdate;
        }

        try {

            lastUpdate = jsonObject.getInt(TAG_LAST_UPDATE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lastUpdate;
    }


    public static ArrayList<ObjectModel.Updates> getServerUpdates(String url, String tagName){

        JSONObject jsonObject = JsonReceiver.getJsonObj(url);
        JSONArray info = null;
        ArrayList<ObjectModel.Updates> updList = null;

        if(jsonObject == null) {
            Log.d(MyActivity.LOG_TAG, "Путой json (ids)");
            return updList;
        }

        try {

            info = jsonObject.getJSONArray(tagName);
            updList = new ArrayList<ObjectModel.Updates>();

            for(int i = 0; i < info.length(); i++){

                ObjectModel.Updates updates = new ObjectModel.Updates();

                JSONObject c = info.getJSONObject(i);
                int id = Integer.valueOf(c.getString(TAG_ID));
                int lastUpdate = Integer.valueOf(c.getString(TAG_LAST_UPDATE));
                updates.id = id;
                updates.lastUpdate = lastUpdate;
                updList.add(updates);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return updList;
    }

    public static ArrayList<Integer> calcDiffForDel(ArrayList<Integer> clientIds, ArrayList<Integer> serverIds) {

        ArrayList<Integer> result = new ArrayList<Integer>();

        if(clientIds != null) {

            int clientValue;
            int serverValue;

            for(int i = 0; i < clientIds.size(); i++) {

                int flag = 0;
                clientValue = clientIds.get(i);

                for(int j = 0; j < serverIds.size(); j++) {
                    serverValue = serverIds.get(j);
                    if(clientValue == serverValue) flag = 1;
//                        if(clientIds.get(i) == serverIds.get(j)) flag = 1;
                }
                if(flag == 0)   result.add(clientIds.get(i));
            }
        }

        return result;
    }

    public static ArrayList<Integer> calcDiffForUpd(ArrayList<ObjectModel.Updates> clientIds, ArrayList<ObjectModel.Updates> serverIds) {

        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i = 0; i < clientIds.size(); i++) {

            int flag = 0;

            for(int j = 0; j < serverIds.size(); j++) {
                if( (clientIds.get(i).id == serverIds.get(j).id) && (clientIds.get(i).lastUpdate == serverIds.get(j).lastUpdate)) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0)   result.add(clientIds.get(i).id);
        }

        return result;
    }

    public static void delRemovedClasses(Context context) {

        ArrayList<Integer> serverIds = getServerIds(SERVER_URL + "get_ids.php", TAG_IDS);

        if(serverIds != null) {
            Log.d(MyActivity.LOG_TAG,"SERVER = " + serverIds);
            Log.d(MyActivity.LOG_TAG,"\n Client = " +DatabaseController.getIds(context, MyContentProvider.CONTENT_URI_CLASSES));
            Log.d(MyActivity.LOG_TAG,"\nFor del = " + calcDiffForDel(DatabaseController.getIds(context, MyContentProvider.CONTENT_URI_CLASSES), serverIds));
            DatabaseController.deleteRows(context, MyContentProvider.CONTENT_URI_CLASSES, calcDiffForDel(DatabaseController.getIds(context, MyContentProvider.CONTENT_URI_CLASSES), serverIds));
        } else Log.d(MyActivity.LOG_TAG, "нет сети");

    }

    public static void delRemovedTeachers(Context context) {

        ArrayList<Integer> serverIds = getServerIds(SERVER_URL + "get_ids.php?table=2", TAG_IDS);

        if(serverIds != null) {
            DatabaseController.deleteRows(context, MyContentProvider.CONTENT_URI_TEACHERS, calcDiffForDel(DatabaseController.getIds(context, MyContentProvider.CONTENT_URI_TEACHERS), serverIds));
        } else Log.d(MyActivity.LOG_TAG, "нет сети");

    }

    public static void updateClasses(Context context) {

        int serverLastUpdate = getLastUpdate(SERVER_URL + "get_last_upd.php?table=1");

        if(serverLastUpdate == 0) {
            Log.d(MyActivity.LOG_TAG,"осутствует соединение с сетью");
            return;
        }

        int clientLastUpdate = DatabaseController.getLastUpdate(context, MyContentProvider.CONTENT_URI_CLASSES);

        if((clientLastUpdate != 0 && serverLastUpdate != 0) && (clientLastUpdate < serverLastUpdate) ) {

            ArrayList<ObjectModel.Updates> serverUpdList = getServerUpdates(SERVER_URL + "get_upd.php?table=1",TAG_UPDATES);
            ArrayList<ObjectModel.Updates> clientUpdList = null;

            if(serverUpdList != null) {
                clientUpdList = DatabaseController.getUpdates(context, MyContentProvider.CONTENT_URI_CLASSES, TableModel.Classes.KEY_ROW_ID, TableModel.Classes.KEY_LAST_UPDATE);
            }

            if(serverUpdList != null && clientUpdList != null) {

                ArrayList<Integer> updList = calcDiffForUpd(clientUpdList , serverUpdList);

                if(updList.size() > 0) {

                    for(int i = 0; i < updList.size(); i++) {
                        context.getContentResolver().update(MyContentProvider.CONTENT_URI_CLASSES, getClassesFromServer(updList.get(i)), TableModel.Classes.KEY_ROW_ID + "=" + updList.get(i), null);
                    }

                }

            }
        } else Log.d(MyActivity.LOG_TAG,"обновление не требуется");
    }

    public static void updateTeachers (Context context) {

        int serverLastUpdate = getLastUpdate(SERVER_URL + "get_last_upd.php?table=2");

        if(serverLastUpdate == 0) {
            Log.d(MyActivity.LOG_TAG,"осутствует соединение с сетью");
            return;
        }

        int clientLastUpdate = DatabaseController.getLastUpdate(context, MyContentProvider.CONTENT_URI_TEACHERS);

        if((clientLastUpdate != 0 && serverLastUpdate != 0) && (clientLastUpdate < serverLastUpdate) ) {

            ArrayList<ObjectModel.Updates> serverUpdList = getServerUpdates(SERVER_URL + "get_upd.php?table=2",TAG_UPDATES);
            ArrayList<ObjectModel.Updates> clientUpdList = null;

            if(serverUpdList != null) {
                clientUpdList = DatabaseController.getUpdates(context, MyContentProvider.CONTENT_URI_TEACHERS, TableModel.Teachers.KEY_ROW_ID, TableModel.Teachers.KEY_LAST_UPDATE);
            }

            if(serverUpdList != null && clientUpdList != null) {

                ArrayList<Integer> updList = calcDiffForUpd(clientUpdList , serverUpdList);

                if(updList.size() > 0) {

                    for(int i = 0; i < updList.size(); i++) {
                        ContentValues teacher = getTeacherFromServer(updList.get(i));
                        if(teacher != null) {
                            context.getContentResolver().update(MyContentProvider.CONTENT_URI_TEACHERS, teacher, TableModel.Teachers.KEY_ROW_ID + "=" + updList.get(i), null);
                        } else Log.d(MyActivity.LOG_TAG, "Ошибка получения данных Teacher = null");
                    }
                }

            }
        } else Log.d(MyActivity.LOG_TAG,"обновление списка преподавателей не требуется");
    }

}
