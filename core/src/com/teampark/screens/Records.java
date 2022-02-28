package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.scenes.TableRecords;
import com.teampark.tools.PreferencesClass;

public class Records implements Screen {

    private Viewport viewport;
    private Stage stage;

    private BitmapFont font;
    private MainGame game;
    private Screen screen;
    private Cat.TypeCat gatoElegido;
    private StringBuilder tiempoTotal;
    private TableRecords tableRecords;
    private Table table;
    private Label labelRecords;
    private Label.LabelStyle labelStyle;

    public Records(final MainGame game,final Screen screen){
        this.game = game;
        this.screen = screen;
        this.viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        Gdx.input.setInputProcessor(MainGame.multiplexer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/dogicapixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 10;
        font = generator.generateFont(params);

        stage = new Stage(viewport, ((MainGame)game).batch);

        this.labelStyle = new Label.LabelStyle(font, Color.WHITE);
        this.labelRecords = new Label("Records",labelStyle);

        this.table = new Table();

        table.setFillParent(true);
        table.top().padTop(15);


        table.row().center().expandX();
        table.add(labelRecords).center().expandX();
        table.row().expandX().center();
        for (String k: PreferencesClass.getRecordPreferences().keySet()) {
            table.row().expandX().padTop(15).center();
            table.add(new Label(k,labelStyle)).center();
            table.add(new Label(PreferencesClass.getRecordPreferences().get(k).toString(),labelStyle)).center();
            table.row().expandX().center();

        }

        Onclick();
        stage.addActor(table);
    }

    public Records(final MainGame game, final Screen screen, final Cat.TypeCat gatoElegido, StringBuilder tiempoTotal){
        this.game = game;
        this.screen = screen;
        this.tiempoTotal = tiempoTotal;

        this.gatoElegido = gatoElegido;
        this.viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);


        Gdx.input.setInputProcessor(MainGame.multiplexer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/dogicapixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 10;
        font = generator.generateFont(params);

        stage = new Stage(viewport, ((MainGame)game).batch);

        this.labelStyle = new Label.LabelStyle(font, Color.WHITE);
        this.labelRecords = new Label("Records",labelStyle);

        this.table = new Table();

        table.setFillParent(true);
        table.top().padTop(15);


        table.row().center().expandX();
        table.add(labelRecords).center().expandX();
        table.row().expandX().center();
        for (String k: PreferencesClass.getRecordPreferences().keySet()) {
            table.row().expandX().padTop(15).center();
            table.add(new Label(k,labelStyle)).center();
            table.add(new Label(PreferencesClass.getRecordPreferences().get(k).toString(),labelStyle)).center();
            table.row().expandX().center();

        }

        Onclick();

        stage.addActor(table);


    }

    public void Onclick(){
        this.tableRecords = new TableRecords(game,screen,gatoElegido,tiempoTotal, game.batch);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(!tableRecords.table1.isVisible()){
                    if (screen instanceof Level2){
                        game.setScreen(new Level2("1-2",game,gatoElegido));
                        dispose();

                    }
                    if(screen instanceof Level1){
                        game.setScreen(new Level1("1-1",game,gatoElegido));
                        dispose();
                    }

                    if(screen instanceof SetPersonaje){
                        game.setScreen(new SetPersonaje(game));
                        dispose();
                    }
                    if(screen == null){
                        game.setScreen(new SetLevel(game,gatoElegido));
                        dispose();
                    }
                    if(screen instanceof SetLevel){
                        game.setScreen(new SetLevel(game,gatoElegido));
                        dispose();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainGame.multiplexer.addProcessor(stage);
        stage.draw();
        tableRecords.stage.draw();
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
        tableRecords.stage.dispose();
    }
}
