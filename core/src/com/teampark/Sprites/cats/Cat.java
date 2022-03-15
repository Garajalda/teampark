package com.teampark.Sprites.cats;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.teampark.MainGame;
import com.teampark.screens.JuegoScreen;
import com.teampark.tools.WorldContactListener;


/**
 * Clase que define el personaje principal del juego
 * @see Sprite
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class Cat extends Sprite {

    /**
     * Define los estados en los que se encuentra el personaje
     */
    public enum State{SALTO, PARA, CAMINA, CAIDA,DEAD}

    /**
     * Define el tipo de gato
     */
    public enum TypeCat{BLACK, BROWN}

    /**
     * Define el estado actual
     */
    public State estadoActual;
    /**
     * Define el estado anterior
     */
    public State estadoAnterior;
    /**
     * Define las fisicas del mundo
     */
    public World world;
    /**
     * Define las características del cuerpo
     */
    public Body body;
    /**
     * Define la región de la textura
     */
    private TextureRegion catTextureRegion;
    /**
     * Define la animacion cuando el personaje camina
     */
    private Animation catCamina;
    /**
     * Es la textura concreta del personaje cuando salta
     */
    private TextureRegion catSalta;
    /**
     * Textura de cuando muere el personaje
     */
    private TextureRegion catDead;
    /**
     * Contador de delta
     */
    private float stateTimer;
    /**
     * Indica si esta mirando hacia la derecha
     */
    private boolean isRight;
    /**
     * Tipo de gato
     */
    private TypeCat tipo;
    /**
     * Lista de frames de la animación
     */
    private final Array<TextureRegion> frames;
    /**
     * Indica si murió el personaje principal
     */
    private boolean catIsDead;
    /**
     * Ubicación del eje x del cuerpo
     */
    private final float xBody;
    /**
     *Ubicación del eje y del cuerpo
     */
    private final float yBody;

    /**
     * Método que nos indica de que tipo es el personaje
     * @param tipo
     */
    public void setTipo(TypeCat tipo){
        this.tipo = tipo;
    }

    /**
     * Contiene coordenadas de la posición
     * @see Vector2
     */
    Vector2 posicionAnterior;

    /**
     * Constructor que inicializa los estados actuales
     * @param screen
     * @param tipoGato
     * @param x
     * @param y
     */
    public Cat(JuegoScreen screen,TypeCat tipoGato,float x, float y){
        super(screen.getTextureAtlas().findRegion("cat_"+tipoGato.name()+"-32x48"));
        this.world = screen.getWorld();
        posicionAnterior = new Vector2(getX(),getY());
        estadoActual = State.PARA;
        estadoAnterior = State.PARA;
        stateTimer = 0;
        isRight = true;
        setTipo(tipoGato);
        this.xBody = x;
        this.yBody = y;

        //gato corre
        this.frames = new Array<>();
        getTipoGato();

    }


    /**
     * Método que actualiza la posición de la textura
     * @param dt
     */
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2, (float) ((double) body.getPosition().y-getHeight()/3.3));
        setRegion(getFrame(dt));

    }

    /**
     * Método que nos devuelve los frames de cada tipo de gato
     */
    private void getTipoGato(){
        switch (tipo){
            case BLACK:

                for (int i = 0; i <3 ; i++) {
                    frames.add(new TextureRegion(getTexture(), i * 32,48,32,48));
                }

                //gato Salta
                catSalta = new TextureRegion(getTexture(),64,48,32,48);
                catDead = catSalta;
                catTextureRegion = new TextureRegion(getTexture(), 32,48,32,48);

                break;
            case BROWN:

                frames.add(new TextureRegion(getTexture(), 96 ,48,34,48));
                frames.add(new TextureRegion(getTexture(), 128 ,48,34,48));
                frames.add(new TextureRegion(getTexture(), 160 ,48,34,48));
                //gato Salta
                catSalta = new TextureRegion(getTexture(),160,48,34,48);
                catDead = catSalta;
                catTextureRegion = new TextureRegion(getTexture(), 128,48,34,48);

                break;

        }


        catCamina = new Animation(0.1f,frames);
        frames.clear();

        defineCat();
        setBounds(0,0,(float)32/MainGame.PPM,(float)48/MainGame.PPM);
        setRegion(catTextureRegion);
    }

    /**
     * Método que nos devuelve la textura del estado en el que se encuentra
     * @param dt
     * @return la textura del gato
     */
    private TextureRegion getFrame(float dt) {
        estadoActual = getState();
        TextureRegion region;
        switch (estadoActual){
            case SALTO:
                region = catSalta;
                break;
            case CAMINA:
                region = (TextureRegion) catCamina.getKeyFrame(stateTimer,true);
                break;
            case CAIDA:
            case DEAD:
                region = catDead;
                break;
            case PARA:
            default:
                region = catTextureRegion;
                break;
        }
            if(getY() < 0){
                catIsDead = true;
            }

            if((body.getLinearVelocity().x <0 || !isRight) && !region.isFlipX()){

                 region.flip(true,false);
                 isRight = false;

            }
            else if((body.getLinearVelocity().x >0 || isRight) && region.isFlipX()){
                region.flip(true,false);
                isRight = true;
                //System.out.println(b2body.getLinearVelocity().x);
            }


        stateTimer = estadoActual == estadoAnterior ? stateTimer + dt : 0;
        estadoAnterior = estadoActual;
        return region;
    }


    /**
     * Método que nos devuelve el estado del gato.
     * @return
     */
    public State getState(){
        if(catIsDead)
            return State.DEAD;
        else if(WorldContactListener.catNotTouch && (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && estadoAnterior == State.SALTO)))
            return State.SALTO;
        else if(WorldContactListener.catNotTouch && body.getLinearVelocity().y<0)
            return State.CAIDA;
        else if(body.getLinearVelocity().x != 0)
            return State.CAMINA;
        else
            return State.PARA;
    }

    /**
     * Método que devuelve si el personaje esta muerto
     * @return nos devuelve booleana
     */
    public boolean isDead(){
        return catIsDead;
    }

    /**
     * Método que nos devuelve un contador.
     * @return
     */
    public float getStateTimer(){
        return stateTimer;
    }


    /**
     * Método que define el cuerpo del personaje
     */
    private void defineCat() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(xBody /MainGame.PPM, yBody /MainGame.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();

        shape.setAsBox((float)13/MainGame.PPM,(float) 9/MainGame.PPM);
        fdef.filter.categoryBits = MainGame.CAT_BIT;
        fdef.filter.maskBits = MainGame.SUELO_BIT | MainGame.BOTON_BIT | MainGame.CAT_BIT | MainGame.ASCENSOR_BIT | MainGame.KEY_BIT |MainGame.PUERTA_BIT | MainGame.CUBO_BIT;

        fdef.shape = shape;

        body.createFixture(fdef).setUserData("body");

        //cabeza
        EdgeShape head = new EdgeShape();
        head.set(new Vector2((float) -7/MainGame.PPM,(float) 10/MainGame.PPM),new Vector2((float) 7/MainGame.PPM,(float) 10/MainGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData("head");



        //pies
        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2((float) -7/MainGame.PPM,(float)-10/MainGame.PPM),new Vector2((float) 7/MainGame.PPM,(float)-10/MainGame.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

    }
}
