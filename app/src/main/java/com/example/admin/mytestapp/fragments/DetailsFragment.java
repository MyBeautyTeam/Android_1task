package com.example.admin.mytestapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.mytestapp.R;

/**
 * Created by Admin on 29.09.2014.
 */
public class DetailsFragment extends Fragment {

    //private static Bundle bundle;
    public final static String LANG_NO = "position";
    public final static String WEATHER = "WEATHER";

    final String LOG_TAG = "myLogs";

    public static DetailsFragment getInstance(int cityNo) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LANG_NO, cityNo);
        //bundle.putString(WEATHER, "АХУЕННО");
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    public int getPosition() {
        return getArguments().getInt("position", 0);
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

        Spinner spinner = (Spinner) view.findViewById(R.id.spiner);
        spinner.setAdapter(adapter);
        spinner.setSelection(getPosition());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "Fragment1 onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onCreate");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Fragment1 onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Fragment1 onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Fragment1 onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Fragment1 onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "Fragment1 onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Fragment1 onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "Fragment1 onDetach");
    }


}
