package com.teampark.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Map;

public class PreferencesClass {
    static Preferences prefs = Gdx.app.getPreferences("Levels");
    static Preferences prefSettings = Gdx.app.getPreferences("Settings");
    static Preferences prefRecords = Gdx.app.getPreferences("Records");


    public static int getCountLevels(){
        return prefs.get().size();
    }

    //level
    public static void setLevelPreferences(String k,String level){
        prefs.putString(k,level.substring(level.length()-1));
        prefs.flush();
    }
    public static String getLevelPreferences(String k){
        return prefs.getString(k);
    }

    //sound
    public static void setSoundPreferences(String k, boolean sound){
        prefSettings.putBoolean(k,sound);
        prefSettings.flush();
    }
    public static Boolean getSoundPreferences(String k){
        return prefSettings.getBoolean(k);
    }

    //records

    public static void setRecordPreferences(String k, StringBuilder record){
        prefRecords.putString(k,record.toString());
        prefRecords.flush();

    }
    public static Map<String, ?> getRecordPreferences(){
        return prefRecords.get();
    }


}
