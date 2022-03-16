package com.teampark.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
 * Clase que define el objeto cubo que se encuentra en el nivel 2
 * @see Sprite
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Cubo extends Sprite {
    /**
     * Variable que determina las físicas del mundo
     * @see World
     */
    private final World world;
    /**
     * Variable que define la forma de los cubos
     * @see Rectangle
     */
    private final Rectangle rectangle;
    /**
     * Variable que define las características del cuerpo
     * @see Body
     */
    public Body body;
    /**
     * @see Fixture
     */
    private Fixture fixture;

    /**
     * Constructor que define las propiedades del cubo
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro.
     * @param rectangle Define el rectángulo.
     * @see Rectangle
     */
    public Cubo(JuegoScreen screen, Rectangle rectangle){

        super(new Texture("ice.png"));
        this.world = screen.getWorld();
        this.rectangle = rectangle;
        setSize(rectangle.getWidth() /MainGame.PPM, rectangle.getHeight() /MainGame.PPM);
        defineItem();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y -getHeight()/2);
    }


    /**
     * Método que define el cuerpo del cubo
     */
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ MainGame.PPM,(rectangle.getY() + rectangle.getHeight()/2)/MainGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(rectangle.getWidth()/2/MainGame.PPM,rectangle.getHeight()/2/MainGame.PPM);
        fdef.shape= shape;
        fdef.filter.categoryBits = MainGame.CUBO_BIT;
        fdef.filter.maskBits = MainGame.SUELO_BIT | MainGame.CAT_BIT;

        fdef.density = 6f;
        fdef.restitution = 0;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);


        setCategoryFilter(MainGame.CUBO_BIT);
    }

    /**
     * Método que actualiza la posición del cubo cuando se arrastra
     * @param dt Parámetro que indica el delta.
     */
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y -getHeight()/2);
    }

    /**
     * Método que dibuja el cubo en el renderizado.
     * @param batch Es el parámetro que nos permite dibujar en 2D rectángulos.
     */
    public void draw(Batch batch){
        super.draw(batch);
    }

    /**
     * Método que añade un filtro de contacto
     * @param filterBit Indica el tipo de filtro al que nos referimos.
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
