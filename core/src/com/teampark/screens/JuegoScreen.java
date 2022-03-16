package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.items.Ascensor;
import com.teampark.items.Key;
import com.teampark.scenes.Controllers;
import com.teampark.scenes.InfoScreen;
import com.teampark.scenes.SettingsPlay;
import com.teampark.tools.WorldContactListener;


/**
 * Clase que define la pantalla del juego, carga los mapas, los personajes, los niveles...
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class JuegoScreen implements Screen{

    /**
     * Define la raíz del juego donde se cargan los assets.
     * @see MainGame
     */
    protected final MainGame game;

    /**
     * Define la información del nivel y del tiempo que se lleva jugado.
     * @see InfoScreen
     */
    protected final InfoScreen info;

    /**
     * Define la proyeccion de la camara.
     * @see OrthographicCamera
     */
    protected final OrthographicCamera gameCamera;

    /**
     * Define la resolución de la pantalla
     * @see Viewport
     */
    protected final Viewport gamePort;

    /**
     * Carga el mapa del nivel
     * @see TiledMap
     */
    protected TiledMap map;

    /**
     * Renderiza el mapa
     * @see OrthogonalTiledMapRenderer
     */
    protected OrthogonalTiledMapRenderer renderer;

    /**
     * Inicia los controles del juego
     * @see Controllers
     */
    protected final Controllers controlTouch;

    /**
     * Carga el mundo
     * @see World
     */
    protected World world;

    /**
     * Personaje principal del mundo
     * @see Cat
     */
    protected Cat cat;

    /**
     * Carga imágenes de una textura del atlas
     * @see TextureAtlas
     */
    protected final TextureAtlas textureAtlas;

    /**
     * Elemento del juego interactivo
     * @see Ascensor
     */
    protected Ascensor ascensor;

    /**
     * Elemento del juego que carga una llave para acceder a otro nivel
     * @see Key
     */
    protected Key key;

    /**
     * Carga el botón de ajustes que aparece en la parte superior de la pantalla del juego.
     * @see SettingsPlay
     */
    protected SettingsPlay settings;

    /**
     * Define el tipo del gato actual
     * @see Cat
     */
    Cat.TypeCat gato;

    /**
     * Define el nivel actual del juego
     */
    String level;

    /**
     * Constructor que define el mapa, los elementos de la ventana.
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     * @param gato Obtiene el tipo de gato actual.
     * @param level Obitene el nivel actual.
     */
    public JuegoScreen(String level, MainGame game, Cat.TypeCat gato) {
        textureAtlas = new TextureAtlas("Cats.pack");
        world = new World(new Vector2(0, -9.8f), true);
        this.level = level;

        this.game = game;
        this.gato = gato;
        gameCamera = new OrthographicCamera();
        //mantiene la relación de aspecto.
        gamePort = new FitViewport((float) MainGame.VIEW_WIDTH / MainGame.PPM, (float) MainGame.VIEW_HEIGHT / MainGame.PPM, gameCamera);

        //info
        info = new InfoScreen(game.batch, level);
        controlTouch = new Controllers(game.batch);
        settings = new SettingsPlay(this,level,game,game.batch,gato);

        //tipo contacto
        world.setContactListener(new WorldContactListener());

    }


    /**
     * Método que define si el protagonista ha muerto.
     * @return devuelve un booleano sobre el estado del gato.
     */
    public boolean gameOver() {
        return cat.estadoActual == Cat.State.DEAD && cat.getStateTimer() > 1;
    }

    /**
     * Método qye devuelve las texturas que cargan al personaje.
     * @return devuelve el textureAtlas del gato.
     */
    //Obtengo las texturas
    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }


    /**
     * Método que define la actualización y movimiento de los diferentes componentes dentro de la pantalla.
     * @param dt Parámetro que indica el delta.
     */
    public void update(float dt) {

        handleInput(dt);
        world.step(1f / 60f, 6, 2);

        info.update(dt);

        if (cat.estadoActual != Cat.State.DEAD) {
            gameCamera.position.x = cat.body.getPosition().x;
        }

        gameCamera.update();
        renderer.setView(gameCamera);
    }


    /**
     * Método que define los eventos de movimiento del personaje.
     * @param dt Parámetro que indica el delta.
     */
    protected void handleInput(float dt) {
            boolean salta = false;
            boolean camina = false;
            final float fastAreaMin = Gdx.graphics.getWidth() / 2;

            for (int j = 0; j < 20; j++) {
                if (Gdx.input.isTouched(j)) {
                    final float iX = Gdx.input.getX(j);
                    camina = camina || (iX < fastAreaMin);
                    salta = salta || (iX > fastAreaMin);
                }
            }

            if (!cat.isDead() && !settings.isTableVisible()) {
                if (camina) {
                    if (cat.body.getLinearVelocity().x >= -0.8f && cat.body.getLinearVelocity().x <= 0.8f) {
                        cat.body.applyLinearImpulse(new Vector2(controlTouch.getKnobPercentX() * 0.05f, 0), cat.body.getWorldCenter(), true);
                    }
                }
                if (salta && !settings.isPressedSettings()) {

                    if(Gdx.input.justTouched()){
                        if ((!WorldContactListener.catNotTouch || cat.body.getLinearVelocity().y== 0)){
                            cat.body.applyLinearImpulse(new Vector2(0, 2.8f), cat.body.getWorldCenter(), true);
                        }
                    }
                }
                //mover personaje PC pruebas
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    cat.body.applyLinearImpulse(new Vector2(0, 2.5f), cat.body.getWorldCenter(), true);

                //velocidad max q se puede permitir el gato
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && cat.body.getLinearVelocity().x <= 0.8f)
                    cat.body.applyLinearImpulse(new Vector2(0.05f, 0), cat.body.getWorldCenter(), true);

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && cat.body.getLinearVelocity().x >= -0.8f)
                    cat.body.applyLinearImpulse(new Vector2(-0.05f, 0), cat.body.getWorldCenter(), true);
            }
        }


    /**
     * Renderiza la pantalla JuegoScreen.
     * @param delta Parámetro que indica el delta.
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        if (gameOver()) {
            Music music = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3", Music.class);
            music.setLooping(true);
            music.play();
            game.setScreen(new GameOverScreen(level,game, gato));
            this.dispose();
        }

    }


    /**
     * Reescala la pantalla dependiendo del ancho y el alto.
     * @param width Indica el ancho de pantalla.
     * @param height Indica el alto de pantalla.
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Método que devuelve la pantalla cargada
     * @return TiledMap
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Método que devuelve el mundo común a todos los niveles
     * @return Un objeto de tipo mundo con esas características.
     */
    public World getWorld() {
        return world;
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
        renderer.dispose();
        world.dispose();
        info.dispose();
        controlTouch.dispose();
    }

}

/*
 *
 * touched
 *   if (Gdx.input.isTouched() ){
 *   gameCamera.position.x +=100*dt/MainGame.PPM;
 *   if(Gdx.input.getX() <= Gdx.graphics.getWidth()/2){
 *       if(MainGame.VIEW_WIDTH/2 >= gameCamera.position.x){
 *           gameCamera.position.x += 0;
 *       }else{
 *       }
 *   }else{
 *   if(gameCamera.position.x >= mapWidthInPixels-MainGame.VIEW_WIDTH/2){
 *       gameCamera.position.x +=0 ;
 *       }else{
 *           gameCamera.position.x +=100*dt;
 *       }
 *   }
 * System.out.println(gameCamera.position.x+", "+MainGame.VIEW_WIDTH*3+", "+mapWidthInPixels);
 * */
