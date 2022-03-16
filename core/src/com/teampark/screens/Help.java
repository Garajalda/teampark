package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.tools.PreferencesClass;

/**
 * Esta clase es la pantalla de ayuda.
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Help implements Screen {

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
     * Temporizador del render
     */
    private float timeCount;

    /**
     * Imagen renderizada
     * @see SpriteBatch
     */
    private final SpriteBatch batch;

    /**
     * Lista de diferentes sprites de ayuda.
     */
    private final Array<Sprite> tutoriales;

    private int cont;

    /**
     * Constructor que inicia los elementos de la ventana de ayuda y salir de la pantalla, así como diferentes eventos.
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     * @see MainGame
     */
    public Help(final MainGame game){

        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        this.batch = game.batch;
        this.game = game;
        this.cont = 0;

        tutoriales = new Array<>();
        timeCount = 0;
        stage = new Stage(viewport, batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Music music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);

        if (PreferencesClass.getSoundPreferences("sound")) {
            music.play();
        } else {
            music.stop();
        }


        TextureRegion buttonSalirT = new TextureRegion(new Texture("controllers/UI_orange_buttons_pressed_3.png"),16,48,16,16);
        Sprite dR = new Sprite(buttonSalirT);
        Image buttonSalir = new Image(dR);


        Table table = new Table();
        table.top().padTop(10);
        table.setFillParent(true);
        Label label = new Label("Ayuda", MainGame.generatorStyle(12, Color.WHITE));


        Sprite tutoCaminar = new Sprite(new TextureRegion(new Texture("tutorial/pictureTutorialCaminar.png")));
        tutoCaminar.setSize(300,150);
        tutoCaminar.setPosition(50,30);


        Sprite tutoSalto = new Sprite(new TextureRegion(new Texture("tutorial/pictureTutoSalto.png")));
        tutoSalto.setSize(300,150);
        tutoSalto.setPosition(50,30);

        tutoriales.add(tutoCaminar);
        tutoriales.add(tutoSalto);

        table.add(label).expandX().top();
        table.add(buttonSalir).right();


        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        buttonSalir.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SetPersonaje(game));
                dispose();
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
     * Método que inicia el renderizado e inicia el contador para ir pasando de imagenes.
     * @param delta Parámetro que indica el delta.
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        timeCount += delta;
        if(timeCount >=2){

            if(cont >= tutoriales.size-1){
                cont = 0;
            }else{
                cont++;
            }
            timeCount = 0;

        }
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        batch.begin();
        batch.draw(tutoriales.get(cont),50, 30,300,150);
        batch.end();

        stage.draw();

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
    }
}
