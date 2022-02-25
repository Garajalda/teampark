package com.teampark.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesClass {
    static Preferences prefs = Gdx.app.getPreferences("Levels");
    static Preferences prefSettings = Gdx.app.getPreferences("Settings");



    public static int getCountLevels(){
        return prefs.get().size();
    }

    public static void setLevelPreferences(String k,String level){
        prefs.putString(k,level.substring(level.length()-1));
        prefs.flush();
    }

    public static void setSoundPreferences(String k, boolean sound){
        prefSettings.putBoolean(k,sound);
        prefSettings.flush();
    }
    public static Boolean getSoundPreferences(String k){
        return prefSettings.getBoolean(k);
    }

    public static String getLevelPreferences(String k){
        return prefs.getString(k);
    }

}
