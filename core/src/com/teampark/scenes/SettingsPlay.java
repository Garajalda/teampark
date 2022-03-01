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
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.screens.Creditos;
import com.teampark.screens.JuegoScreen;
import com.teampark.screens.Records;
import com.teampark.screens.SetLevel;
import com.teampark.tools.PreferencesClass;


/**
 * Esta clase define los ajustes dentro de la ventana de los niveles.
 * @see com.badlogic.gdx.Screen
 * @see Disposable implementa
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class SettingsPlay implements Disposable{

    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    public final Stage stage;


    /**
     * Método que define si se ha presionado el botón settings
     */
    private boolean pressedSettings;

    /**
     * Método que devuelve si se ha presionado el botón settings
     * @return pressedSettings
     */
    public boolean isPressedSettings() {
        return pressedSettings;
    }

    /**
     * Botón para habilitar o deshabilitar el sonido.
     * @see ImageTextButton
     */
    private final ImageTextButton sound;
    /**
     * Variable que define si esta el sonido activado o no
     */
    private boolean soundBoolean;


    /**
     * Variable que define si se activó la vista de los ajustes
     */
    private boolean tableVisible;

    /**
     * Método que devuelve si se activó la vista de los ajustes
     * @return
     */
    public boolean isTableVisible() { return tableVisible; }

    /**
     * Define la música del nivel 1
     * @see Music
     */
    private final Music musicplatform;
    /**
     * Define la música del nivel 2
     * @see Music
     */
    private final Music musicplatform2;
    /**
     * Define la música de inicio
     * @see Music
     */
    private final Music musicInicio;

    /**
     *
     * Constructor de clase que define los elementos de los ajustes y su actual estado
     * @param juegoScreen
     * @param level
     * @param game
     * @param sb
     * @param gatoElegido
     */
    public SettingsPlay(final JuegoScreen juegoScreen, final String level, final MainGame game, final SpriteBatch sb, final Cat.TypeCat gatoElegido){

        Viewport viewport = new FitViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);
        final Table table = new Table();
        table.setFillParent(true);
        table.right().top().padTop(2).padRight(2);


        musicplatform = MainGame.managerSongs.get("audio/music/MusicPlatform.mp3");
        musicplatform2 = MainGame.managerSongs.get("audio/music/MusicPlatform2.mp3");
        musicInicio = MainGame.managerSongs.get("audio/music/aeon.ogg");

        soundBoolean = PreferencesClass.getSoundPreferences("sound");

        Gdx.input.setInputProcessor(MainGame.multiplexer);
        //Image settingImg = new Image(new Texture("configuraciones.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("configuraciones.png")));
        final Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));

        final ImageButton btnSetting = new ImageButton(drawable);
        final ImageTextButton records = new ImageTextButton("Records       ", skin,"default");
        final ImageTextButton salir = new ImageTextButton("Salir                          ", skin,"default");
        final ImageTextButton creditos = new ImageTextButton("Ver creditos              ", skin,"default");
        sound = new ImageTextButton(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off",skin,"default");

        final Table table1 = new Table();
        table1.setSize(180,120);
        table1.background(skin.getDrawable("window"));
        table1.setPosition(113, 70);
        table1.row();
        table1.add(records).left();
        table1.row();

        if(juegoScreen != null){
            table1.add(salir).left();
            table1.row();
        }
        table1.add(creditos).left();

        table1.row();
        table1.add(sound).left();
        table1.setVisible(false);
        stage.addActor(table1);

        salir.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //table1.setVisible(false);

                game.setScreen(new SetLevel(game,gatoElegido));
                musicplatform.stop();
                musicplatform2.stop();

                if (PreferencesClass.getSoundPreferences("sound")) {
                    musicInicio.play();
                } else {
                    musicInicio.stop();
                }

                assert juegoScreen != null;
                juegoScreen.dispose();

                return true;
            }
        });

        btnSetting.setSize(16,16);
        btnSetting.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tableVisible = !tableVisible;

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
                soundBoolean = !soundBoolean;
                PreferencesClass.setSoundPreferences("sound",soundBoolean);
                sound.setText(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off");
                Music mu;
                if(level.equals("1-1")){
                    mu = musicplatform;
                }else if(level.equals("1-2")){
                    mu = musicplatform2;
                }else{
                    mu = musicInicio;
                }
                if (PreferencesClass.getSoundPreferences("sound")) {
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

        creditos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Creditos(game,juegoScreen,gatoElegido));
                assert juegoScreen != null;
                juegoScreen.dispose();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        records.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Records(game,juegoScreen,gatoElegido,new StringBuilder("")));
                assert juegoScreen != null;
                juegoScreen.dispose();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add(btnSetting).size(btnSetting.getWidth(),btnSetting.getHeight());
        stage.addActor(table);

        MainGame.multiplexer.addProcessor(stage);

    }

    /**
     * Método que permite liberar recursos
     * @see Disposable#dispose()
     */
    @Override
    public void dispose() {

        stage.dispose();

    }


}
