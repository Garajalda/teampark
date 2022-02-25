package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.tools.CreadorDeMundo;
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
    public Level2(String level, MainGame game, Cat.TypeCat gato) {
        super(level, game, gato);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl2.tmx");

        this.music  = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
        music.setLooping(true);
        music.play();


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
        cat.update(dt);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.6f, 0.7f, 0.7f, 0.0f);
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
        game.batch.end();

        game.batch.setProjectionMatrix(settings.stage.getCamera().combined);
        settings.stage.draw();


        if (gameOver()) {
            game.setScreen(new GameOverScreen(level,game, gato));
            dispose();
        }
    }

    @Override
    public void dispose() {
        map.dispose();

        controlTouch.dispose();
        settings.dispose();
    }

}
