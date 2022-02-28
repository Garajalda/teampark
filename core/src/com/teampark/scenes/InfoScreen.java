package com.teampark.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.teampark.MainGame;

public class InfoScreen implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Integer ss;
    private float timeCount;
    private Integer mm;

    private StringBuilder tiempoTotal;
    public StringBuilder getTiempoTotal() {
        return tiempoTotal;
    }
    Label levelLabel;
    Label countLabel;
    String level;

    public InfoScreen(SpriteBatch sb, String level) {
        viewport = new FillViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        this.level = level;
        ss = 0;
        mm = 0;
        timeCount = 0;

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        levelLabel = new Label(level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countLabel = new Label(String.format("%02d:%02d",mm,ss), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        table.row().expandX();
        table.add(levelLabel);
        table.add(countLabel);
        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;

        if(timeCount >= 1){
            ss++;
            if(ss >59){
                mm++;
                ss = 0;
            }
            countLabel.setText(String.format("%02d:%02d",mm,ss));
            tiempoTotal = countLabel.getText();
            timeCount = 0;


        }


    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
