package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private MainGame game;
    Cat.TypeCat gato;
    String level;
    JuegoScreen screen;
    public GameOverScreen(String level, MainGame game, Cat.TypeCat gato){
        this.game = game;
        this.gato = gato;
        this.level =level;
        this.screen = screen;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        stage = new Stage(viewport, ((MainGame)game).batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label playAgainLabel = new Label("Pulsa  para  reiniciar  la  partida",font);
        Image image = new Image(new Texture("gameover.png"));
        image.setScale(0.6f,0.5f);
        table.add(image).expandX().padLeft(115).padBottom(10);
        table.row();
        table.add(playAgainLabel).expandX().padBottom(80);

        stage.addActor(table);
    }

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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
