package com.teampark.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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


public abstract class ObjetosTileInteractivos {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public ObjetosTileInteractivos(JuegoScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(((float)(bounds.getX() + bounds.getWidth()/2)/ MainGame.PPM),(float)(bounds.getY() + bounds.getHeight()/2)/MainGame.PPM);
        body = world.createBody(bdef);
        shape.setAsBox((float)bounds.getWidth()/2/MainGame.PPM,(float)bounds.getHeight()/2/MainGame.PPM);
        fdef.shape= shape;
        fixture = body.createFixture(fdef);

    }



    public abstract void onFootHit();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }


    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        //reescalar hacia arriba para volver al tamaño original y
        // obtener el cell de la posicion en la que
        //se encuentra el cuerpo.
        return layer.getCell((int)(body.getPosition().x * MainGame.PPM/16),
                (int)(body.getPosition().y * MainGame.PPM/16));
    }
}
