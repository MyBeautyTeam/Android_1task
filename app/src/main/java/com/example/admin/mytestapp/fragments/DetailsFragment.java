package com.example.admin.mytestapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import com.example.admin.mytestapp.MainActivity;
import com.example.admin.mytestapp.R;
import com.example.admin.mytestapp.languages.LanguageHelper;

/**
 * Created by Admin on 29.09.2014.
 */

public class DetailsFragment extends Fragment {

    //private static Bundle bundle;
    private Activity mCallback;
    public final static String FROM = "FROM";
    public final static String TO = "TO";
    public final static String FROM_TEXT = "FROM_TEXT";
    public final static String TO_TEXT = "TO_TEXT";
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText fromText;
    private EditText toText;
    private int position;
    private String[] languageAll;
    private String[] languageAvailable;
    private LanguageHelper languageHelper;

    final String LOG_TAG = "myLogs";

    public static DetailsFragment getInstance(String from, String to, String fromText, String toText) {
        //Лог специально показывающий что этот фрагмент инстанцируется единственнй раз
        Log.e("", "new DetailFragment");
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FROM, from);
        bundle.putString(TO, to);
        bundle.putString(FROM_TEXT, fromText);
        bundle.putString(TO_TEXT, toText);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    public void setAvailableSpinnerTo(String []languageTo) {
        if (languageTo == null) {
            languageTo = new String[]{"No Language"};
        }
        spinnerTo.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageTo));
    }

    public void setFromText(String fromText) {
        this.fromText.setText(fromText);
    }

    public void setToText(String fromText) {
        this.toText.setText(fromText);
    }

    public void setFrom(String lang) {
        //String to = spinnerTo.getSelectedItem().toString();
        languageAvailable = languageHelper.getAvailableLanguage(lang);
        for (int i=0; i<languageAll.length; i++) {
            if (spinnerFrom.getItemAtPosition(i).toString().equals(lang))
                spinnerFrom.setSelection(i);
        }
            saveTo(lang);
    }


    /*
    Функция, позволяющая сохранить язык To при изменении языка From.
    lang - язык from
     */
    public void saveTo(String lang) {
        if (spinnerTo != null) {
            String to = spinnerTo.getSelectedItem().toString();
            languageAvailable = languageHelper.getAvailableLanguage(lang);
            setTo(to);
        }
    }


    public void setTo(String lang) {
        spinnerTo.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageAvailable));
        for (int i = 0; i < languageAvailable.length; i++) {
            if (spinnerTo.getItemAtPosition(i).toString().equals(lang))
                spinnerTo.setSelection(i);
        }
    }

    public void exchange() {
        /*
        int buf = spinnerFrom.getSelectedItemPosition();
        spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
        spinnerTo.setSelection(buf);
        */
        String from = spinnerFrom.getSelectedItem().toString();
        String to = spinnerTo.getSelectedItem().toString();

        languageAvailable = languageHelper.getAvailableLanguage(to);

        spinnerFrom.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageAll));
        spinnerTo.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageAvailable));
        setFrom(to);
        setTo(from);

        /*for (int i=0; i<languageAll.length; i++) {
            if (spinnerFrom.getItemAtPosition(i).toString().equals(to))
                spinnerFrom.setSelection(i);
        }

        for (int i=0; i<languageAvailable.length; i++) {
            if (spinnerTo.getItemAtPosition(i).toString().equals(from))
                spinnerTo.setSelection(i);
        }*/
    }


    public String getFrom() {
        Log.d(MainActivity.TAG + " Details", "getFrom = " + getArguments().getInt(FROM));
        return getArguments().getString(FROM, "Английский");
    }

    public String getTo() {
        return getArguments().getString(TO, "Русский");
    }

    public String getFromText() {
        return getArguments().getString(FROM_TEXT, "");
    }

    public String getToText() {
        return getArguments().getString(TO_TEXT, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        languageHelper = ((MainActivity)getActivity()).getLanguageHelper();

        return inflater.inflate(R.layout.fragment_details, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //TextView title = (TextView) view.findViewById(R.id.detailsTitle);
        //title.setText("Погода в городе " + ListsFragment.arrayOfCity[getPosition()]);
        //TextView content = (TextView) view.findViewById(R.id.detailsContent); // ASYNC TASK!!!
        //content.setText("Подробности: \n");// + new Random().nextInt());
        languageAll = languageHelper.getAllLanguages();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageHelper.getAllLanguages());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom = (Spinner) view.findViewById(R.id.spiner_from);
        spinnerFrom.setAdapter(adapter);
        setFrom(getFrom());

        spinnerTo = (Spinner) view.findViewById(R.id.spiner_to);
        spinnerTo.setAdapter(adapter);
        setTo(getTo());

        Button okBtn = (Button) view.findViewById(R.id.OkBtn);
        Button exchangeBtn = (Button) view.findViewById(R.id.excahngeBtn);

        okBtn.setOnClickListener((OnClickListener) mCallback);
        exchangeBtn.setOnClickListener((OnClickListener) mCallback);

        Button menuBtn = (Button) view.findViewById(R.id.menuBtn);
        if (menuBtn != null)
            menuBtn.setOnClickListener((OnClickListener)getActivity());

        fromText = (EditText)view.findViewById(R.id.fromText);
        fromText.setText(getFromText());

        toText = (EditText)view.findViewById(R.id.toText);
        toText.setText(getToText());

        spinnerFrom.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) mCallback);
        spinnerTo.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) mCallback);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = activity;
    }

}
