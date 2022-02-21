package com.teampark;

import com.badlogic.gdx.math.Vector2;
import com.teampark.Sprites.cats.Cat;

import java.util.ArrayList;
import java.util.Hashtable;

public interface FireBaseInterface {
    public void setOnValueChangedListener();
    public void writeCat(int codGato, Cat cat);
    public Hashtable<Integer, Vector2> getCodRecogido();
    public ArrayList<Cat>getCats();
    public void updateDatos(int codCat, Cat cat);
    public void transacction(Cat.TypeCat tipo, float x, float y);

    void updateDatos(Cat.TypeCat tipo,float x, float y);
}
