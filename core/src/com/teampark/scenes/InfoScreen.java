package com.teampark.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.teampark.MainGame;

public class InfoScreen implements Disposable {
    public Stage stage;
    public Viewport viewport;

    Label levelLabel;
    String level;

    public InfoScreen(SpriteBatch sb, String level) {
        viewport = new FillViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        this.level = level;
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        levelLabel = new Label(level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.row();
        table.add(levelLabel).expandX();
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
