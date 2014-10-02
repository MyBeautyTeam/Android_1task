package com.example.admin.mytestapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.admin.mytestapp.fragments.DetailsFragment;
import com.example.admin.mytestapp.fragments.ListsFragment;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentTransaction fTran;
        Log.d(TAG, String.valueOf(1));
        if (savedInstanceState != null) { // Восстанавливаем предыдущие значение
            from = savedInstanceState.getInt(DetailsFragment.FROM);
            to = savedInstanceState.getInt(DetailsFragment.TO);
            fromText = savedInstanceState.getString(DetailsFragment.FROM_TEXT);
            toText = savedInstanceState.getString(DetailsFragment.TO_TEXT);
        }

        detailsFragment = DetailsFragment.getInstance(from, to, fromText, toText);

        if (findViewById(R.id.titlesRight) != null) { // Горизонтально
            isHorisontal = true;
            fTran = getSupportFragmentManager().beginTransaction();
            fTran.replace(R.id.details, detailsFragment);
            fTran.commit();
        } else { // Вертикально

            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                /*
                УЗНАТЬ КАК УДАЛЯТЬ!!!!!!!!!!!!!!!!!!!!!!!!!
                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                НЕ ВЫХОДИТ!!!!!!!!!!!!!!!!


                 */
                fTran = getSupportFragmentManager().beginTransaction();
                fTran.remove(getSupportFragmentManager().getFragments().get(1));
                fTran.addToBackStack(null); //????
                fTran.commit();
            }

            int count2 = getSupportFragmentManager().getBackStackEntryCount();
            fTran = getSupportFragmentManager().beginTransaction();
            fTran.replace(R.id.details, detailsFragment);
            fTran.commit();
            int count3 = getSupportFragmentManager().getBackStackEntryCount();
            int qwerty = 999;
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

        if (isHorisontal) {
            /*
            Если горизонтальная ориентация - заменяем, то, что есть в Детаилс
             */
            FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
            fTran.replace(R.id.details, detailsFragment);
            fTran.commit();

        } else {
            /*
            Если вертикально добавляем поверх листов свою
             */
            FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
            fTran.add(R.id.details, detailsFragment);
            fTran.addToBackStack(null);
            fTran.commit();
        }
        //showDetails(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
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
                FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
                listsFragment = new ListsFragment();
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

}
/*
КАК УБРАТЬ ФРАГМЕНТ БЕЗ ОШИБОК!?
КАК ПЕРЕДАВАТЬ ОДНОВРЕМЕННО putInt И putString
Передача объекта между активити. Хочу заполнить статик!
 */