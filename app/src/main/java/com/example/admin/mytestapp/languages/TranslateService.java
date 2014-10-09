package com.example.admin.mytestapp.languages;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.admin.mytestapp.MainActivity;
import com.example.admin.mytestapp.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TranslateService extends IntentService {

    final String LOG_TAG = "myLogs";
    public static final String ACTION_TRANSLATESERVICE = ".languages.TranslateService.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String EXTRA_KEY_OUT1 = "EXTRA_OUT1";
    public static final String EXTRA_KEY_OUT2 = "EXTRA_OUT2";
    public static final String EXTRA_KEY_OUT3 = "EXTRA_OUT3";
    public static final String TEXT_TRANSLATE = "TEXT_TRANSLATE";

    public TranslateService() {
        super("myService");
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
            String from= intent.getStringExtra(MainActivity.LANGUAGE_FROM);
            String to = intent.getStringExtra(MainActivity.LANGUAGE_TO);
            String text = intent.getStringExtra(MainActivity.TEXT_LANGUAGE);

            Network network=new Network();
            Log.d("JSON", "from="+from);
            Log.d("JSON", "to="+to);
            Log.d("JSON", "text="+text);

            JSONObject json = new JSONObject(network.urlConnection(from, to, text));
            String translateObject = json.getString("text");
            String translateText = translateObject.substring(2,translateObject.length()-2);
            Log.d(LOG_TAG,translateText);
            Intent intentResponse = new Intent();
            intentResponse.setAction(ACTION_TRANSLATESERVICE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra(TEXT_TRANSLATE, translateText);
            sendBroadcast(intentResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "onHandleIntent end ");
    }

}
