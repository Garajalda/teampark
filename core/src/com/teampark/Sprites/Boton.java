package com.teampark.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;
import com.teampark.tools.PreferencesClass;

/**
 * Clase que define un botón que inicia una interacción en el nivel
 * @see ObjetosTileInteractivos
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Boton extends ObjetosTileInteractivos {
    /**
     * Recoge el tileset del mapa
     */
    private static TiledMapTileSet tileSet;
    /**
     * Define si el botón ha sido pulsado o no.
     */
    public static boolean btnPulsado = false;

    /**
     * Define el cuerpo del botón
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro, en este caso sería el nivel 1.
     * @param bounds Define el rectángulo.
     * @see Rectangle
     */
    public Boton(JuegoScreen screen, Rectangle bounds) {
        super(screen, bounds);
        Boton.btnPulsado = false;
        tileSet = map.getTileSets().getTileSet("platform_asset");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.BOTON_BIT);
    }

    /**
     * Método que indica cuando ha tenido contacto el cuerpo del gato, concretamente los pies en el botón
     */
    @Override
    public void contactoFoot() {
        btnPulsado = true;
        setCategoryFilter(MainGame.DESTROYED_BIT);
        MainGame.managerSongs.get("audio/sounds/click.wav", Sound.class).play();
        if (PreferencesClass.getPrefVibrator("vibrator")) {
            Gdx.input.vibrate(200);
        }
        int TILE_BTN_PULSADO = 583;
        getCell().setTile(tileSet.getTile(TILE_BTN_PULSADO));
    }


    @Override
    public void onBodyHit() {

    }


}
