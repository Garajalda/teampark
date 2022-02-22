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


public class Cat extends Sprite {


    public enum State{SALTO, PARA, CAMINA, CAIDA,DEAD}
    public enum TypeCat{BLACK, BROWN}
    public State estadoActual;
    public State estadoAnterior;
    public World world;
    public Body b2body;
    private TextureRegion catTextureRegion;
    private Animation catCamina;
    private TextureRegion catSalta;
    private TextureRegion catDead;
    private float stateTimer;
    private boolean runningRight;
    private TypeCat tipo;
    private final Array<TextureRegion> frames;
    private boolean catIsDead;
    private final float xBody;
    private final float yBody;

    public void setTipo(TypeCat tipo){
        this.tipo = tipo;
    }
    public TypeCat getTipo(){
        return tipo;
    }
    Vector2 previousPosition;
    public Cat(JuegoScreen screen,TypeCat tipoGato,float x, float y){
        super(screen.getTextureAtlas().findRegion("cat_"+tipoGato.name()+"-32x48"));
        this.world = screen.getWorld();
        previousPosition = new Vector2(getX(),getY());
        estadoActual = State.PARA;
        estadoAnterior = State.PARA;
        stateTimer = 0;
        runningRight = true;
        setTipo(tipoGato);
        this.xBody = x;
        this.yBody = y;

        //gato corre


        this.frames = new Array<>();
        getTipoGato();

    }

    public void handleInput(float dt){

    }



    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, (float) ((double) b2body.getPosition().y-getHeight()/3.3));
        setRegion(getFrame(dt));

    }
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

        defineCatBlack();
        setBounds(0,0,(float)32/MainGame.PPM,(float)48/MainGame.PPM);
        setRegion(catTextureRegion);
    }

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

            if((b2body.getLinearVelocity().x <0 || !runningRight) && !region.isFlipX()){

                 region.flip(true,false);
                 runningRight = false;

            }
            else if((b2body.getLinearVelocity().x >0 || runningRight) && region.isFlipX()){
                region.flip(true,false);
                runningRight = true;
                //System.out.println(b2body.getLinearVelocity().x);
            }




        stateTimer = estadoActual == estadoAnterior ? stateTimer + dt : 0;
        estadoAnterior = estadoActual;
        return region;
    }


    public State getState(){
        if(catIsDead)
            return State.DEAD;
        else if(WorldContactListener.catNotTouch && (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && estadoAnterior == State.SALTO)))
            return State.SALTO;
        else if(WorldContactListener.catNotTouch && b2body.getLinearVelocity().y<0)
            return State.CAIDA;
        else if(b2body.getLinearVelocity().x != 0)
            return State.CAMINA;
        else
            return State.PARA;
    }

    public boolean isDead(){
        return catIsDead;
    }
    public float getStateTimer(){
        return stateTimer;
    }


    private void defineCatBlack() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((float)xBody/MainGame.PPM,(float)yBody/MainGame.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox((float)13/MainGame.PPM,(float) 9/MainGame.PPM);
        fdef.filter.categoryBits = MainGame.CAT_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT | MainGame.BOTON_BIT | MainGame.CAT_BIT | MainGame.ASCENSOR_BIT | MainGame.KEY_BIT |MainGame.PUERTA_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData("body");




        //cabeza
        EdgeShape head = new EdgeShape();
        head.set(new Vector2((float) -7/MainGame.PPM,(float) 10/MainGame.PPM),new Vector2((float) 7/MainGame.PPM,(float) 10/MainGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

        //pies
        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2((float) -7/MainGame.PPM,(float)-10/MainGame.PPM),new Vector2((float) 7/MainGame.PPM,(float)-10/MainGame.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("foot");


    }
}
