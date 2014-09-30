package com.example.admin.mytestapp;

/**
 * Created by Admin on 30.09.2014.
 */
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.admin.mytestapp.fragments.DetailsFragment;

public class DetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Если горизонтальная ориентация - закрываем активити, переходим к меинАктивити
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish(); // Отдать данные на перерисовку в main activity
            return;
        }

        if (savedInstanceState == null) { // Создается впервые?
            /*
            Почему создаем фрагмент только при создании Activity и при пересоздании - нет?
            Потому что система сама умеет пересоздавать существующие фрагменты при поворотах экрана,
            сохраняя при этом аргументы фрагмента.
            И нам совершенно незачем в данном случае пересоздавать фрагмент самим.
             */
            DetailsFragment details = DetailsFragment.getInstance(getIntent().getIntExtra("position", 0));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}
