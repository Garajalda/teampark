package com.teampark.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;

public class SettingsPlay implements Disposable {

    public Stage stage;


    public Viewport viewport;


    public boolean isPressedSettings() {
        return pressedSettings;
    }

    boolean pressedSettings;
    public SettingsPlay(final SpriteBatch sb){
        viewport = new FitViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);

        final Table table = new Table();
        table.setFillParent(true);
        table.right().top().padTop(2).padRight(2);

        //Image settingImg = new Image(new Texture("configuraciones.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("configuraciones.png")));
        final Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));

        final ImageButton btnSetting = new ImageButton(drawable);
        final ImageTextButton text = new ImageTextButton("Guardar partida        ", skin,"default");
        final ImageTextButton text2 = new ImageTextButton("Salir                          ", skin,"default");
        final ImageTextButton text3 = new ImageTextButton("Ver creditos              ", skin,"default");


        final Table table1 = new Table();
        table1.setSize(180,100);
        table1.background(skin.getDrawable("window"));
        table1.setPosition(113, 70);
        table1.row();
        table1.add(text).left();
        table1.row();
        table1.add(text2).left();
        table1.row();
        table1.add(text3).left();
        table1.setVisible(false);
        stage.addActor(table1);


        text2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                table1.setVisible(false);
                return true;
            }
        });

        btnSetting.setSize(16,16);
        btnSetting.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                table1.setVisible(true);
                pressedSettings = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressedSettings = false;

            }
        });

        table.add(btnSetting).size(btnSetting.getWidth(),btnSetting.getHeight());
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
