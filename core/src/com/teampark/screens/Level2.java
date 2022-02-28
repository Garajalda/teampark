package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.teampark.MainGame;
import com.teampark.Sprites.Cubo;
import com.teampark.Sprites.Puerta;
import com.teampark.Sprites.cats.Cat;
import com.teampark.items.Key;
import com.teampark.tools.CreadorDeMundo;
import com.teampark.tools.PreferencesClass;
import com.teampark.tools.WorldContactListener;

public class Level2 extends JuegoScreen{
    /**
     * Constructor que define el mapa, los elementos de la ventana.
     *
     * @param level
     * @param game
     * @param gato
     */
    Music music;
    CreadorDeMundo creadorDeMundo;
    Cat.TypeCat gatoElegido;
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

        //debug
        b2dr = new Box2DDebugRenderer();
        //creando los objetos del mundo
        creadorDeMundo = new CreadorDeMundo(this);


        //tipo contacto
        world.setContactListener(new WorldContactListener());

        cat = new Cat(this, gatoElegido,50,40);
        key = new Key(this,12.8f, (float) 160 / MainGame.PPM);

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
        cat.update(dt); key.update(dt,cat);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.6f, 0.7f, 0.7f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gameCamera.combined);
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
