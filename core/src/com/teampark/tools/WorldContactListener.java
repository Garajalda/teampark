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

public class WorldContactListener implements ContactListener {
    static public boolean catNotTouch= false;
    static public boolean catTouchAscensor = false;
    boolean llaveCogida = false;

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
                ((ObjetosTileInteractivos)object.getUserData()).onFootHit();
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
                    System.out.println("toca ascensor");
                }
            case MainGame.GROUND_BIT | MainGame.CAT_BIT:
                if(fixA.getFilterData().categoryBits== MainGame.CAT_BIT) {

                    //System.out.println("toca suelo");
                }

        }
    }

    @Override
    public void endContact(Contact contact) {
        catNotTouch = true;
        catTouchAscensor = false;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
