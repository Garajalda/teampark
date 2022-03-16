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

/**
 * Clase que define un la llave para pasar al siguiente nivel
 * @see Sprite
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Key extends Sprite {

    /**
     * Variable que define las características del cuerpo
     * @see Body
     */
    public Body body;

    /**
     * Variable que define las físicas del mundo
     * @see World
     */
    private final World world;

    /**
     * Variable que define el tipo de contacto del objeto
     * @see Fixture
     */
    private Fixture fixture;

    /**
     * Variable que define si la llave ha sido recogida o no
     */
    private boolean keyUp;

    /**
     * Constructor que define la posición del objeto y el nivel en el que se encuentra
     * @param screen El parámetro screen define la pantalla previa que se pasa como parámetro, en este caso sería cualquier nivel heredado por JuegoScreen.
     * @param x Se trata de la coordenada X del objeto.
     * @param y Se trata de la coordenada Y del objeto.
     */
    public Key(JuegoScreen screen,float x, float y) {
        super(new Texture("key.png"));
        setScale(0.0032f,0.0032f);
        this.world = screen.getWorld();
        setPosition(x,y);
        defineItem();
    }


    /**
     * Método que define las características de la llave
     */
    public void defineItem() {
        BodyDef bdef = new BodyDef();
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

    /**
     * Método que devuelve si se ha recogido la llave o no.
     * @return keyUp
     */
    public boolean llaveCogida(){
        return keyUp;
    }

    /**
     * Método que permite saber si esta fuera de su sitio original
     */
    private boolean mooveON = false;

    /**
     * Método que activa que la llave siga al personaje
     */
    public void keySigue(){
        setCategoryFilter(MainGame.DESTROYED_BIT);
        keyUp = true;
        mooveON = true;
    }

    /**
     * Método que define los filtros del objeto
     * @param filterBit Se trata de un parámetro que nos permite definir el filtro de colisión.
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * Método que dibuja el sprite de la llave en el renderizado
     * @param batch Es el parámetro que nos permite dibujar en 2D rectángulos.
     */
    public void draw(Batch batch){
        super.draw(batch);
    }

    /**
     * Método que permite la actualización de la posición de la llave
     * @param dt Es el delta que se pasa previamente.
     * @param cat Obtenemos el personaje cat para obtener la posición del gato y así que se mueva la llave a dicha posición.
     */
    public void update(float dt, Cat cat) {
        if(!mooveON)
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        else{
            setPosition(cat.body.getPosition().x-getWidth()/2-0.1000f,cat.body.getPosition().y-getHeight()/2+0.0600f);
        }

    }
}
