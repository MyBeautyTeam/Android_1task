package com.example.admin.mytestapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.mytestapp.languages.LanguageService;
import com.example.admin.mytestapp.languages.ParcelMap;

/**
 * Created by Admin on 25.09.2014.
 */
public class Splash extends Activity {

    public static final String ACTION_MAININTENT = ".languages.LanguageService.RESPONSE";
    public static final String MAIN_KEY_OUT = "MAIN_OUT";
    public static final String MAIN_KEY_OUT1 = "MAIN_OUT1";
    public static final String MAIN_KEY_OUT2 = "MAIN_OUT2";
    public static final String MAIN_KEY_OUT3 = "MAIN_OUT3";
    public final static String PARAM_RESULT = "result";
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final String LOG = "MyLog";
    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainActivity.wasVertical = false;
        setContentView(R.layout.splash);

        Intent intent = new Intent(Splash.this, LanguageService.class);
        startService(intent);

        receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter(
                LanguageService.ACTION_LANGUAGESERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String[] listOfLang = intent.getStringArrayExtra (LanguageService.EXTRA_KEY_OUT);
            ParcelMap mapNameToAlias = intent.getParcelableExtra(LanguageService.EXTRA_KEY_OUT1);
            ParcelMap mapAliasToAvailable = intent.getParcelableExtra(LanguageService.EXTRA_KEY_OUT2);
            ParcelMap mapAliasToName = intent.getParcelableExtra(LanguageService.EXTRA_KEY_OUT3);

            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
            mainIntent.setAction(ACTION_MAININTENT);
            mainIntent.addCategory(Intent.CATEGORY_DEFAULT);
            mainIntent.putExtra(MAIN_KEY_OUT, listOfLang);
            mainIntent.putExtra(MAIN_KEY_OUT2, mapAliasToAvailable);
            mainIntent.putExtra(MAIN_KEY_OUT1, mapNameToAlias);
            mainIntent.putExtra(MAIN_KEY_OUT3, mapAliasToName);
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();
        }
    }
}


