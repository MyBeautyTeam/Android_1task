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
    public static final String ACTION_LANGUAGESERVICE = ".languages.LanguageService.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String EXTRA_KEY_OUT1 = "EXTRA_OUT1";
    public static final String EXTRA_KEY_OUT2 = "EXTRA_OUT2";
    public static final String EXTRA_KEY_OUT3 = "EXTRA_OUT3";

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
            Map<String, String> mapAliasToAvailable = new HashMap<String, String>();
            Map<String, String> mapNameToAlias = new HashMap<String, String>();
            Map<String, String> mapAliasToName = new HashMap<String, String>();
            Network network=new Network();

            JSONObject json = new JSONObject(network.urlConnection());
            JSONObject tagName = json.getJSONObject("langs");

            String[] listName = network.getNameList(tagName);
            mapAliasToName = network.getMapAliasToName(tagName);
            mapNameToAlias = network.getNameTag(tagName,listName);
            mapAliasToAvailable = network.getMap(json);

        // возвращаем результат
            ParcelMap parcelMapAliasToName = new ParcelMap();
            parcelMapAliasToName.putAll(mapAliasToName);

            ParcelMap parcelMapToAlias = new ParcelMap();
            parcelMapToAlias.putAll(mapNameToAlias);

            ParcelMap parcelMapAliasToAvailable = new ParcelMap();
            parcelMapAliasToAvailable.putAll(mapAliasToAvailable);

            Intent intentResponse = new Intent();
            intentResponse.setAction(ACTION_LANGUAGESERVICE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra(EXTRA_KEY_OUT, listName);
            intentResponse.putExtra(EXTRA_KEY_OUT1, parcelMapToAlias);
            intentResponse.putExtra(EXTRA_KEY_OUT2, parcelMapAliasToAvailable);
            intentResponse.putExtra(EXTRA_KEY_OUT3, parcelMapAliasToName);
            sendBroadcast(intentResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "onHandleIntent end ");
    }
}