package com.teampark.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.teampark.MainGame;
import com.teampark.Sprites.cats.Cat;
import com.teampark.screens.JuegoScreen;

public class Key extends Sprite {
    public Body body;
    private World world;
    private Fixture fixture;
    private boolean keyUp;
    public Key(JuegoScreen screen,float x, float y) {
        super(new Texture("key.png"));
        setScale(0.0032f,0.0032f);
        this.world = screen.getWorld();
        setPosition(x,y);
        defineItem();

    }

    BodyDef bdef;
    public void defineItem() {
        bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius((float) 3/ MainGame.PPM);
        fdef.shape= shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
        setCategoryFilter(MainGame.KEY_BIT);

    }

    public boolean llaveCogida(){
        return keyUp;
    }
    private boolean mooveON = false;
    public void keySigue(){
        setCategoryFilter(MainGame.DESTROYED_BIT);
        keyUp = true;
        mooveON = true;
    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    public void update(float dt, Cat cat) {
        if(!mooveON)
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        else{
            setPosition(cat.b2body.getPosition().x-getWidth()/2-0.1000f,cat.b2body.getPosition().y-getHeight()/2+0.0600f);
        }

    }
}
