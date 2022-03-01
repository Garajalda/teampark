package com.teampark.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teampark.MainGame;

/**
 * Esta clase define el texto que se encuentra en el juego.
 * @see com.badlogic.gdx.Screen
 * @see Disposable implementa
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class InfoScreen implements Disposable {
    /**
     * Define el escenario de la ventana
     * @see Stage
     */
    public Stage stage;

    /**
     * Define la resolución de la pantalla
     * @see Viewport
     */
    public Viewport viewport;


    /**
     * Contador de segundos
     */
    private Integer ss;

    /**
     * Contador de delta
     */
    private float timeCount;

    /**
     * Contador de minutos
     */
    private Integer mm;

    /**
     * Define el tiempo con el que se finaliza el nivel
     */
    private StringBuilder tiempoTotal;

    /**
     * Método que devuelve el tiempo total
     * @return tiempoTotal
     */
    public StringBuilder getTiempoTotal() {
        return tiempoTotal;
    }

    /**
     * Etiqueta de texto que define en la ventana el tiempo.
     */
    private final Label countLabel;

    /**
     * Contructor que implementa los valores del texto que se muestra por pantalla
     * @param sb
     * @param level
     */
    @SuppressWarnings("DefaultLocale")
    public InfoScreen(SpriteBatch sb, String level) {
        viewport = new FillViewport(MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        ss = 0;
        mm = 0;
        timeCount = 0;

        Table table = new Table();
        table.top().padTop(10);
        table.setFillParent(true);
        Label levelLabel = new Label(level, MainGame.generatorStyle(10,Color.WHITE));
        countLabel = new Label(String.format("%02d:%02d",mm,ss), MainGame.generatorStyle(10,Color.WHITE));
        table.row().expandX();
        table.add(levelLabel);
        table.add(countLabel);
        stage.addActor(table);

    }

    /**
     * Método que define la actualización del tiempo
     * @param dt
     */
    @SuppressWarnings("DefaultLocale")
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

    /**
     * Método que permite liberar recursos
     * @see Disposable#dispose()
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
