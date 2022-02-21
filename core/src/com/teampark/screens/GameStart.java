package com.teampark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;

public class GameStart implements Screen {

    private final Stage stage;

    private final Game game;

    Image fondoImage;

    Music music;
    public GameStart(MainGame game){

        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        stage = new Stage(viewport, ((MainGame)game).batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        this.game = game;

        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);
        music.play();

        Table table = new Table();
        table.setFillParent(true);
        Label label = new Label("Team Park", font);

        fondoImage = new Image(new TextureRegion(new Texture("fondostart.png")));

        fondoImage.setScale(1f,1f);
        table.add(label).expandX().padLeft(100).padRight(50);
        table.add(fondoImage).expandX();

        table.row();
        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        float gyroZ = Gdx.input.getGyroscopeZ();
        if(Gdx.input.justTouched()){

            game.setScreen(new SetPersonaje((MainGame) game));
            dispose();
        }

        boolean gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        if(gyroscopeAvail){
            fondoImage.addAction(Actions.rotateTo(gyroZ));
        }

        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
