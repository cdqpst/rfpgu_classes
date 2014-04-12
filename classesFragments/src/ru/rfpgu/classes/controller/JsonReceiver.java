package ru.rfpgu.classes.controller;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import ru.rfpgu.classes.MyActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class JsonReceiver {

    public static JSONObject getJsonObj(String url){

        JSONObject jsonObject =  null;

        DefaultHttpClient defaultClient = new DefaultHttpClient();
        HttpGet httpGetRequest = new HttpGet(url);

        try{
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();

//            Log.d(MyActivity.LOG_TAG, "JsonReceiver: " + json + "\n\r");
            jsonObject = new JSONObject(json);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(MyActivity.LOG_TAG, "Ошибка 1" + e.toString());

        } catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
