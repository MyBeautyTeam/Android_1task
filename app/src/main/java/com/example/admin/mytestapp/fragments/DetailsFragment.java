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
    public final static String WEATHER = "WEATHER";
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText fromText;
    private EditText toText;
    private int position;

    final String LOG_TAG = "myLogs";

    public static DetailsFragment getInstance(int from, int to, String fromText, String toText) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FROM, from);
        bundle.putInt(TO, to);
        bundle.putString(FROM_TEXT, fromText);
        bundle.putString(TO_TEXT, toText);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    public void setFromText(String fromText) {
        this.fromText.setText(fromText);
    }

    public void setToText(String fromText) {
        this.toText.setText(fromText);
    }

    public void setFrom(int position) {
        Log.d(MainActivity.TAG+" D", "AFTselectedFROM = " + this.spinnerFrom.getSelectedItemPosition());
        this.spinnerFrom.setSelection(position);
        Log.d(MainActivity.TAG+" D", "AFTselectedFROM = " + this.spinnerFrom.getSelectedItemPosition());
    }

    public void setTo(int position) {
        Log.d(MainActivity.TAG+" D", "BEFselectedTO = " + this.spinnerTo.getSelectedItemPosition());
        this.spinnerTo.setSelection(position);
        Log.d(MainActivity.TAG+" D", "AFTselectedTO = " + this.spinnerTo.getSelectedItemPosition());
    }

    public void exchange() {
        int buf = spinnerFrom.getSelectedItemPosition();
        spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
        spinnerTo.setSelection(buf);
    }

    private int getFrom() {
        Log.d(MainActivity.TAG + " Details", "getFrom = " + getArguments().getInt(FROM));
        return getArguments().getInt(FROM, 3);
    }

    private int getTo() {
        return getArguments().getInt(TO, 0);
    }

    private String getFromText() {
        return getArguments().getString(FROM_TEXT, "");
    }

    private String getToText() {
        return getArguments().getString(TO_TEXT, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //TextView title = (TextView) view.findViewById(R.id.detailsTitle);
        //title.setText("Погода в городе " + ListsFragment.arrayOfCity[getPosition()]);
        //TextView content = (TextView) view.findViewById(R.id.detailsContent); // ASYNC TASK!!!
        //content.setText("Подробности: \n");// + new Random().nextInt());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ListsFragment.arrayOfCity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom = (Spinner) view.findViewById(R.id.spiner_from);
        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setSelection(getFrom());

        spinnerTo = (Spinner) view.findViewById(R.id.spiner_to);
        spinnerTo.setAdapter(adapter);
        spinnerTo.setSelection(getTo());

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
