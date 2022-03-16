package com.teampark.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;

/**
 * Clase que define el control del touch, tanto las imagenes como la lógica.
 * @see Touchpad
 * @see Disposable
 * @see InputProcessor
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Controllers extends Touchpad implements Disposable, InputProcessor {

    /**
     *Define coordenadas de la camara y tamaño.
     */
    Viewport viewport;

    /**
     * Escena que contiene actores y acciones.
     */
    public Stage stage;

    //Touchpad

    /**
     * Método que define el estilo del touchpad.
     * @return devuelve un objeto de TouchpadStyle
     */
        private static TouchpadStyle getTouchPadStyle(){
            Skin touchpadSkin;
            touchpadSkin = new Skin();
            touchpadSkin.add("touchBackground", new Texture("controllers/JoystickSplitted.png"));
            touchpadSkin.add("touchKnob", new Texture("controllers/touchKnob.png"));

            TouchpadStyle touchpadStyle = new TouchpadStyle();
            touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
            touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
            return touchpadStyle;
        }


    /**
     * Constructor que define los elementos del Touchpad, stage y el viewport.
     * @param batch batch Es el parámetro que nos permite dibujar en 2D rectángulos.
     */
    public Controllers(SpriteBatch batch){
        super(4,Controllers.getTouchPadStyle());
        Gdx.input.setInputProcessor(MainGame.multiplexer);

        viewport = new FitViewport(MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT,new OrthographicCamera());


        stage = new Stage(viewport, batch);

        setBounds(15, 10, 80, 80);
        stage.addActor(this);
        //MainGame.multiplexer.addProcessor(settings.stage);
        MainGame.multiplexer.addProcessor(stage);

    }



    /**
     * Método que libera recursos de la escena.
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
