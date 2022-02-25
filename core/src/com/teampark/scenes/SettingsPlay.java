package com.teampark.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import com.teampark.Sprites.cats.Cat;
import com.teampark.screens.JuegoScreen;
import com.teampark.screens.SetLevel;
import com.teampark.tools.PreferencesClass;

public class SettingsPlay implements Disposable{

    public Stage stage;
    public Viewport viewport;

    MainGame game;
    public boolean isPressedSettings() {
        return pressedSettings;
    }
    Cat.TypeCat gatoElegido;
    boolean pressedSettings;
    String level;
    JuegoScreen juegoScreen;
    Music music;
    final ImageTextButton sound;
    boolean soundBoolean;
    public boolean tableVisible;
    Music musicplatform;
    Music musicplatform2;
    public SettingsPlay(final JuegoScreen juegoScreen, final String level, final MainGame game, final SpriteBatch sb, final Cat.TypeCat gatoElegido){

        viewport = new FitViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);
        this.level = level;
        this.gatoElegido = gatoElegido;
        this.game = game;
        this.juegoScreen = juegoScreen;
        final Table table = new Table();
        table.setFillParent(true);
        table.right().top().padTop(2).padRight(2);


        musicplatform = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3");
        musicplatform2 = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
        soundBoolean = PreferencesClass.getSoundPreferences("sound");
        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);




        Gdx.input.setInputProcessor(MainGame.multiplexer);
        //Image settingImg = new Image(new Texture("configuraciones.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("configuraciones.png")));
        final Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));

        final ImageButton btnSetting = new ImageButton(drawable);
        final ImageTextButton text = new ImageTextButton("Records       ", skin,"default");
        final ImageTextButton text2 = new ImageTextButton("Salir                          ", skin,"default");
        final ImageTextButton text3 = new ImageTextButton("Ver creditos              ", skin,"default");
        sound = new ImageTextButton(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off",skin,"default");


        final Table table1 = new Table();
        table1.setSize(180,120);
        table1.background(skin.getDrawable("window"));
        table1.setPosition(113, 70);
        table1.row();
        table1.add(text).left();
        table1.row();
        table1.add(text2).left();
        table1.row();
        table1.add(text3).left();
        table1.row();
        table1.add(sound).left();
        table1.setVisible(false);
        stage.addActor(table1);



        text2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //table1.setVisible(false);

                game.setScreen(new SetLevel(game,gatoElegido));
                Music musicplatform = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3");
                musicplatform.stop();
                Music musicplatform2 = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
                musicplatform2.stop();

                music.play();

                juegoScreen.dispose();
                dispose();
                return true;
            }
        });

        btnSetting.setSize(16,16);
        btnSetting.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tableVisible = tableVisible ? false : true;

                table1.setVisible(tableVisible);
                pressedSettings = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                pressedSettings = false;

            }
        });


        sound.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundBoolean = soundBoolean ? false : true;
                PreferencesClass.setSoundPreferences("sound",soundBoolean);
                sound.setText(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off");
                boolean preferencesSound = PreferencesClass.getSoundPreferences("sound");

                Music mu;
                if(level == "1-1"){
                    mu = musicplatform;
                }else{
                    mu = musicplatform2;
                }
                if (preferencesSound) {
                    mu.play();
                } else {
                    mu.stop();
                }


                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        table.add(btnSetting).size(btnSetting.getWidth(),btnSetting.getHeight());
        stage.addActor(table);

        MainGame.multiplexer.addProcessor(stage);

    }

    @Override
    public void dispose() {

        stage.dispose();

    }


}
