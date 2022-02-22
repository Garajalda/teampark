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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
 */
public class JuegoScreen implements Screen{

    protected final MainGame game;
    protected final InfoScreen info;
    protected final OrthographicCamera gameCamera;
    protected final Viewport gamePort;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected final Controllers controlTouch;
    protected World world;
    protected Box2DDebugRenderer b2dr;
    protected Cat cat;
    protected final TextureAtlas textureAtlas;
    protected Ascensor ascensor;
    protected Key key;
    protected SettingsPlay settings;
    Cat.TypeCat gato;
    String level;

    /**
     * Constructor que define el mapa, los elementos de la ventana.
     * @param game
     * @param gato
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
        Music music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);


        //music.play();

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

    @Override
    public void show() {

    }


    /**
     * Método que define la actualización y movimiento de los diferentes componentes dentro de la pantalla.
     * @param dt
     */
    //movimiento
    public void update(float dt) {

        handleInput(dt);

        world.step(1f / 60f, 6, 2);


        if (cat.estadoActual != Cat.State.DEAD) {
            gameCamera.position.x = cat.b2body.getPosition().x;
        }

        gameCamera.update();
        renderer.setView(gameCamera);


    }


    /**
     * Método que define los eventos de movimiento del personaje.
     * @param dt
     */
    //eventos de teclado para el personaje
    protected void handleInput(float dt) {

            boolean salta = false;
            boolean camina = false;
            final int fastAreaMin = Gdx.graphics.getWidth() / 2;


            for (int j = 0; j < 2; j++) {
                if (Gdx.input.isTouched(j)) {
                    final int iX = Gdx.input.getX(j);
                    camina = camina || (iX < fastAreaMin);
                    salta = salta || (iX > fastAreaMin);
                }
            }


            if (!cat.isDead() && !settings.tableVisible) {

                if (camina) {
                    if (controlTouch.isTouched()) {

                        if (cat.b2body.getLinearVelocity().x >= -0.8f && cat.b2body.getLinearVelocity().x <= 0.8f) {
                            cat.b2body.applyLinearImpulse(new Vector2(controlTouch.getKnobPercentX() * 0.05f, 0), cat.b2body.getWorldCenter(), true);
                            //game.fbic.writeCat(gato.ordinal(), cats.get(gato.ordinal()).b2body.getLinearVelocity().x, cats.get(gato.ordinal()).b2body.getLinearVelocity().x);

                        }
                    }
                }
                if (salta && !settings.isPressedSettings()) {
                    if (Gdx.input.justTouched()) {
                        if (!WorldContactListener.catNotTouch && cat.b2body.getLinearVelocity().y== 0 || WorldContactListener.catTouchAscensor)
                            cat.b2body.applyLinearImpulse(new Vector2(0, 2.5f), cat.b2body.getWorldCenter(), true);
                    }
                }
                //mover personaje PC pruebas
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    cat.b2body.applyLinearImpulse(new Vector2(0, 2.5f), cat.b2body.getWorldCenter(), true);

                //velocidad max q se puede permitir el gato
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && cat.b2body.getLinearVelocity().x <= 0.8f)
                    cat.b2body.applyLinearImpulse(new Vector2(0.05f, 0), cat.b2body.getWorldCenter(), true);

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && cat.b2body.getLinearVelocity().x >= -0.8f)
                    cat.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), cat.b2body.getWorldCenter(), true);
            }
        }




    /**
     * Renderiza la pantalla JuegoScreen.
     * @param delta
     */
    @Override
    public void render(float delta) {

        update(delta);


        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();


        b2dr.render(world, gameCamera.combined);


        if (gameOver()) {
            game.setScreen(new GameOverScreen(level,game, gato));
            this.dispose();
        }


    }


    /**
     * Reescala la pantalla dependiendo del ancho y el alto.
     * @param width
     * @param height
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

    public World getWorld() {
        return world;
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

        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
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
