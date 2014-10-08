package com.example.admin.mytestapp.network;

    import android.util.Log;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

public class Network {

        public String urlConnection() throws IOException {
            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20140930T153442Z.105b1ec04823ba60.5c5ef51913657c82847062d4086a34017f00f3ea&ui=ru");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            String str = "";
            if (code == 200) {
                InputStream in = connection.getInputStream();
                str = handleInputStream(in);
            }
            return str;
        }

        public String urlConnection(String from,String to, String text) throws IOException {
            String urlTranslate = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140930T153442Z.105b1ec04823ba60.5c5ef51913657c82847062d4086a34017f00f3ea&text="+text+"&lang="+from + "-" + to;
            URL url = new URL(urlTranslate);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            String str = "";
            if (code == 200) {
                InputStream in = connection.getInputStream();
                str = handleInputStream(in);
            }
            return str;
        }

        private String handleInputStream(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result = "", line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            Log.e("", result);
            return result;
        }

        public String[] getNameList(JSONObject lang) throws IOException, JSONException {
            JSONArray tagArray = lang.names();
            String[] tagList= new String[tagArray.length()];
            String[] nameList= new String[tagArray.length()];
            for (int i=0; i < tagArray.length(); i++) {
                tagList[i]=tagArray.getString(i);
                nameList[i] = lang.getString(tagList[i]);
            }
            return nameList;
        }


    public Map getMapAliasToName(JSONObject lang) throws IOException, JSONException {
        Map<String, String> mapAliasToName = new HashMap<String, String>();
        JSONArray tagArray = lang.names();
        for (int i=0; i < tagArray.length(); i++) {
            mapAliasToName.put(tagArray.getString(i),lang.getString(tagArray.getString(i)));
        }
        return mapAliasToName;
    }

    public Map getMap(JSONObject json) throws IOException, JSONException {
        String key;
        String value;
        Map<String, String> map = new HashMap<String, String>();
        JSONArray dirs = json.getJSONArray("dirs");
        for (int i=0; i < dirs.length(); i++) {
            key = dirs.getString(i).substring(0,2);
            value = dirs.getString(i).substring(3,5);
            if (map.get(key)!= null) {
                map.put(key,map.get(key)+"," + value);
            }
            else {
                map.put(key,value);
            }
        }
        return map;
    }

    public Map getNameTag(JSONObject lang,String[] nameList) throws IOException, JSONException {
        JSONArray tagArray = lang.names();
        Map<String, String> nameTag = new HashMap<String, String>();
        for (int i=0; i < lang.length(); i++) {
            nameTag.put(nameList[i],tagArray.getString(i));
        }
        return nameTag;
    }

}
