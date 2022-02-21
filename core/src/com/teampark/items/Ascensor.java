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

public class Ascensor extends Sprite implements Disposable {
    private float stateTime;
    public Body body;
    private World world;
    private boolean toDestroy;
    private boolean recomponer;


    public Ascensor(JuegoScreen screen,float x, float y) {
        super(new Texture("elevator.png"));
        this.world = screen.getWorld();
        setScale( 0.02f,0.05f);
        stateTime = 0;
        setPosition(x,y);

        defineAscensor();
        body.setActive(false);

        recomponer = false;
    }

    public void use(){
        recomponer = true;
    }

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
        fdef.filter.maskBits = MainGame.GROUND_BIT | MainGame.CAT_BIT;

        fdef.shape = shape;

        body.createFixture(fdef);
    }


    boolean llego = false;
    public void update(float dt) {
        stateTime += dt;

        if(Boton.btnPulsado){
            recomponer = true;
        }
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

    public void draw(Batch batch){
        if(Boton.btnPulsado){
            super.draw(batch);
            body.setActive(true);

        }
    }

    @Override
    public void dispose() {
        this.dispose();
    }
}
