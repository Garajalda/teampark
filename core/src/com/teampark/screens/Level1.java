package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.teampark.MainGame;
import com.teampark.Sprites.Puerta;
import com.teampark.Sprites.cats.Cat;
import com.teampark.items.Ascensor;
import com.teampark.items.Key;
import com.teampark.tools.CreadorDeMundo;
import com.teampark.tools.PreferencesClass;
import com.teampark.tools.WorldContactListener;

/**
 * Clase que define el nivel 1 de la aplicación
 * @see Screen
 * @see JuegoScreen hereda
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Level1 extends JuegoScreen{

    /**
     * Inicia la música del nivel
     */
    private final Music music;
    /**
     * Contador del delta
     */
    private float timeCount;
    /**
     * Contador de segundos
     */
    private Integer countSegundos;


    /**
     * Constructor que define el mapa, los elementos de la ventana.
     * @param level
     * @param game
     * @param gato
     */
    public Level1(String level, MainGame game, Cat.TypeCat gato) {
        super(level, game, gato);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");
        this.music = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3");
        music.setLooping(true);

        countSegundos = 0;

        if(PreferencesClass.getSoundPreferences("sound")){
            music.play();
        }

        //unidades de escala
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / MainGame.PPM);
        //posicion de camara con relacion y aspecto del mundo
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //creando los objetos del mundo
        new CreadorDeMundo(this);

        //tipo contacto
        world.setContactListener(new WorldContactListener());

        cat = new Cat(this, gato,50,40);
        //ascensor
        ascensor = new Ascensor(this,9.8f, (float) 100 / MainGame.PPM);
        ascensor.body.setActive(false);
        //crear key
        key = new Key(this,9.3f, (float) 160 / MainGame.PPM);

    }

    /**
     * Renderiza la pantalla del nivel1.
     * @param delta
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        super.render(delta);



        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();

        //matriz de proyeccion
        game.batch.setProjectionMatrix(info.stage.getCamera().combined);
        info.stage.draw();


        game.batch.setProjectionMatrix(controlTouch.stage.getCamera().combined);
        controlTouch.stage.draw();

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        cat.draw(game.batch);
        ascensor.draw(game.batch);
        key.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(settings.stage.getCamera().combined);
        settings.stage.draw();

        timeCount += delta;

        if(timeCount >= 1){
            if(countSegundos <= 2 && Puerta.isNextLevel()){
                level = "1-2";
                PreferencesClass.setLevelPreferences(2+"",level);
                Puerta.setNextLevel(false);
                music.stop();
                game.setScreen(new Level2(level,game,gato));
                dispose();
                countSegundos++;
            }
            timeCount = 0;
        }

    }

    /**
     * Método que define los eventos de movimiento del personaje.
     * @param dt
     * @see JuegoScreen#handleInput(float)
     */
    @Override
    protected void handleInput(float dt) {
        super.handleInput(dt);
    }

    /**
     * Reescala la pantalla dependiendo del ancho y el alto.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * Método que define la actualización y movimiento de los diferentes componentes dentro de la pantalla.
     * @param dt
     * @see JuegoScreen#update(float)
     */
    @Override
    public void update(float dt) {
        super.update(dt);

        ascensor.update(dt);
        key.update(dt,cat);
        cat.update(dt);

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
