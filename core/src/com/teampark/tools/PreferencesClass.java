package com.teampark.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Map;

/**
 * Clase que guarda los ajustes del juego
 * @see Preferences
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class PreferencesClass {

    /**
     * Obtiene clave-valor de niveles
     */
    static Preferences prefs = Gdx.app.getPreferences("Levels");
    /**
     * Obtiene clave-valor de ajustes
     */
    static Preferences prefSettings = Gdx.app.getPreferences("Settings");
    /**
     * Obtiene clave-valor de records
     */
    static Preferences prefRecords = Gdx.app.getPreferences("Records");

    static Preferences prefVibrator = Gdx.app.getPreferences("SettingsApp");

    /**
     * Método que obtiene el total de niveles que se ha pasado.
     * @return
     */
    public static Map<String, ?> getCountLevels(){
        return prefs.get();
    }

    /**
     * Método que guarda el nivel que se ha pasado.
     * @param k
     * @param level
     */
    //level
    public static void setLevelPreferences(String k,String level){
        prefs.putString(k,level.substring(level.length()-1));
        prefs.flush();

    }

    /**
     * Método que recoge un nivel que se ha pasado.
     * @param k
     * @return
     */
    public static String getLevelPreferences(String k){
        return prefs.getString(k);
    }

    /**
     * Método que guarda los ajustes de vibración
     * @param k
     * @param vibrator
     */
    public static void setPrefVibrator(String k, boolean vibrator){
        prefVibrator.putBoolean(k,vibrator);
        prefVibrator.flush();
    }

    /**
     * Método que recoge los ajustes de vibración
     * @param k
     * @return
     */
    public static Boolean getPrefVibrator(String k){
        return prefVibrator.getBoolean(k);
    }

    /**
     * Método que guarda los ajustes del sonido
     * @param k
     * @param sound
     */
    //sound
    public static void setSoundPreferences(String k, boolean sound){
        prefSettings.putBoolean(k,sound);
        prefSettings.flush();
    }

    /**
     * Método que recoge los ajustes de un sonido
     * @param k
     * @return
     */
    public static Boolean getSoundPreferences(String k){
        return prefSettings.getBoolean(k);
    }

    //records

    /**
     * Método que guarda un record
     * @param k
     * @param record
     */
    public static void setRecordPreferences(String k, StringBuilder record){
        prefRecords.putString(k,record.toString());
        prefRecords.flush();

    }

    /**
     * Método que recoge los records guardados
     * @return Map<String,?>
     */
    public static Map<String, ?> getRecordPreferences(){
        return prefRecords.get();
    }


}
