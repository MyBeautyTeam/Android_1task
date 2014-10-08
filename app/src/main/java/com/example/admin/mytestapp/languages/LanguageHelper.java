package com.example.admin.mytestapp.languages;

import android.util.Log;

import com.example.admin.mytestapp.network.Network;

import java.util.HashMap;

/**
 * Created by asus on 01.10.2014.
 */
public class LanguageHelper {
    private ParcelMap mapNameToAlias;
    private ParcelMap mapAliasToName;
    private ParcelMap mapAliasToAvailable;
    private String [] listOfLanguages;

    private void fillMapNameToAlias(String response) {

    }

    public LanguageHelper(ParcelMap parcelMapAliasToAvailable,ParcelMap parcelMapNameToAlias,ParcelMap parcelAliasToName, String[] listOfLang ) {
        mapNameToAlias = parcelMapNameToAlias;
        mapAliasToAvailable = parcelMapAliasToAvailable;
        listOfLanguages = listOfLang;
        mapAliasToName = parcelAliasToName;
/*        Network network = new Network();
        String response = network.urlConnection();

        fillMapNameToAlias(response);

*/
    }

    public String[] getAllLanguages() {
        return  listOfLanguages;
    }

    public String [] getAvailableLanguage(String lang) {
        String availableAlias = mapAliasToAvailable.get(mapNameToAlias.get(lang));
        String[] listAvailableLang = availableAlias.split(",");
        if(listAvailableLang != null)
            for(int i = 0; i< listAvailableLang.length; i++) {
                listAvailableLang[i] = mapAliasToName.get(listAvailableLang[i]);
            }
        return listAvailableLang;
    }

    public String getAlias (String lang) {
        String Alias = mapNameToAlias.get(lang);
        return Alias;
    }



}
