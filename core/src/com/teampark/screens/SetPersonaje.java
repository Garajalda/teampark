package com.teampark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
 * @version 1.0, 2022/02/17
 */
public class SetPersonaje implements Screen{

    private final Stage stage;
    private final MainGame game;
    Skin skin;
    private final Array<Sprite> catImages;
    public static ArrayList<Cat.TypeCat> map = new ArrayList<>();
    public Cat.TypeCat gatoElegido;
    Music music;
    Sprite cat;
    private int i = 0;
    boolean soundBoolean;
    final ImageTextButton sound;

    /**
     * Método que define el personaje que ha elegido el ususario.
     * @param tipoGato
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
     * @param game
     */
    public SetPersonaje(final MainGame game) {
        skin= new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));
        this.game = game;
        final Viewport viewport;
        viewport = new FitViewport((float) MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT);

        this.catImages = new Array<>();
        catImages.add(tipoGato(Cat.TypeCat.BROWN));
        catImages.add(tipoGato(Cat.TypeCat.BLACK));

        this.stage = new Stage(viewport, ((MainGame)game).batch);
        gatoElegido = Cat.TypeCat.BLACK;

        soundBoolean = PreferencesClass.getSoundPreferences("sound");
        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);


        final Skin skin = new Skin(Gdx.files.internal("Terra_Mother_UI_Skin/terramotherui/terra-mother-ui.json"));
        final ImageTextButton records = new ImageTextButton("Records", skin,"default");
        final ImageTextButton salir = new ImageTextButton("Salir", skin,"default");
        final ImageTextButton creditos = new ImageTextButton("Ver creditos", skin,"default");
        final ImageTextButton help = new ImageTextButton("Ayuda", skin,"default");
        sound = new ImageTextButton(PreferencesClass.getSoundPreferences("sound") ? "Sonido: On" : "Sonido: Off",skin,"default");


        Table table = new Table();
        table.setFillParent(true);
        table.top();

        TextField label = new TextField("  Elige el personaje",skin);

        table.row().expandX().center();
        table.add(label).padLeft(59).padTop(10);
        table.row().expandX().right();
        table.add(records).left();
        table.row().expandX().right();
        table.add(salir).left();
        table.row().right();
        table.add(sound).left();
        table.row().right();
        table.add(help).left();
        table.row().right();
        table.add(creditos).left();


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

        help.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Help(game));
                dispose();
                return true;
            }
        });

        creditos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Creditos(game,SetPersonaje.this,gatoElegido));
                dispose();
                return true;
            }
        });

        records.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Records(game, SetPersonaje.this));
                dispose();
                return true;
            }
        });
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

        //eventos botones
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

    }

    /**
     * @see Screen#show()
     */
    @Override
    public void show() {

    }


    /**
     * Método que renderiza la ventana y si se realiza el evento pasa a la ventana del juego.
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(catImages.get(i),211,120);
        this.cat = tipoGato(gatoElegido);
        game.batch.end();
        stage.act();
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
