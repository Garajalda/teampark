package com.teampark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teampark.screens.GameStart;

import io.socket.client.Socket;

public class MainGame extends Game{

	//720-144
	public static final int VIEW_WIDTH = 400;
	public static final int VIEW_HEIGHT = 208;
	public static final int PPM = 100;

//hoal

	public static final short GROUND_BIT = 1;
	public static final short CAT_BIT = 2;
	public static final short KEY_BIT = 4;
	public static final short BOTON_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short ASCENSOR_BIT = 32;

	public SpriteBatch batch;
	public static InputMultiplexer multiplexer = new InputMultiplexer();

	public static AssetManager managerSongs;


	public static final String EVENT_CONNECT = "connect";
	public static final String EVENT_DISCONNECT = "disconnect";
	public MainGame(){

	}
	static public FireBaseInterface fbic;
	public MainGame(FireBaseInterface fbic){
		this.fbic = fbic;
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		managerSongs = new AssetManager();
		//managerSongs.load("audio/sounds/click.wav", Sound.class);
		managerSongs.load("audio/music/aeon.ogg", Music.class);
		managerSongs.finishLoading();

		setScreen(new GameStart(this));

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
