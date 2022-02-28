package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;

/**
 * Ventana para los creditos
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0, 2022/02/15
 */
public class Creditos implements Screen {

    /**
     * Escenario de la Screen
     * @see Stage
     */
    private final Stage stage;

    /**
     * Constructor que define el texto que tendrá los créditos y el comportamiento de eventos.
     * @param game
     * @param screen
     * @param gato
     */
    public Creditos(final MainGame game, final Screen screen, final Cat.TypeCat gato){

        Viewport viewport = new FitViewport((float) MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT);
        Gdx.input.setInputProcessor(MainGame.multiplexer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/dogicapixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 10;
        BitmapFont font = generator.generateFont(params);

        stage = new Stage(viewport, ((MainGame)game).batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label labelCreditos = new Label("Créditos",labelStyle);
        Label labelSkin = new Label("Skins: RAY3K ",labelStyle);
        Label labelMusica = new Label("Música:",labelStyle);
        Label labelMusicaInicio = new Label("Inicio: Grindhold - AEON ",labelStyle);
        Label labelMusicaNivel1 = new Label("1: CodeManu - Intro Theme ",labelStyle);
        Label labelMusicaNivel2 = new Label("2: CodeManu - Worldmap Theme  ",labelStyle);
        Label labelCat = new Label("Personajes: Bluecarrot16  ",labelStyle);
        Label labelButtons = new Label("Botones: Totuslotus",labelStyle);
        Label labelSalir = new Label("Toca para salir",labelStyle);


        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(10);
        table.add(labelCreditos).expandX();
        table.row().left().padTop(15);
        table.add(labelSkin);
        table.row().left().padTop(10);
        table.add(labelMusica);
        table.row().center().padTop(10);
        table.add(labelMusicaInicio).center();
        table.row().center().padTop(10);
        table.add(labelMusicaNivel1).center();
        table.row().center().padTop(10);
        table.add(labelMusicaNivel2).center();
        table.row().left().padTop(20);
        table.add(labelCat);
        table.row().left().padTop(10);
        table.add(labelButtons);
        table.row().center().padTop(10);
        table.add(labelSalir);
        stage.addActor(table);

        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (screen instanceof Level2){
                    game.setScreen(new Level2("1-2",game,gato));
                    dispose();
                }
                if (screen instanceof Level1){
                    game.setScreen(new Level1("1-1",game,gato));
                    dispose();
                }
                if(screen instanceof SetPersonaje){
                    game.setScreen(new SetPersonaje(game));
                    dispose();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }


    /**
     * Método que permite el renderizado de la ventana o la escena y añade los diferentes eventos de una escena.
     * @see Screen#render(float)
     * @param delta
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainGame.multiplexer.addProcessor(stage);
        stage.draw();

        //font.draw(game.batch, "jpña",20,70);
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
