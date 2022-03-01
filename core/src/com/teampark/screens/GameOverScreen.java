package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;


/**
 * Clase que define la pantalla cuando pierde el personaje
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class GameOverScreen implements Screen {


    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    private final Stage stage;

    /**
     * Define la raíz del juego donde se cargan los assets.
     * @see MainGame
     */
    private final MainGame game;

    /**
     * Define el tipo de gato que proviene del contructor
     * @see Cat
     */
    Cat.TypeCat gato;


    /**
     * Define el nivel del juego en el que se encuentra actualmente.
     */
    String level;

    /**
     * Constructor de clase que definen los elementos que se ven en pantalla.
     * @param level
     * @param game
     * @param gato
     */
    public GameOverScreen(String level, MainGame game, Cat.TypeCat gato){
        this.game = game;
        this.gato = gato;
        this.level =level;
        Viewport viewport = new FitViewport((float) MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT);
        stage = new Stage(viewport, ((MainGame)game).batch);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label playAgainLabel = new Label("Pulsa para reiniciar la partida",MainGame.generatorStyle(10,Color.WHITE));
        Image image = new Image(new Texture("gameover.png"));
        image.setScale(0.6f,0.5f);
        table.add(image).expandX().padLeft(115).padBottom(10);
        table.row();
        table.add(playAgainLabel).expandX().padBottom(80);

        stage.addActor(table);
    }


    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }

    /**
     * Método que renderiza la pantalla del GameOver, en el se puede volver a la pantalla del JuegoScreen.
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(Gdx.input.justTouched()){

            if(level.equals("1-1")){

                Music music = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3", Music.class);
                music.setLooping(true);
                music.play();
                game.setScreen(new Level1(level,game, gato));


            }
            if(level.equals("1-2")){
                Music music = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3", Music.class);
                music.setLooping(true);
                music.play();
                game.setScreen(new Level2(level,game,gato));
            }
            dispose();
        }
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
     * @see Screen#resume() ()
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
