package com.teampark.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.tools.PreferencesClass;

public class TableRecords implements Disposable {

    private Viewport viewport;
    public Stage stage;

    private BitmapFont font;
    MainGame game;
    private Screen screen;
    private Cat.TypeCat gatoElegido;
    public Table table1;

    public TableRecords(final MainGame game, final Screen screen, final Cat.TypeCat gatoElegido, final StringBuilder tiempoTotal, final SpriteBatch sb) {
        viewport = new FitViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);
        this.game = game;
        Gdx.input.setInputProcessor(MainGame.multiplexer);
        this.screen = screen;
        this.table1 = new Table();
        table1.setVisible(false);
        if(screen == null){

            final Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));

            final TextField records = new TextField("Nombre", skin,"default");

            TextureRegion txtAgregar = new TextureRegion(new Texture("controllers/UI_grey_buttons_light_pressed_1.png"),0,64,16,16);
            Sprite dR = new Sprite(txtAgregar);
            Image buttonAgregar = new Image(dR);


            table1.setSize(180,90);
            table1.background(skin.getDrawable("window"));
            table1.setPosition(113, 70);
            table1.row();
            table1.add(records).left();
            table1.add(buttonAgregar).right();
            table1.row();
            table1.setVisible(true);

            buttonAgregar.addListener(new InputListener(){

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println(records.getText());
                    PreferencesClass.setRecordPreferences(records.getText(),tiempoTotal);
                    table1.setVisible(false);

                    return super.touchDown(event, x, y, pointer, button);
                }
            });

            stage.addActor(table1);

            MainGame.multiplexer.addProcessor(stage);

        }
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
