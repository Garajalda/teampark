package com.teampark.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;

public class Boton extends ObjetosTileInteractivos {
    private static TiledMapTileSet tileSet;
    public static boolean btnPulsado = false;
    public Boton(JuegoScreen screen, Rectangle bounds) {
        super(screen, bounds);
        Boton.btnPulsado = false;
        tileSet = map.getTileSets().getTileSet("platform_asset");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.BOTON_BIT);
    }

    @Override
    public void onFootHit() {
        btnPulsado = true;
        setCategoryFilter(MainGame.DESTROYED_BIT);
           // MainGame.managerSongs.get("audio/sounds/click.wav",Music.class).play();
        int TILE_BTN_PULSADO = 583;
        getCell().setTile(tileSet.getTile(TILE_BTN_PULSADO));
    }

    @Override
    public void onBodyHit() {

    }


}
