package com.teampark.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;

/**
 * Clase que define la puerta para pasar al siguiente nivel
 * @see ObjetosTileInteractivos
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Puerta extends ObjetosTileInteractivos {
    /**
     * Recoge el tileset del mapa
     */
    private static TiledMapTileSet tileSet;
    /**
     * Define si se pasa al siguiente nivel
     */
    private static boolean nextLevel;

    /**
     * Constructor de clase que define la puerta
     * @param screen
     * @param bounds
     */
    public Puerta(JuegoScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("platform_asset");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.PUERTA_BIT);
    }

    /**
     * Método que indica si se pasa al siguiente
     * @param next
     */
    public static void setNextLevel(boolean next){
        nextLevel = next;
    }

    /**
     * Método que devuelve si se pasa al siguiente nivel
     * @return nextLevel
     */
    public static boolean isNextLevel(){
        return nextLevel;
    }


    /**
     * Método que indica si se ha tocado con la parte del pie
     */
    @Override
    public void contactoFoot() {

    }

    /**
     * Método que indica si se ha tocado con el cuerpo que reemplace la celda por otra nueva.
     */
    @Override
    public void onBodyHit() {

        setCategoryFilter(MainGame.DESTROYED_BIT);

            int TILE_PUERTA_OPEN_2 = 290;
            int TILE_PUERTA_OPEN_1 = 258;

            getCell().setTile(tileSet.getTile(TILE_PUERTA_OPEN_2));
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
            layer.getCell((int)(body.getPosition().x * MainGame.PPM/16),(int)(body.getPosition().y * MainGame.PPM/16)+ MainGame.PPM/64).setTile(tileSet.getTile(TILE_PUERTA_OPEN_1));

    }
}
