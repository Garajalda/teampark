package com.teampark.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;

public class Controllers extends Touchpad implements Disposable, InputProcessor {

    Viewport viewport;
    public Stage stage;

    //Touchpad

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


    public Controllers(SpriteBatch batch){
        super(4,Controllers.getTouchPadStyle());
        viewport = new FitViewport(MainGame.VIEW_WIDTH,MainGame.VIEW_HEIGHT,new OrthographicCamera());


        stage = new Stage(viewport, batch);

        setBounds(15, 10, 80, 80);
        stage.addActor(this);
        MainGame.multiplexer.addProcessor(stage);
        //Gdx.input.setInputProcessor(stage);
    }



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
