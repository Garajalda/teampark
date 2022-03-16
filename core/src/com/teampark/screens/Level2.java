package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.teampark.MainGame;
import com.teampark.Sprites.Cubo;
import com.teampark.Sprites.Puerta;
import com.teampark.Sprites.cats.Cat;
import com.teampark.items.Key;
import com.teampark.tools.CreadorDeMundo;
import com.teampark.tools.PreferencesClass;
import com.teampark.tools.WorldContactListener;

/**
 * Clase que define el nivel 2 de la aplicación
 * @see Screen
 * @see JuegoScreen hereda
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Level2 extends JuegoScreen{

    /**
     * Inicia la musica del nivel 2
     */
    Music music;
    /**
     * Crea las colisiones del nivel 2
     * @see CreadorDeMundo
     */
    CreadorDeMundo creadorDeMundo;

    /**
     * Define el tipo de gato actualmente elegido
     * @see Cat
     */
    Cat.TypeCat gatoElegido;

    /**
     * Constructor que define el mapa, los elementos de la ventana
     * @param level Obtiene el actual nivel del juego como String.
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     * @param gatoElegido Obtiene el tipo de gato actual.
     */
    public Level2(String level, MainGame game, Cat.TypeCat gatoElegido) {
        super(level, game, gatoElegido);
        TmxMapLoader mapLoader = new TmxMapLoader();
        this.gatoElegido = gatoElegido;
        map = mapLoader.load("lvl2.tmx");

        //Texto = se paciente, los cubos de hielo son muy resvaladizos y entre todos pesan demasiado para el pobre gatito.

        this.music  = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
        this.music  = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
        music.setLooping(true);

        if(PreferencesClass.getSoundPreferences("sound")){
            music.play();
        }
        //unidades de escala
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / MainGame.PPM);
        //posicion de camara con relacion y aspecto del mundo
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //creando los objetos del mundo
        creadorDeMundo = new CreadorDeMundo(this);

        //tipo contacto
        world.setContactListener(new WorldContactListener());

        cat = new Cat(this, gatoElegido,50,40);
        key = new Key(this,12.8f, (float) 160 / MainGame.PPM);

    }

    /**
     * Método que define los eventos de movimiento del personaje.
     * @param dt Parámetro que indica el delta.
     * @see JuegoScreen#handleInput(float)
     */
    @Override
    protected void handleInput(float dt) {
        super.handleInput(dt);
    }

    /**
     * Reescala la pantalla dependiendo del ancho y el alto.
     * @param width Es el ancho de la pantalla.
     * @param height Es el alto de la pantalla.
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * Método que define la actualización y movimiento de los diferentes componentes dentro de la pantalla.
     * @param dt Parámetro que indica el delta.
     * @see JuegoScreen#update(float)
     */
    @Override
    public void update(float dt) {
        super.update(dt);
        cat.update(dt); key.update(dt,cat);
    }

    /**
     * Renderiza la pantalla del nivel 2.
     * @param delta Parámetro que indica el delta.
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.6f, 0.7f, 0.7f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        //matriz de proyeccion

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        cat.draw(game.batch);
        key.draw(game.batch);

        for (Cubo c: creadorDeMundo.getCubos()) {
            c.draw(game.batch);
            c.update(delta);

        }
        game.batch.end();

        game.batch.setProjectionMatrix(info.stage.getCamera().combined);
        info.stage.draw();

        game.batch.setProjectionMatrix(controlTouch.stage.getCamera().combined);
        controlTouch.stage.draw();

        game.batch.setProjectionMatrix(settings.stage.getCamera().combined);
        settings.stage.draw();


        if(Puerta.isNextLevel()){
            //level = "1-3";
            //PreferencesClass.setLevelPreferences(3+"",level);
            game.setScreen(new Records(game,null,gatoElegido, info.getTiempoTotal()));
            Puerta.setNextLevel(false);
            music.stop();
            dispose();

        }
    }

    /**
     * Método que libera recursos.
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
        map.dispose();
        controlTouch.dispose();
        settings.dispose();
    }

    /**
     * Método que define si el protagonista ha muerto.
     * @return devuelve un booleano sobre el estado del gato.
     * @see JuegoScreen#gameOver()
     */
    @Override
    public boolean gameOver() {
        return super.gameOver();
    }
}
