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

    public final static String PARAM_RESULT = "result";
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final String LOG = "MyLog";
    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.wasVertical = false;
        setContentView(R.layout.splash);

        Intent intent = new Intent(Splash.this, LanguageService.class);
        startService(intent);

        receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter(
                LanguageService.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(receiver);
        super.onPause();
    }

    public class Receiver extends BroadcastReceiver {

        //public final static String PARAM_RESULT = "result";
        public static final String ACTION = "language.received";

        @Override
        public void onReceive(Context context, Intent intent) {
            ParcelMap mapAliasToAvaliable = new ParcelMap();
            String[] list = intent.getStringArrayExtra (LanguageService.EXTRA_KEY_OUT);
            mapAliasToAvaliable = intent.getParcelableExtra("MapAlias");
            System.out.println(mapAliasToAvaliable);
            Log.d(LOG, list[2]);
            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();
        }
    }
}


