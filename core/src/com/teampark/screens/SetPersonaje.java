package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.tools.PreferencesClass;

import java.util.ArrayList;


/**
 * Esta clase define la elección por parte del usuario sobre el personaje.
 * @see Screen
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class SetPersonaje implements Screen{

    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    private final Stage stage;

    /**
     * Define la raíz del juego donde se cargan los assets.
     * @see MainGame
     */
    private final MainGame game;

    /**
     * Lista de Sprites de gatos.
     * @see Sprite
     */
    private final Array<Sprite> catImages;

    /**
     * Lista de tipos de gato
     * @see Cat
     */
    public static ArrayList<Cat.TypeCat> map = new ArrayList<>();

    /**
     * Tipo de gato actualmente elegido
     * @see Cat
     */
    public Cat.TypeCat gatoElegido;

    /**
     * Musica de inicio
     * @see Music
     */
    private final Music music;

    /**
     * Sprite de gato
     * @see Sprite
     */
    private Sprite cat;

    /**
     * Contador que se incrementa cada vez que se pulsa en el botón de selección para elegir entre los diferentes gatos
     */
    private int i = 0;

    /**
     * Define si esta activada la música
     */
    private boolean soundBoolean;

    /**
     * Define si esta activada la vibración
     */
    private boolean vibratorBoolean;

    /**
     * Botón para activar o desactivar la música
     * @see ImageTextButton
     */
    private final ImageTextButton sound;

    private final ImageTextButton vibrator;

    /**
     * Método que define el personaje que ha elegido el ususario.
     * @param tipoGato Obtiene el tipo de gato actual.
     * @return el sprite del tipo de gato.
     */
    public Sprite tipoGato(Cat.TypeCat tipoGato){

        TextureAtlas textureAtlas = new TextureAtlas("Cats.pack");
        TextureAtlas.AtlasRegion getTexture = textureAtlas.findRegion("cat_"+tipoGato+"-32x48");

        TextureRegion catTextureRegion = new TextureRegion(getTexture, 32, 48, 32, 48);
        Sprite dr = new Sprite(catTextureRegion);
        dr.setSize(100,100);
        return dr;
    }

    boolean pressStartGameButton;

    /**
     * Constructor en el que se crea la ventana y su contenido, tambíen contiene eventos de si se pulsan ciertos botones.
     * @param game Obtiene el game, que es la clase que permite obtener todas las dependencias del juego.
     */
    public SetPersonaje(final MainGame game) {
        Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));
        this.game = game;
        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);

        this.catImages = new Array<>();
        catImages.add(tipoGato(Cat.TypeCat.BROWN));
        catImages.add(tipoGato(Cat.TypeCat.BLACK));

        this.stage = new Stage(viewport, game.batch);
        gatoElegido = Cat.TypeCat.BLACK;

        soundBoolean = PreferencesClass.getSoundPreferences("sound");
        vibratorBoolean = PreferencesClass.getPrefVibrator("vibrator");

        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);

        final ImageTextButton records = new ImageTextButton("Records", skin,"default");
        final ImageTextButton salir = new ImageTextButton("Salir", skin,"default");
        final ImageTextButton creditos = new ImageTextButton("Ver creditos", skin,"default");
        final ImageTextButton help = new ImageTextButton("Ayuda", skin,"default");
        sound = new ImageTextButton(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off", skin,"default");
        vibrator = new ImageTextButton(PreferencesClass.getPrefVibrator("vibrator") ? "Vibracion: On" : "Vibracion: Off", skin,"default");

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Label label = new Label("Elige el personaje", MainGame.generatorStyle(12, Color.WHITE));

        table.row().center();
        table.add(label).padLeft(60).padTop(10);
        table.row().expandX().right();
        table.add(records).left();
        table.row().expandX().right();
        table.add(salir).left();
        table.row().right();
        table.add(sound).left();
        table.row().right();
        table.add(vibrator).left();
        table.row().right();
        table.add(help).left();
        table.row().right();
        table.add(creditos).left().padBottom(50);


        //boton derecha
        TextureRegion buttonRight = new TextureRegion(new Texture("controllers/UI_orange_buttons_pressed_3.png"),32,0,16,16);
        Sprite dR = new Sprite(buttonRight);
        Image buttonRightImg = new Image(dR);
        buttonRightImg.setSize(16,16);

        //boton izq
        TextureRegion buttonLeft = new TextureRegion(new Texture("controllers/UI_orange_buttons_pressed_3.png"),0,16,16,16);

        Sprite drL = new Sprite(buttonLeft);
        Image buttonLeftImg = new Image(drL);
        buttonLeftImg.setSize(16,16);

        cat = new Sprite();
        gatoElegido = Cat.TypeCat.BROWN;
        cat = catImages.get(i);
        cat.setPosition(211,120);
        buttonLeftImg.setPosition(cat.getX()-15,cat.getY()+5);
        buttonRightImg.setPosition(cat.getX()+32,cat.getY()+5);

        Texture newGameTexture = new Texture("startgame.png");
        final SpriteDrawable startGame = new SpriteDrawable(new Sprite(newGameTexture));
        ImageButton button = new ImageButton(startGame);
        button.setSize(100,100);
        button.setPosition(176,23);
        stage.addActor(table);
        stage.addActor(buttonLeftImg);

        stage.addActor(buttonRightImg);
        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);

        //evento de boton de ayuda
        help.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Help(game));
                dispose();
                return true;
            }
        });

        //evento de botón de creditos
        creditos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Creditos(game,SetPersonaje.this,gatoElegido));
                dispose();
                return true;
            }
        });

        //evento de botón de records
        records.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Records(game, SetPersonaje.this));
                dispose();
                return true;
            }
        });

        //evento de botón de startgame
        button.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressStartGameButton = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new SetLevel(game, gatoElegido));
                dispose();

                return true;
            }
        });

        //evento de boton de seleccion de gato
        buttonLeftImg.addListener(new InputListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (i){
                    case 1:
                        gatoElegido = Cat.TypeCat.BROWN;
                        break;
                    case 0:
                        gatoElegido = Cat.TypeCat.BLACK;

                        break;
                }
                i = i == 0 ? 1 : 0;
                return true;
            }
        });

        //evento de boton de seleccion de gato
        buttonRightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (i){
                    case 1:
                        gatoElegido = Cat.TypeCat.BROWN;
                        break;
                    case 0:
                        gatoElegido = Cat.TypeCat.BLACK;

                        break;
                }
                i = i == 1 ? 0 : 1;
                return super.touchDown(event, x, y, pointer, button);

            }
        });

        //evento de botón de sonido
        sound.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundBoolean = !soundBoolean;
                PreferencesClass.setSoundPreferences("sound", soundBoolean);
                sound.setText(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off");

                if (PreferencesClass.getSoundPreferences("sound")) {
                    music.play();
                } else {
                    music.stop();
                }
                return true;
            }
        });

        //evento de botón de sonido
        vibrator.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                vibratorBoolean = !vibratorBoolean;
                PreferencesClass.setPrefVibrator("vibrator", vibratorBoolean);
                vibrator.setText(PreferencesClass.getPrefVibrator("vibrator") ? "Vibracion: On" : "Vibracion: Off");
                System.out.println(PreferencesClass.getPrefVibrator("vibrator"));
                return true;
            }
        });

        salir.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
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
     * Método que renderiza la ventana y si se realiza el evento pasa a la ventana del juego.
     * @param delta Parámetro que indica el delta.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(catImages.get(i),211,120);
        this.cat = tipoGato(gatoElegido);
        game.batch.end();
        stage.draw();
    }

    /**
     * @see Screen#resize(int, int)
     * @param width Es el ancho de la pantalla.
     * @param height Es el alto de la pantalla.
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
