package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.teampark.MainGame;
import com.teampark.Sprites.Puerta;
import com.teampark.Sprites.cats.Cat;
import com.teampark.items.Ascensor;
import com.teampark.items.Key;
import com.teampark.tools.CreadorDeMundo;
import com.teampark.tools.PreferencesClass;
import com.teampark.tools.WorldContactListener;

public class Level1 extends JuegoScreen{
    /**
     * Constructor que define el mapa, los elementos de la ventana.
     *
     * @param level
     * @param game
     * @param gato
     */
   Music music;
    public Level1(String level, MainGame game, Cat.TypeCat gato) {
        super(level, game, gato);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");



        this.music = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3");
        music.setLooping(true);
        if(PreferencesClass.getSoundPreferences("sound")){
            music.play();
        }

        //unidades de escala
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / MainGame.PPM);
        //posicion de camara con relacion y aspecto del mundo
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        //debug
        b2dr = new Box2DDebugRenderer();
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



    @Override
    public void render(float delta) {

        super.render(delta);

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();
        b2dr.render(world, gameCamera.combined);
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


        if(Puerta.isNextLevel()){
            level = "1-2";
            PreferencesClass.setLevelPreferences(2+"",level);

            game.setScreen(new Level2(level,game,gato));
            Puerta.setNextLevel(false);
            music.stop();
            dispose();

        }

    }

    @Override
    protected void handleInput(float dt) {
        super.handleInput(dt);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        ascensor.update(dt);
        //creando catBlack

        key.update(dt,cat);
        cat.update(dt);


    }

    /**
     * Método que devuelve la pantalla cargada
     * @return TiledMap
     */



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
        controlTouch.dispose();
        settings.dispose();
    }


    @Override
    public boolean gameOver() {
        return super.gameOver();
    }
}
