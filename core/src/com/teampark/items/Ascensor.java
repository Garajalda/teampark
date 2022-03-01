package com.teampark.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.teampark.MainGame;
import com.teampark.Sprites.Boton;
import com.teampark.screens.JuegoScreen;

/**
 * Clase que define un ascensor
 * @see Sprite
 * @see Disposable implementa
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Ascensor extends Sprite implements Disposable {
    /**
     * Define las características del cuerpo
     * @see Body
     */
    public Body body;
    /**
     * Define las físicas del mundo
     * @see World
     */
    private final World world;

    /**
     * Variable que define si llego a cierto punto el ascensor para cambiar de movimiento.
     */
    private boolean llego;


    /**
     * Constructor del ascensor que define en el nivel en el que se esta mostrando y la posición
     * @param screen
     * @param x
     * @param y
     */
    public Ascensor(JuegoScreen screen,float x, float y) {
        super(new Texture("elevator.png"));
        this.world = screen.getWorld();
        setScale( 0.02f,0.05f);
        setPosition(x,y);

        defineAscensor();
        body.setActive(false);
        llego = false;

    }

    /**
     * Método que define las características del cuerpo
     */
    public void defineAscensor() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.KinematicBody;

        body = world.createBody(bdef);
        body.setActive(false);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        fdef.friction = 0.3f;

        shape.setAsBox((float)13/MainGame.PPM,(float) 3/MainGame.PPM);
        fdef.filter.categoryBits = MainGame.ASCENSOR_BIT;
        fdef.filter.maskBits = MainGame.SUELO_BIT | MainGame.CAT_BIT;

        fdef.shape = shape;

        body.createFixture(fdef);
    }


    /**
     * Actualiza la posición del ascensor
     * @param dt
     */
    public void update(float dt) {

        setPosition(body.getPosition().x -getWidth() / 2, body.getPosition().y -getHeight()/2);
        if(!llego &&getY() <= 140/MainGame.PPM) {
            body.setLinearVelocity(new Vector2(0,0.5f));
            if(getY() >= 0.7900000){
                llego = true;
            }
        }

        if(llego && getY() >= 50/MainGame.PPM){
            body.setLinearVelocity(new Vector2(0,-0.5f));
            if(getY() <= 0.008333802){
                llego = false;
            }
        }
    }

    /**
     * Dibuja el sprite del ascensor en el renderizado
     * @param batch
     */
    public void draw(Batch batch){
        //si el botón ha sido pulsado dibuja el ascensor
        if(Boton.btnPulsado){
            super.draw(batch);
            body.setActive(true);

        }
    }

    /**
     * Método que implementa la liberación de recursos
     */
    @Override
    public void dispose() {

    }
}
