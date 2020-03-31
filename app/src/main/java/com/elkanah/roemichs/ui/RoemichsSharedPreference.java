package com.elkanah.roemichs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class RoemichsSharedPreference {
    private static final String DEFAULT_VALUE = "";
    private static RoemichsSharedPreference outInstance = new RoemichsSharedPreference();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    private RoemichsSharedPreference(){}

    public static RoemichsSharedPreference getInstance(Context context){
      if(sharedPreferences == null){
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
      }
      return outInstance;
    }

    public void savePreference(String key, String value){
      editor.putString(key,value);
      editor.commit();
    }

    public void savePreference(String key, int value){
      editor.putInt(key, value);
      editor.commit();
    }

    public  void deletePreference(String Key) {
      editor.remove(Key);
      editor.commit();
    }


    public boolean clearPreference(){
      return editor.clear().commit();
    }


    public String retrievePreference(String key){
      return sharedPreferences.getString(key, DEFAULT_VALUE);
    }

    public int retrievePreferenceInt(String key) {
      return sharedPreferences.getInt(key, 0);
    }


}
