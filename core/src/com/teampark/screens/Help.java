package com.teampark.screens;

import com.badlogic.gdx.Game;
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

public class Help implements Screen {

    private final Stage stage;
    final Game game;
    Sprite tutoCaminar;
    Sprite tutoSalto;
    Music music;
    private float timeCount;

    SpriteBatch batch;
    private Array<Sprite> tutoriales;

    public Help(final MainGame game){

        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        this.batch = ((MainGame)game).batch;
        this.game = game;

        tutoriales = new Array<>();
        timeCount = 0;
        stage = new Stage(viewport, batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
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
        table.top();
        table.setFillParent(true);
        Label label = new Label("Ayuda", font);



        tutoCaminar = new Sprite(new TextureRegion(new Texture("tutorial/pictureTutorialCaminar.png")));
        tutoCaminar.setSize(300,150);
        tutoCaminar.setPosition(50,30);


        tutoSalto = new Sprite(new TextureRegion(new Texture("tutorial/pictureTutoSalto.png")));
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

    @Override
    public void show() {

    }

    int cont = 0;
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

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
