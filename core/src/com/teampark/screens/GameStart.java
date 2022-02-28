package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.tools.PreferencesClass;

/**
 * Esta clase es la pantalla que se encuentra al iniciar la aplicación.
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0, 2022/01/05
 */
public class GameStart implements Screen {

    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    private final Stage stage;

    /**
     * Define la raíz del juego donde se cargan los assets.
     * @see MainGame
     */
    final MainGame game;

    /**
     * Imagen que se encuentra al iniciar la aplicación
     * @see Image
     */
    Image fondoImage;

    /**
     * Musica de la pantalla
     * @see Music
     */
    Music music;

    /**
     * Imagen renderizada
     * @see SpriteBatch
     */
    SpriteBatch batch;

    /**
     * Constructor que define los diferentes elementos que se muentran en la ventana.
     * @param game
     * @param batch
     */
    public GameStart(MainGame game, SpriteBatch batch){

        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        this.batch = batch;

        stage = new Stage(viewport, game.batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        this.game = game;

        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);

        if (PreferencesClass.getSoundPreferences("sound")) {
            music.play();
        } else {
            music.stop();
        }

        Table table = new Table();
        table.setFillParent(true);
        Label label = new Label("Team Park", font);

        fondoImage = new Image(new TextureRegion(new Texture("fondostart.png")));

        fondoImage.setScale(1f,1f);
        table.add(label).expandX().padLeft(100).padRight(50);
        table.add(fondoImage).expandX();

        table.row();
        stage.addActor(table);

    }

    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }


    /**
     * Método que inicia el renderizado de la ventana o pasa a otra ventana donde se puede elegir el personaje.
     * @param delta
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {

        float gyroZ = Gdx.input.getGyroscopeZ();
        if(Gdx.input.justTouched()){

            game.setScreen(new SetPersonaje((MainGame) game));
            dispose();
        }

        boolean gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        if(gyroscopeAvail){
            fondoImage.addAction(Actions.rotateTo(gyroZ));
        }

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    /**
     * @see Screen#resize(int, int)
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see Screen#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see Screen#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * @see Screen#hide()
     */
    @Override
    public void hide() {

    }

    /**
     * Método que libera recursos de la escena.
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
