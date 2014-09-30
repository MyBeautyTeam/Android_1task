package com.example.admin.mytestapp;

//import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.mytestapp.fragments.DetailsFragment;
import com.example.admin.mytestapp.fragments.ListsFragment;

import java.util.List;

/**
 * Created by Admin on 29.09.2014.
 */
public class MainActivity extends FragmentActivity implements ListsFragment.onItemClickListener {

    int position = 0;
    boolean withDetails = true;
    ListsFragment listsFragment;
    DetailsFragment detailsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        listsFragment = new ListsFragment();

            FragmentTransaction fTran1 = getSupportFragmentManager().beginTransaction();
            fTran1.add(R.id.cont, listsFragment);
            fTran1.addToBackStack(null);
            fTran1.commit();

            detailsFragment = DetailsFragment.getInstance(0);
            FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
            fTran.add(R.id.cont, detailsFragment);
            fTran.addToBackStack(null);
            fTran.commit();

            //Toast.makeText(this, "ЗДЕСЬ", Toast.LENGTH_LONG);
          //  System
                    //.add(R.id.cont, listsFragment).commit();
        //}
        /*position = savedInstanceState.getInt(DetailsFragment.LANG_NO);
        withDetails = (findViewById(R.id.cont) != null);
        if (withDetails)
            showDetails(position);*/
    }

    void showDetails(int pos) {
        if (withDetails) {
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.cont);
            if (detailsFragment == null || detailsFragment.getPosition() != pos) {
                detailsFragment = DetailsFragment.getInstance(pos);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.cont, detailsFragment).commit();
            }
        } else {
            startActivity(new Intent(this, DetailsActivity.class).putExtra("position", position));
        }
    }

    @Override
    public void onArticleSelected(int position) {
        this.position = position;
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cont);
        if (detailsFragment == null || detailsFragment.getPosition() != position) {
            detailsFragment = DetailsFragment.getInstance(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cont, detailsFragment).commit();
        }
        //showDetails(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}
