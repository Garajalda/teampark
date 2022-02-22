package com.teampark.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesClass {
    static Preferences prefs = Gdx.app.getPreferences("Levels");
    static String levelPrefs;
    public static void setLevelPreferences(String level){
        prefs.putString("level", level.substring(level.length()-1));
        prefs.flush();
        levelPrefs = prefs.getString("level");
    }

    public static String getLevelPreferences(){
        return levelPrefs;
    }

    public static int getCountLevels(){
        return prefs.get().size();
    }

    public static void setLevelPreferences(String k,String level){
        prefs.putString(k,level.substring(level.length()-1));
        prefs.flush();
    }

    public static String getLevelPreferences(String k){
        return prefs.getString(k);
    }

}
