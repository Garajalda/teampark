package com.teampark.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
import com.teampark.tools.CreadorDeMundo;
import com.teampark.tools.WorldContactListener;

import java.util.ArrayList;


public class JuegoScreen implements Screen{

    private final MainGame game;
    private final InfoScreen info;
    private final OrthographicCamera gameCamera;
    private final Viewport gamePort;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Controllers controlTouch;
    private World world;
    private final Box2DDebugRenderer b2dr;
    private Cat cat;

    private final TextureAtlas textureAtlas;
    private final Ascensor ascensor;
    private final Key key;
    private final SettingsPlay settings;
    Cat.TypeCat gato;
    ArrayList<Cat> cats;

    public JuegoScreen(MainGame game, Cat.TypeCat gato) {
        textureAtlas = new TextureAtlas("Cats.pack");
        world = new World(new Vector2(0, -9.8f), true);
        //System.out.println(gato.ordinal());
        cats = new ArrayList<>();
        this.game = game;
        this.gato = gato;
        gameCamera = new OrthographicCamera();
        //mantiene la relación de aspecto.
        gamePort = new FitViewport((float) MainGame.VIEW_WIDTH / MainGame.PPM, (float) MainGame.VIEW_HEIGHT / MainGame.PPM, gameCamera);

        //info
        info = new InfoScreen(game.batch, "1-1");
        controlTouch = new Controllers(game.batch);
        settings = new SettingsPlay(game.batch);

        //cargando mapa
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");

        //unidades de escala
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / MainGame.PPM);
        //posicion de camara con relacion y aspecto del mundo
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //debug
        b2dr = new Box2DDebugRenderer();
        //creando los objetos del mundo
        new CreadorDeMundo(this);
        this.cat = new Cat(this,gato,50,72);

        //cats.add(new Cat(this,gato,50,72));


        //ascensor
        ascensor = new Ascensor(this,9.8f, (float) 100 / MainGame.PPM);
        ascensor.body.setActive(false);

        //crear key
        key = new Key(this,9.3f, (float) 160 / MainGame.PPM);
        //tipo contacto
        world.setContactListener(new WorldContactListener());
        Music music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);


        //music.play();


    }

    public boolean gameOver() {
        return cat.estadoActual == Cat.State.DEAD && cat.getStateTimer() > 1;
    }

    //Obtengo las texturas
    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    @Override
    public void show() {

    }

    int cont = 0;

    public void comprobarGatos(float dt) {

        for (Integer key : game.fbic.getCodRecogido().keySet()) {
            if (cont < cats.size()) {
                cats.add(new Cat(this, key == 0 ? Cat.TypeCat.BLACK : Cat.TypeCat.BROWN, 50, 70));
            } else if (key != gato.ordinal()) {
                Vector2 vectorGato = game.fbic.getCodRecogido().get(key);
                cats.get(key).setPosition(vectorGato.x, vectorGato.y);
                cats.get(key).update(dt);
                System.out.println("posicion actualizada");
            }
        }
            cont++;

        System.out.println("hola");


    }
    //movimiento
    public void update(float dt) {
        handleInput(dt);
        world.step(1f / 60f, 6, 2);



        //MainGame.fbic.updateDatos(gato,cat.b2body.getPosition().x, cat.b2body.getPosition().y);
        //MainGame.fbic.transacction(gato,cat.b2body.getPosition().x,cat.b2body.getPosition().y);
       // comprobarGatos(dt);
       // MainGame.fbic.transacction(gato,cats.get(gato.ordinal()).b2body.getPosition().x,cats.get(gato.ordinal()).b2body.getPosition().y);

        ascensor.update(dt);
        //creando catBlack

        key.update(dt,cat);
        System.out.println();

        cat.update(dt);



        if (cat.estadoActual != Cat.State.DEAD) {

            gameCamera.position.x = cat.b2body.getPosition().x;


        }

        gameCamera.update();
        renderer.setView(gameCamera);
    }


    //eventos de teclado para el personaje
    private void handleInput(float dt) {
        Gdx.input.setInputProcessor(MainGame.multiplexer);
        boolean salta = false;
        boolean camina = false;
        final int fastAreaMin = Gdx.graphics.getWidth() / 2;


        for (int j = 0; j < 20; j++) {
            if (Gdx.input.isTouched(j)) {
                final int iX = Gdx.input.getX(j);
                camina = camina || (iX < fastAreaMin);
                salta = salta || (iX > fastAreaMin);
            }
        }
        MainGame.multiplexer.addProcessor(settings.stage);


        if (!cat.isDead()) {

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
                    if (!WorldContactListener.catNotTouch || cat.b2body.getLinearVelocity().y== 0)
                        cat.b2body.applyLinearImpulse(new Vector2(0, 2.5f), cat.b2body.getWorldCenter(), true);
                }
            }
            //mover personaje PC pruebas
            /*if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                cat.b2body.applyLinearImpulse(new Vector2(0, 2.5f), cat.b2body.getWorldCenter(), true);

            //velocidad max q se puede permitir el gato
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && cat.b2body.getLinearVelocity().x <= 0.8f)
                cat.b2body.applyLinearImpulse(new Vector2(0.05f, 0), cat.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && cat.b2body.getLinearVelocity().x >= -0.8f)
                cat.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), cat.b2body.getWorldCenter(), true);*/
        }

       // }
    }


    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //MainGame.fbic.transacction(gato,cats.get(gato.ordinal()).b2body.getPosition().x,cats.get(gato.ordinal()).b2body.getPosition().y);


       // comprobarGatos(delta);

        //box2ddDebug
        b2dr.render(world, gameCamera.combined);

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();

        cat.draw(game.batch);


        ascensor.draw(game.batch);
        key.draw(game.batch);
        game.batch.end();

        //matriz de proyeccion
        game.batch.setProjectionMatrix(info.stage.getCamera().combined);
        info.stage.draw();

        game.batch.setProjectionMatrix(settings.stage.getCamera().combined);
        settings.stage.draw();


        game.batch.setProjectionMatrix(controlTouch.stage.getCamera().combined);
        controlTouch.stage.draw();



        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, gato));
            dispose();
        }
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

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
        settings.dispose();
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
