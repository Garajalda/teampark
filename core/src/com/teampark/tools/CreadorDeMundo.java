package com.teampark.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.teampark.MainGame;
import com.teampark.Sprites.Boton;
import com.teampark.Sprites.Cubo;
import com.teampark.Sprites.Puerta;
import com.teampark.screens.JuegoScreen;
import com.teampark.screens.Level2;

import java.util.ArrayList;

/**
 * Clase que define la creación de objetos en el mundo
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class CreadorDeMundo {

    /**
     * Guarda los cubos creados en un array
     * @see Cubo
     */
    private final ArrayList<Cubo> cubos;

    /**
     * Constructor de clase que define las características comunes de los objetos creados de un tilemap
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro.
     */
    public CreadorDeMundo(JuegoScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //obtengo el objeto del suelo
        for (RectangleMapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ MainGame.PPM,(rectangle.getY() + rectangle.getHeight()/2)/MainGame.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectangle.getWidth()/2/MainGame.PPM,rectangle.getHeight()/2/MainGame.PPM);
            fdef.shape= shape;
            fdef.filter.categoryBits = MainGame.SUELO_BIT;
            fdef.filter.maskBits = MainGame.CAT_BIT | MainGame.ASCENSOR_BIT | MainGame.CUBO_BIT;

            body.createFixture(fdef);

        }

        //obtengo objeto boton
        for (RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = object.getRectangle();
            new Boton(screen,rectangle);
        }

        //obtengo objeto puerta
        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = object.getRectangle();
            new Puerta(screen,rectangle);
        }

        cubos = new ArrayList<>();

        if(screen instanceof Level2){
            for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rectangle = object.getRectangle();
                cubos.add(new Cubo(screen,rectangle));
            }
        }
    }

    /**
     * Método que devuelve los cubos creados.
     * @return ArrayList<Cubo> Devuelve lista de cubos.
     */
    public ArrayList<Cubo> getCubos(){
        return cubos;
    }


}
