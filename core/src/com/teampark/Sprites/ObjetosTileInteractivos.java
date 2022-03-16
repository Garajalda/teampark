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


/**
 * Clase que define los objetos con características comunes que tienen una interacción en el nivel
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public abstract class ObjetosTileInteractivos {
    /**
     * Variable que determina las físicas del mundo
     * @see World
     */
    protected World world;
    /**
     * Variable que obtiene el mapa.
     * @see World
     */
    protected TiledMap map;
    /**
     * Variable que obtiene el tile del mapa
     * @see TiledMap
     */
    protected TiledMapTile tile;
    /**
     * Variable que define el rectangulo
     * @see TiledMapTile
     */
    protected Rectangle bounds;
    /**
     * Variable que define el cuerpo
     * @see Rectangle
     */
    protected Body body;
    /**
     * Tipo de contacto
     * @see Fixture
     */
    protected Fixture fixture;

    /**
     * Constructor que define todos los atributos
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro.
     * @param bounds Define el rectángulo.
     * @see Rectangle
     */
    public ObjetosTileInteractivos(JuegoScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(((bounds.getX() + bounds.getWidth()/2) / MainGame.PPM), (bounds.getY() + bounds.getHeight()/2) /MainGame.PPM);
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() /2/MainGame.PPM, bounds.getHeight() /2/MainGame.PPM);
        fdef.shape= shape;
        fixture = body.createFixture(fdef);

    }

    /**
     * Método que implementa el contacto del pie
     */
    public abstract void contactoFoot();

    /**
     * Método que implementa el contacto del body
     */
    public abstract void onBodyHit();

    /**
     * Método que implementa el filtro de contacto
     * @param filterBit Es el tipo de filtro que se le indica.
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }


    /**
     * Método que devuelve la posicion de la celda en la que ha habido contacto
     * @return  posicion de celda
     */
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        //reescalar hacia arriba para volver al tamaño original y
        // obtener el cell de la posicion en la que
        //se encuentra el cuerpo.
        return layer.getCell((int)(body.getPosition().x * MainGame.PPM/16),
                (int)(body.getPosition().y * MainGame.PPM/16));
    }

}
