package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.tools.PreferencesClass;

import java.util.ArrayList;

/**
 * Esta clase es la pantalla para selecionar el nivel.
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0, 2022/02/17
 */
public class SetLevel implements Screen {

    /**
     * Define la raíz del juego donde se cargan los assets.
     * @see MainGame
     */
    MainGame game;

    /**
     * Define el tipo del gato actual
     * @see Cat
     */
    Cat.TypeCat gatoElegido;

    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    private final Stage stage;


    /**
     * Define la musica que esta sonando
     */
    private final Music music;

    /**
     * Nivel actual del juego.
     */
    private String level;

    /**
     * Constructor que inicializa el selector de niveles con el gato elegido y el nivel
     * @param game
     * @param gatoElegido
     */
    public SetLevel(final MainGame game, final Cat.TypeCat gatoElegido){
        this.game = game;
        this.gatoElegido = gatoElegido;
        this.level = "1-1";

        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);

        Gdx.input.setInputProcessor(MainGame.multiplexer);

        PreferencesClass.setLevelPreferences(1+"",level);
        System.out.println(PreferencesClass.getCountLevels());

        System.out.println(PreferencesClass.getLevelPreferences(1+""));
        Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));
        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);
        this.stage = new Stage(viewport, ((MainGame)game).batch);


        TextureRegion buttonSalirT = new TextureRegion(new Texture("controllers/UI_orange_buttons_pressed_3.png"),16,48,16,16);
        Sprite dR = new Sprite(buttonSalirT);
        Image buttonSalir = new Image(dR);

        Table table = new Table();
        table.debug();
        table.setFillParent(true);
        table.top();

        ImageTextButton label = new ImageTextButton("Elige el nivel", skin,"default");

        table.row().expandX().center();
        table.add(label).center();
        table.add(buttonSalir).right();
        stage.addActor(table);

        ArrayList<ImageButton> buttons = new ArrayList<>();

        float espacio = 0;
        float espacioTexto = 0;
        for (int i = 1; i < 3; i++) {
            Texture newLevelTexture = new Texture("controllers/level.png");
            final SpriteDrawable startGame = new SpriteDrawable(new Sprite(newLevelTexture));
            ImageButton button = new ImageButton(startGame);
            Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label nivel;
            if(i <= PreferencesClass.getCountLevels()){
                nivel = new Label(i+"", font);
            }else{
                nivel = new Label("?",font);
            }

            nivel.setPosition(122+ espacioTexto,110);
            button.setSize(50 + espacio,50);
            button.setPosition(100 + espacio,94);
            stage.addActor(button);
            stage.addActor(nivel);
            buttons.add(button);

            espacio+=40;
            espacioTexto+=9.5;
            espacioTexto+=espacio+espacioTexto;

        }

        buttons.get(0).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                level = "1-1";

                game.setScreen(new Level1(level,game, gatoElegido));
                music.stop();
                dispose();

                return true;
            }
        });
        System.out.println(PreferencesClass.getCountLevels());
        if(PreferencesClass.getCountLevels() == 2){
            buttons.get(1).addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    level = "1-2";
                    game.setScreen(new Level2(level,game,gatoElegido));

                    music.stop();
                    dispose();
                    return true;
                }
            });
        }

        buttonSalir.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SetPersonaje(game));
                dispose();
                return super.touchDown(event, x, y, pointer, button);
            }
        });


    }

    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }

    /**
     * Método que inicia el renderizado.
     * @param delta
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainGame.multiplexer.addProcessor(stage);
        stage.draw();
    }

    /**
     * @see Screen#resize(int, int)
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see Screen#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see Screen#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * @see Screen#hide()
     */
    @Override
    public void hide() {

    }

    /**
     * Método que libera recursos de la escena.
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
