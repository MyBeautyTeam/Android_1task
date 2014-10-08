package com.example.admin.mytestapp;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import com.example.admin.mytestapp.fragments.DetailsFragment;
import com.example.admin.mytestapp.fragments.ListsFragment;
import com.example.admin.mytestapp.languages.LanguageHelper;
import com.example.admin.mytestapp.languages.LanguageService;
import com.example.admin.mytestapp.languages.MyIntentService;
import com.example.admin.mytestapp.languages.ParcelMap;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Admin on 29.09.2014.
 */
public class MainActivity extends FragmentActivity
        implements ListsFragment.onItemClickListener,
        OnClickListener,
        AdapterView.OnItemSelectedListener {

    private static final String POSITION = "position";
    public static final String TAG = "myLogs123";
    private int from = 0;
    private int to = 0;
    private String fromText = "";
    private String toText = "";
    private DetailsFragment detailsFragment;
    private static ListsFragment listsFragment;
    private boolean isHorisontal = false;
    public static boolean wasVertical = false;
    public static final String LANGUAGE_FROM = "LANGUAGE_FROM";
    public static final String LANGUAGE_TO = "LANGUAGE_TO";
    public static final String TEXT_LANGUAGE = "TEXT_LANGUAGE";
    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        String[] listOfLang = getIntent().getStringArrayExtra (Splash.MAIN_KEY_OUT);
        ParcelMap mapNameToAlias = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT1);
        ParcelMap mapAliasToAvailable = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT2);
        ParcelMap mapAliasToName = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT3);
        LanguageHelper languageHelper = new LanguageHelper(mapAliasToAvailable, mapNameToAlias,mapAliasToName ,listOfLang);
        //проверка
     /*   languageHelper.getAllLanguages();
        languageHelper.getAvailableLanguage("Русский");

        String  txt = "hand like";
        String fr = "Английский";
        String t = "Русский";


        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        intent.putExtra(LANGUAGE_FROM,languageHelper.getAlias(fr));
        intent.putExtra(LANGUAGE_TO,languageHelper.getAlias(t));
        intent.putExtra(TEXT_LANGUAGE,txt);
        startService(intent);

        receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter(
        LanguageService.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);
    */


        FragmentTransaction fTran;
        Log.d(TAG, String.valueOf(1));
        if (savedInstanceState != null) { // Восстанавливаем предыдущие значение
            from = savedInstanceState.getInt(DetailsFragment.FROM);
            to = savedInstanceState.getInt(DetailsFragment.TO);
            fromText = savedInstanceState.getString(DetailsFragment.FROM_TEXT);
            toText = savedInstanceState.getString(DetailsFragment.TO_TEXT);
        }


        //не буду здесь комментировать каждую строчку, если внимательно всмотреться, то всё вполне понятно
        if (getSupportFragmentManager().findFragmentByTag("detailsFragment") == null) {
            detailsFragment = DetailsFragment.getInstance(from, to, fromText, toText);
        } else {
            detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("detailsFragment");
        }

        if (findViewById(R.id.titlesRight) == null) { // Горизонтально
            isHorisontal = true;
            fTran = getSupportFragmentManager().beginTransaction();
            fTran.replace(R.id.details, detailsFragment, "detailsFragment");
            fTran.commit();
        } else { // Вертикально
            FragmentManager manager = getSupportFragmentManager();
            fTran = manager.beginTransaction();

            fTran.replace(R.id.details, detailsFragment, "detailsFragment");

            if (manager.findFragmentByTag("listsFragment") == null) {
                listsFragment = new ListsFragment();
                fTran.replace(R.id.titlesLeft, listsFragment, "listsFragment");
            } else {
                listsFragment = (ListsFragment) manager.findFragmentByTag("listsFragment");
                fTran.attach(listsFragment);
            }

            ListsFragment listsFragmentRight;
            if (manager.findFragmentByTag("listsFragmentRight") == null) {
                listsFragmentRight = new ListsFragment();
                fTran.replace(R.id.titlesRight, listsFragmentRight, "listsFragmentRight");
            } else {
                listsFragmentRight = (ListsFragment) manager.findFragmentByTag("listsFragmentRight");
                fTran.attach(listsFragmentRight);
            }

            fTran.commit();
        }
    }


    @Override
    public void onArticleSelected(int pos, int id, View view) {

        //DetailsFragment detailsFragment = DetailsFragment.getInstance(this.from, this.to, fromText, toText);
        if (R.id.titlesLeft == id) {
            //detailsFragment = DetailsFragment.getInstance(pos, this.to, fromText, toText);
            detailsFragment.setFrom(pos);
            this.from = pos;
        }
        if (R.id.titlesRight == id) {
            //detailsFragment = DetailsFragment.getInstance(this.from, pos, fromText, toText);
            detailsFragment.setTo(pos);
            this.to = pos;
        }
        Log.d(TAG, ListsFragment.arrayOfCity[pos]);
        //showDetails(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //отцепляем фрагменты чтобы их вновь прицепить в onCreate
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("listsFragment");
        if (fragment != null) {
            FragmentTransaction fTran = manager.beginTransaction();
            fTran.detach(fragment);
            fragment = manager.findFragmentByTag("listsFragmentRight");
            fTran.detach(fragment);
            fTran.commit();
        }
        super.onSaveInstanceState(outState);
        outState.putInt(DetailsFragment.FROM, from);
        outState.putInt(DetailsFragment.TO, to);
        //outState.put
        String text = ((EditText)findViewById(R.id.fromText)).getText().toString();
        String text2 = ((EditText)findViewById(R.id.toText)).getText().toString();
        outState.putString(DetailsFragment.FROM_TEXT, ((EditText)findViewById(R.id.fromText)).getText().toString());
        outState.putString(DetailsFragment.TO_TEXT, ((EditText)findViewById(R.id.toText)).getText().toString());
    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case R.id.excahngeBtn:
                /*Spinner spinner_from = (Spinner) findViewById(R.id.spiner_from);
                int from = spinner_from.getSelectedItemPosition();

                Spinner spinner_to = (Spinner) findViewById(R.id.spiner_to);
                int to = spinner_to.getSelectedItemPosition();

                spinner_to.setSelection(from);
                spinner_from.setSelection(to);
                Iterator <Fragment> iterator = getSupportFragmentManager().getFragments().iterator();
                while(iterator.hasNext()) {
                    Fragment frag = iterator.next();
                    if (frag.getId() == R.id.titlesLeft ||
                            frag.getId() == R.id.titlesRight) {
                        ((ListsFragment) frag).clearItemColor();
                    }
                }

                this.from = to;
                this.to = from;
                break;*/
                detailsFragment.exchange();
                int buf = to;
                to = from;
                from = buf;
                List <Fragment> list = getSupportFragmentManager().getFragments();
                Iterator<Fragment> it = list.iterator();
                Fragment fragment;
                while (it.hasNext()) {
                    fragment = it.next();
                    if (fragment != null&& // Похоже на кастостыль. Один из фрагментов мо
                            (
                                (fragment.getId() == R.id.titlesLeft)||
                                (fragment.getId() == R.id.titlesRight))
                            )
                        ((ListsFragment)fragment).clearItemColor();
                        //((ListsFragment)fragment).clearItemColor();
                }
                break;

            case R.id.OkBtn:
                // TODO ASINC_TASK, ЗАПРОС
                break;

            case R.id.menuBtn:
                // переиспользуем фрагмент, не создаём новый
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fTran = manager.beginTransaction();
                if (manager.findFragmentByTag("") == null) {
                    listsFragment = new ListsFragment();
                } else {
                    listsFragment = (ListsFragment) manager.findFragmentByTag("listsFragment");
                }
                fTran.add(R.id.details, listsFragment);
                fTran.addToBackStack(null);
                fTran.commit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY!");
        unregisterReceiver(receiver);
        /*FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        */

        /*
        List<Fragment> list = getSupportFragmentManager().getFragments();
        Iterator<Fragment> it = list.iterator();
        FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        while (it.hasNext()) {
            fragment = it.next();
            fTran.remove(fragment);
        }
        fTran.addToBackStack(null);
        fTran.commit();*/
    }

    /* !!!!!!!!!!!!!!!!! SPINNER SELECTOR*/

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        switch (parentView.getId()) {
            case R.id.spiner_from:
                this.from = position;
                break;
            case R.id.spiner_to:
                this.to = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String translateText = intent.getStringExtra (MyIntentService.TEXT_TRANSLATE);
            Log.d("translate", translateText);
        }
    }
}
