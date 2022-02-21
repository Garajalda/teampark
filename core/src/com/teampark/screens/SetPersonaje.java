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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;

import java.util.ArrayList;


public class SetPersonaje implements Screen{

    private final Stage stage;
    private MainGame game;
    Skin skin;
    private Array<Sprite> catImages;

    public static ArrayList<Cat.TypeCat> map = new ArrayList<>();
    public Cat.TypeCat gatoElegido;

    Music music;
    Sprite cat;
    private int i = 0;


    public Sprite tipoGato(Cat.TypeCat tipoGato){

        TextureAtlas textureAtlas = new TextureAtlas("Cats.pack");
        TextureAtlas.AtlasRegion getTexture = textureAtlas.findRegion("cat_"+tipoGato+"-32x48");

        TextureRegion catTextureRegion = new TextureRegion(getTexture, 32, 48, 32, 48);
        Sprite dr = new Sprite(catTextureRegion);
        dr.setSize(100,100);
        return dr;
    }

    boolean pressStartGameButton;
    public boolean getPressStartGameButton(){
        return pressStartGameButton;
    }

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
        music = MainGame.managerSongs.get("audio/music/aeon.ogg", Music.class);
        music.setLooping(true);


        Table table = new Table();
        table.setFillParent(true);
        table.top();

        TextField label = new TextField("  Elige el personaje",skin);

        table.row().expandX().center();
        table.add(label).center();
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
        cat.setPosition(180,130);
        buttonLeftImg.setPosition(cat.getX()-15,cat.getY()+5);
        buttonRightImg.setPosition(cat.getX()+32,cat.getY()+5);

        Texture newGameTexture = new Texture("startgame.png");
        final SpriteDrawable startGame = new SpriteDrawable(new Sprite(newGameTexture));
        ImageButton button = new ImageButton(startGame);
        button.setSize(100,100);
        button.setPosition(147,30);
        stage.addActor(table);
        stage.addActor(buttonLeftImg);

        stage.addActor(buttonRightImg);
        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);


        button.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressStartGameButton = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {


                pressStartGameButton = true;


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

                if(i == 1){
                    i = 0;
                }else{
                    i++;
                }
                return true;
            }
        });
    }

    @Override
    public void show() {

    }

    public void update(float dt){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.9f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        game.batch.begin();
        game.batch.draw(catImages.get(i),180,130);
        this.cat = tipoGato(gatoElegido);
        game.batch.end();
        stage.act();
        stage.draw();
        if(getPressStartGameButton()){

            game.setScreen(new JuegoScreen(game, gatoElegido));
            dispose();
        }

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
        music.dispose();
        stage.dispose();
    }


}
