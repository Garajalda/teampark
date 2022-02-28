
package com.teampark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teampark.screens.GameStart;

/**
 * Clase que inicia todas las dependencias del juego, es la raíz del juego.
 * @author Gara Jalda
 * @see com.badlogic.gdx.Game
 * @version 1.0
 */
public class MainGame extends Game{

	public static final int VIEW_WIDTH = 400;
	public static final int VIEW_HEIGHT = 208;
	public static final int PPM = 100;
	public static final short GROUND_BIT = 1;
	public static final short CAT_BIT = 2;
	public static final short KEY_BIT = 4;
	public static final short BOTON_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short ASCENSOR_BIT = 32;
	public static final short PUERTA_BIT = 64;
	public static final short CUBO_BIT = 128;
	public SpriteBatch batch;
	public static InputMultiplexer multiplexer = new InputMultiplexer();
	public static AssetManager managerSongs;

	/**
	 * Método que carga la música, el juego y las imagenes.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();


		managerSongs = new AssetManager();
		managerSongs.load("audio/sounds/click.wav", Sound.class);
		managerSongs.load("audio/music/MusicPlatform.mp3",Music.class);
		managerSongs.load("audio/music/aeon.ogg", Music.class);
		managerSongs.load("audio/music/MusicPlatform2.mp3",Music.class);
		managerSongs.finishLoading();

		setScreen(new GameStart(this,batch));

	}



	@Override
	public void dispose() {
		super.dispose();
		managerSongs.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		super.render();


	}

}
