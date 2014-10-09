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
import android.widget.Spinner;

import com.example.admin.mytestapp.fragments.DetailsFragment;
import com.example.admin.mytestapp.fragments.ListsFragment;
import com.example.admin.mytestapp.languages.LanguageHelper;
import com.example.admin.mytestapp.languages.TranslateService;
import com.example.admin.mytestapp.languages.ParcelMap;

/**
 * Created by Admin on 29.09.2014.
 */
public class MainActivity extends FragmentActivity
        implements ListsFragment.onItemClickListener,
        OnClickListener,
        AdapterView.OnItemSelectedListener {

    public static final String TAG = "myLogs123";

    private String from = "Английский";
    private String to = "Русский";
    private String fromText = "";
    private String toText = "";

    private DetailsFragment detailsFragment;
    private ListsFragment listsFragment;
    public static boolean wasVertical = false;

    public static final String LANGUAGE_FROM = "LANGUAGE_FROM";
    public static final String LANGUAGE_TO = "LANGUAGE_TO";
    public static final String TEXT_LANGUAGE = "TEXT_LANGUAGE";

    private LanguageHelper languageHelper;
    BroadcastReceiver translateReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        String[] listOfLang = getIntent().getStringArrayExtra (Splash.MAIN_KEY_OUT);
        ParcelMap mapNameToAlias = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT1);
        ParcelMap mapAliasToAvailable = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT2);
        ParcelMap mapAliasToName = getIntent().getParcelableExtra(Splash.MAIN_KEY_OUT3);
        languageHelper = new LanguageHelper(mapAliasToAvailable, mapNameToAlias,mapAliasToName ,listOfLang);


        /*
        Регистрируем ресивер
         */

        //проверка
        //languageHelper.getAllLanguages();
        //languageHelper.getAvailableLanguage("Русский");

        FragmentTransaction fTran;
        Log.d(TAG, String.valueOf(1));
        languageHelper = new LanguageHelper(mapAliasToAvailable, mapNameToAlias,mapAliasToName ,listOfLang);
        if (savedInstanceState != null) { // Восстанавливаем предыдущие значение
            from = savedInstanceState.getString(DetailsFragment.FROM);
            to = savedInstanceState.getString(DetailsFragment.TO);
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
            //isHorisontal = true;
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
    public void onResume() {
        translateReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter(
                TranslateService.ACTION_TRANSLATESERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(translateReceiver, intentFilter);

        super.onResume();
    }

    public LanguageHelper getLanguageHelper() {
        return this.languageHelper;
    }

    @Override//ДЛЯ LIST
    public void onArticleSelected(int pos, int id, View view) {

        if (R.id.titlesLeft == id) {
            String from = (languageHelper.getAllLanguages())[pos];
            detailsFragment.setFrom(from);
            listsFragment.makeActualList(from);
        }
        if (R.id.titlesRight == id) {
            String from = ((Spinner)findViewById(R.id.spiner_from)).getSelectedItem().toString();
            String to = (languageHelper.getAvailableLanguage(from))[pos];
            detailsFragment.setTo(to);
        }
        Log.d(TAG, (languageHelper.getAllLanguages())[pos]);
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
        outState.putString(DetailsFragment.FROM, ((Spinner)findViewById(R.id.spiner_from)).getSelectedItem().toString());
        outState.putString(DetailsFragment.TO, ((Spinner)findViewById(R.id.spiner_to)).getSelectedItem().toString());
        outState.putString(DetailsFragment.FROM_TEXT, ((EditText)findViewById(R.id.fromText)).getText().toString());
        outState.putString(DetailsFragment.TO_TEXT, ((EditText)findViewById(R.id.toText)).getText().toString());
    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case R.id.excahngeBtn:

                detailsFragment.exchange();

                /*int buf = to;
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

                }*/
                break;

            case R.id.OkBtn:

                Spinner spinner_from = (Spinner) findViewById(R.id.spiner_from);
                Spinner spinner_to = (Spinner) findViewById(R.id.spiner_to);
                Intent intent = new Intent(MainActivity.this, TranslateService.class);
                intent.putExtra(LANGUAGE_FROM, languageHelper.getAlias(spinner_from.getSelectedItem().toString()));
                intent.putExtra(LANGUAGE_TO, languageHelper.getAlias(spinner_to.getSelectedItem().toString()));
                EditText fromEditText = (EditText)findViewById(R.id.fromText);
                Log.d("JSON", "From TEXT = "+fromEditText.getText().toString());
                intent.putExtra(TEXT_LANGUAGE, fromEditText.getText().toString());
                startService(intent);

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
        unregisterReceiver(translateReceiver);
        Log.d(TAG, "DESTROY!");

    }


    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        switch (parentView.getId()) {
            case R.id.spiner_from:
                String from = parentView.getItemAtPosition(position).toString();
                detailsFragment.saveTo(from);
                //this.from = position; ///??
                //String languageFrom = (languageHelper.getAllLanguages())[position];
                /*
                ТАНЯ ДОЛЖНА ВОЗВРАЩАТЬ ["no language"], если нет языка
                 */

                //detailsFragment.setAvailableSpinnerTo(languageHelper.getAvailableLanguage(languageFrom));
                break;
            case R.id.spiner_to:
                //this.to = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String translateText = intent.getStringExtra (TranslateService.TEXT_TRANSLATE);
            if (translateText != null) {
                ((EditText) findViewById(R.id.toText)).setText(translateText); // ЗАКЭШИРОВАТЬ!
            }
        }
    }
}
