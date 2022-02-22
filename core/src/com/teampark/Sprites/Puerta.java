package com.teampark.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;

public class Puerta extends ObjetosTileInteractivos {
    private static TiledMapTileSet tileSet;
    private static boolean nextLevel;
    public Puerta(JuegoScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("platform_asset");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.PUERTA_BIT);
    }

    public void setNextLevel(boolean next){
        nextLevel = true;
    }

    public static boolean isNextLevel(){
        return nextLevel;
    }


    @Override
    public void onFootHit() {

    }

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
