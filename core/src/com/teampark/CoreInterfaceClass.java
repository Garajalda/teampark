package com.teampark;

import com.badlogic.gdx.math.Vector2;
import com.teampark.Sprites.cats.Cat;

import java.util.ArrayList;
import java.util.Hashtable;

public class CoreInterfaceClass implements FireBaseInterface{
    @Override
    public void setOnValueChangedListener() {

    }

    @Override
    public void writeCat(int codGato, Cat cat) {

    }


    @Override
    public Hashtable<Integer, Vector2> getCodRecogido() {
        return null;
    }

    @Override
    public ArrayList<Cat> getCats() {
        return null;
    }

    @Override
    public void updateDatos(int codCat, Cat cat) {

    }

    @Override
    public void transacction(Cat.TypeCat tipo, float x, float y) {
        System.out.println("usando CORE");

    }

    @Override
    public void updateDatos(Cat.TypeCat tipo, float x, float y) {

    }
}
