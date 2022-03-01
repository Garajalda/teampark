package com.teampark.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.teampark.MainGame;
import com.teampark.Sprites.ObjetosTileInteractivos;
import com.teampark.Sprites.Puerta;
import com.teampark.items.Key;

/**
 * Clase que define el tipo de contacto del mundo
 * @author Gara Jalda / Colegio Vivas
 * @version 1.0
 */
public class WorldContactListener implements ContactListener {
    /**
     * Variable que define cuando el gato no esta tocando un objeto
     */
    static public boolean catNotTouch= false;
    /**
     * Variable que define cuando el gato no está tocando el ascensor
     */
    static public boolean catTouchAscensor = false;
    /**
     * Variable que define cuando el gato esta tocando el cubo
     */
    static public boolean catTouchCubo = false;
    /**
     * Variable que define cuando se ha recogido la llave
     */
    boolean llaveCogida = false;

    /**
     * Método que define si se ha mantenido contacto con algún objeto
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if (fixA.getUserData()=="foot" || fixB.getUserData() == "foot"){
            Fixture foot = fixA.getUserData() == "foot" ? fixA : fixB;
            Fixture object = foot==fixA ? fixB : fixA;

            if (object.getUserData() != null && ObjetosTileInteractivos.class.isAssignableFrom(object.getUserData().getClass())){
                catNotTouch = false;
                ((ObjetosTileInteractivos)object.getUserData()).contactoFoot();
            }
        }

        //body

        if (fixA.getUserData()=="body" || fixB.getUserData() == "body"){
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body==fixA ? fixB : fixA;
            if (object.getUserData() != null && Key.class.isAssignableFrom(object.getUserData().getClass())){

                Key k = ((Key)object.getUserData());
                k.keySigue();
                llaveCogida = k.llaveCogida();
            }

            if (object.getUserData() != null && ObjetosTileInteractivos.class.isAssignableFrom(object.getUserData().getClass())){
                //((ObjetosTileInteractivos)object.getUserData()).onBodyHit();
                if(object.getUserData() instanceof Puerta){
                    Puerta p = (Puerta) object.getUserData();
                    if(llaveCogida){
                        p.onBodyHit();
                        p.setNextLevel(true);
                    }
                }
            }

        }

        switch (cDef){
            case MainGame.ASCENSOR_BIT | MainGame.CAT_BIT:
                if(fixA.getFilterData().categoryBits== MainGame.CAT_BIT) {
                    catTouchAscensor = true;
                    catNotTouch = false;
                }
                break;
            case MainGame.CUBO_BIT | MainGame.CAT_BIT:
                if(fixA.getFilterData().categoryBits== MainGame.CAT_BIT) {
                    catNotTouch = false;
                    catTouchCubo = true;
                }
                break;
        }
    }

    /**
     * Método que define si ya no hay contacto
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {
        catNotTouch = true;
        catTouchAscensor = false;
        catTouchCubo = false;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
