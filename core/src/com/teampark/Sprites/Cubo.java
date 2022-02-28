package com.teampark.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;

public class Cubo extends Sprite {
    private final World world;
    private final JuegoScreen screen;
    private final Rectangle rectangle;
    public Body body;
    private Fixture fixture;
    private TiledMap map;
    private Rectangle bounds;
    public Cubo(JuegoScreen screen, Rectangle rectangle){

        super(new Texture("ice.png"));
        this.world = screen.getWorld();
        this.screen = screen;
        this.rectangle = rectangle;
        setSize((float) rectangle.getWidth()/MainGame.PPM,(float)rectangle.getHeight()/MainGame.PPM);
        defineItem();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y -getHeight()/2);
    }


    public void defineItem() {
        this.map = screen.getMap();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ MainGame.PPM,(rectangle.getY() + rectangle.getHeight()/2)/MainGame.PPM);


        body = world.createBody(bdef);

        shape.setAsBox(rectangle.getWidth()/2/MainGame.PPM,rectangle.getHeight()/2/MainGame.PPM);
        fdef.shape= shape;
        fdef.filter.categoryBits = MainGame.CUBO_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT | MainGame.CAT_BIT;

        fdef.density = 6f;
        fdef.restitution = 0;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);


        setCategoryFilter(MainGame.CUBO_BIT);
    }

    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y -getHeight()/2);
    }
    public void draw(Batch batch){
        super.draw(batch);
    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
