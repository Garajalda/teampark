package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.scenes.TableRecords;
import com.teampark.tools.PreferencesClass;

/**
 * Esta clase es la pantalla que recoge los records almacenados.
 * @see Screen
 * @see TableRecords
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Records implements Screen {

    /**
     * Define la resolución de la pantalla
     * @see Viewport
     */
    private final Viewport viewport;

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
     * Ventana actual definida en el constructor
     * @see Screen
     */
    private final Screen screen;

    /**
     * Define el tipo del gato actual
     * @see Cat
     */
    private Cat.TypeCat gatoElegido;

    /**
     *Tiempo en el que finalizó el nivel
     */
    private StringBuilder tiempoTotal;
    /**
     * Recoge los records guardados
     * @see TableRecords
     */
    private TableRecords tableRecords;
    /**
     * Implementa tabla en la ventana
     * @see Table
     */
    private final Table table;

    /**
     * Define una etiqueta de título de la ventana
     * @see Label
     */
    private final Label labelRecords;


    /**
     * Constructor que define la ventana records en caso de superar el nivel 2
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro, en este caso sería el nivel 1.
     */
    public Records(final MainGame game,final Screen screen){
        this.game = game;
        this.screen = screen;
        this.viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        Gdx.input.setInputProcessor(MainGame.multiplexer);

        stage = new Stage(viewport, ((MainGame)game).batch);


        this.labelRecords = new Label("Records",MainGame.generatorStyle(10,Color.WHITE));

        this.table = new Table();

        table.setFillParent(true);
        table.top().padTop(15);


        table.row().center().expandX();
        table.add(labelRecords).center().expandX();
        table.row().expandX().center();
        for (String k: PreferencesClass.getRecordPreferences().keySet()) {
            table.row().expandX().padTop(15).center();
            table.add(new Label(k,MainGame.generatorStyle(10,Color.WHITE))).center();
            table.add(new Label(PreferencesClass.getRecordPreferences().get(k).toString(),MainGame.generatorStyle(10,Color.WHITE))).center();
            table.row().expandX().center();
        }

        Onclick();
        stage.addActor(table);
    }

    /**
     * Constructor que define records en caso de que se buscar un record sin haber terminado la partida
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro, en este caso sería el nivel 1.
     * @param gatoElegido Obtiene el tipo de gato actual.
     * @param tiempoTotal Obtiene la marca final del tiempo en el que se ha tardado en superar el juego.
     */
    public Records(final MainGame game, final Screen screen, final Cat.TypeCat gatoElegido, StringBuilder tiempoTotal){
        this.game = game;
        this.screen = screen;
        this.tiempoTotal = tiempoTotal;

        this.gatoElegido = gatoElegido;
        this.viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);


        Gdx.input.setInputProcessor(MainGame.multiplexer);

        stage = new Stage(viewport, ((MainGame)game).batch);

        this.labelRecords = new Label("Records",MainGame.generatorStyle(10,Color.WHITE));

        this.table = new Table();

        table.setFillParent(true);
        table.top().padTop(15);


        table.row().center().expandX();
        table.add(labelRecords).center().expandX();
        table.row().expandX().center();
        for (String k: PreferencesClass.getRecordPreferences().keySet()) {
            table.row().expandX().padTop(15).center();
            table.add(new Label(k,MainGame.generatorStyle(10,Color.WHITE))).center();
            table.add(new Label(PreferencesClass.getRecordPreferences().get(k).toString(),MainGame.generatorStyle(10,Color.WHITE))).center();
            table.row().expandX().center();

        }

        Onclick();

        stage.addActor(table);

    }

    /**
     * Método que define el evento de la escena cuando se quiera volver a una ventana anterior.
     */
    public void Onclick(){
        this.tableRecords = new TableRecords(screen, tiempoTotal, game.batch);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(!tableRecords.table1.isVisible()){
                    if (screen instanceof Level2){
                        game.setScreen(new Level2("1-2",game,gatoElegido));
                        dispose();

                    }
                    if(screen instanceof Level1){
                        game.setScreen(new Level1("1-1",game,gatoElegido));
                        dispose();
                    }

                    if(screen instanceof SetPersonaje){
                        game.setScreen(new SetPersonaje(game));
                        dispose();
                    }
                    if(screen == null){
                        game.setScreen(new SetLevel(game,gatoElegido));
                        dispose();
                    }
                    if(screen instanceof SetLevel){
                        game.setScreen(new SetLevel(game,gatoElegido));
                        dispose();
                    }
                }
                return true;
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
     * Método que inicia el renderizado.
     * @param delta Parámetro que indica el delta.
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainGame.multiplexer.addProcessor(stage);
        stage.draw();
        tableRecords.stage.draw();
    }

    /**
     * @see Screen#resize(int, int)
     * @param width Indica el ancho de pantalla.
     * @param height Indica el alto de pantalla.
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
        tableRecords.stage.dispose();
    }
}
