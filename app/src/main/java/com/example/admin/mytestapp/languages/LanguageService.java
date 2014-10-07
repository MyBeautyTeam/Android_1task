package com.example.admin.mytestapp.languages;

import android.app.IntentService;
import android.content.Intent;

import android.os.IBinder;
import android.util.Log;

import com.example.admin.mytestapp.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanguageService extends IntentService {

    final String LOG_TAG = "myLogs";
    public static final String ACTION_MYINTENTSERVICE = ".languages.LanguageService.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";

    public LanguageService() {
        super("myname");
        // TODO Auto-generated constructor stub
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        try {
            Map<String, String> mapAliasToAvaliable = new HashMap<String, String>();
            Map<String, String> mapNameToAlias = new HashMap<String, String>();
            Network network=new Network();


            JSONObject json = new JSONObject(network.urlConnection());
            JSONObject tagName = json.getJSONObject("langs");

            String[] listName = network.getNameList(tagName);
            mapAliasToAvaliable = network.getMap(json);
            mapNameToAlias = network.getNameTag(tagName,listName);
        // возвращаем результат
            ParcelMap p = new ParcelMap();
            System.out.println(mapNameToAlias);
            p.putAll(mapNameToAlias);
            System.out.println(p);
            Intent intentResponse = new Intent();
            intentResponse.setAction(ACTION_MYINTENTSERVICE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra(EXTRA_KEY_OUT, listName);
            intent.putExtra("MapAlias", p);
            // intentResponse.put(EXTRA_KEY_OUT2, mapAl)
            sendBroadcast(intentResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "onHandleIntent end ");
    }
}