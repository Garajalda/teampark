
package com.teampark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.teampark.screens.GameStart;

/**
 * Clase que inicia todas las dependencias del juego, es la raíz del juego.
 * @author Gara Jalda
 * @see com.badlogic.gdx.Game
 * @version 1.0
 */
public class MainGame extends Game{

	/**
	 * Formato de ancho de la ventana
	 */
	public static final int VIEW_WIDTH = 400;
	/**
	 * Formato de alto de la ventana
	 */
	public static final int VIEW_HEIGHT = 208;
	/**
	 * Pixeles por mentro del mundo
	 */
	public static final int PPM = 100;
	/**
	 * Define el bit del suelo
	 */
	public static final short SUELO_BIT = 1;
	/**
	 * Define el bit del gato
	 */
	public static final short CAT_BIT = 2;
	/**
	 * Define el bit de la llave
	 */
	public static final short KEY_BIT = 4;
	/**
	 * Define el bit del botón
	 */
	public static final short BOTON_BIT = 8;
	/**
	 * Define el bit de destruír
	 */
	public static final short DESTROYED_BIT = 16;
	/**
	 * Define el bit del ascensor
	 */
	public static final short ASCENSOR_BIT = 32;
	/**
	 * Define el bit de la puerta
	 */
	public static final short PUERTA_BIT = 64;
	/**
	 * Define el bit del cubo
	 */
	public static final short CUBO_BIT = 128;
	/**
	 * Define el sprite
	 * @see SpriteBatch
	 */
	public SpriteBatch batch;
	/**
	 * Define la lista de eventos
	 * @see InputMultiplexer
	 */
	public static InputMultiplexer multiplexer = new InputMultiplexer();
	/**
	 * Define la lista de Assets
	 * @see AssetManager
	 */
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


	/**
	 * Método que permite generar un estilo de fuente.
	 * @param size Es el tamaño del texto.
	 * @param color Es el color del texto.
	 * @return Devuelve un estilo de texto.
	 */
	public static Label.LabelStyle generatorStyle(int size, Color color){
		final Label.LabelStyle labelStyle;
		final BitmapFont font;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/dogicapixel.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = size;
		font = generator.generateFont(params);
		labelStyle = new Label.LabelStyle(font, color);
		return labelStyle;
	}


	/**
	 * Método que libera los recursos
	 */
	@Override
	public void dispose() {
		super.dispose();
		managerSongs.dispose();
		batch.dispose();
	}

	/**
	 * Método que renderiza
	 */
	@Override
	public void render() {
		super.render();
	}

}
