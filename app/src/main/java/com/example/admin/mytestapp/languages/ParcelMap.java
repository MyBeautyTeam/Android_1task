package com.example.admin.mytestapp.languages;

/**
 * Created by asus on 07.10.2014.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap implements Parcelable {
    private Map<String,String> map;

    public ParcelMap() {
        map = new HashMap<String,String>();
    }

    public ParcelMap(Parcel in) {
        map = new HashMap<String,String>();
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelMap createFromParcel(Parcel in) {
            return new ParcelMap(in);
        }

        public ParcelMap[] newArray(int size) {
            return new ParcelMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(map.size());
        for (String s: map.keySet()) {
            dest.writeString(s);
            dest.writeString(map.get(s));
        }
    }

    public void readFromParcel(Parcel in) {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            map.put(in.readString(), in.readString());
        }
    }

    public String get(String key) {
        return map.get(key);
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public void putAll(Map <String,String> nameMap) {
        map = nameMap;
    }
}
