package com.example.admin.mytestapp.fragments;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.admin.mytestapp.R;

/**
 * Created by Admin on 29.09.2014.
 */

public class ListsFragment extends android.support.v4.app.ListFragment {

    public interface onItemClickListener {
        public void onArticleSelected(int number);
    }

    private onItemClickListener mCallback;

    public static String[] arrayOfCity = {"Москва", "Санкт-Петербург",
            "Казань", "Воронеж", "Челябинск"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        Возвращаем вьюху, созданную из XMLки
         */
        return inflater.inflate(R.layout.fragment_list, null);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /*
        Сразу после onCreateView вызовется этот метод.
        В нем мы используем адаптер для формирования списка.
        И навешиваем обработчик (в данном случае в его роли выступит MainActivity.
         */

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new NewsListAdapter(arrayOfCity));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mCallback.onArticleSelected(position);
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        /*
        Вызовется в момент прикрепления фрагмента к активити.
        Запомним ссылку на активити в поле mCallback, чтобы потом использовать ее в обработчике.
         */
        super.onAttach(activity);
        try {
            mCallback = (onItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    private class NewsListAdapter extends ArrayAdapter<String> { // Свой Адаптер

        public NewsListAdapter(String[] objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            ViewHolder - класс, хрянящий TextView. Это нужно, чтобы всякий раз не создавать
            не проводить поиск findViewById(R.id.text), а брать готовую вьюху и менять в ней поля.
            convertView будет всегда "носить себя же" в теге.
             */
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.text.setText(getItem(position));
            return convertView;
        }

        private class ViewHolder {

            public TextView text;
        }
    }
}
