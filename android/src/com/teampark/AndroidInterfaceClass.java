package com.teampark;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.badlogic.gdx.math.Vector2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teampark.Sprites.cats.Cat;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AndroidInterfaceClass implements FireBaseInterface {
    FirebaseDatabase database;
    DatabaseReference myref;

    Instant lastUpdate;

    public AndroidInterfaceClass() {
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("room");
        if (myref == null) {
            myref.child("room");
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void transacction(Cat.TypeCat tipo, float x, float y) {
        Instant now = Instant.now();
        if (lastUpdate != null) {
            if (Duration.between(lastUpdate, now).getSeconds() >= 1) {
                myref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {

                        Map<String, Object> hopperUpdates = new HashMap<>();
                        hopperUpdates.put("Y", x);
                        hopperUpdates.put("X", y);
                        hopperUpdates.put("ID", tipo.ordinal());


                        mutableData.child(String.valueOf(tipo.ordinal())).setValue(hopperUpdates);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed) lastUpdate = now;
                    }
                });

            }
        } else {
            lastUpdate = now;
            setOnValueChangedListener();
        }
    }


    public void updateDatos(int codCat, Cat cat) {

        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(codCat + "/Y", cat.b2body.getPosition().x);
        hopperUpdates.put(codCat + "/X", cat.b2body.getPosition().y);


        myref.updateChildren(hopperUpdates);
    }

    @Override
    public void updateDatos(Cat.TypeCat tipo, float x, float y) {
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(tipo.ordinal() + "/Y", x);
        hopperUpdates.put(tipo.ordinal() + "/X", y);

        myref.updateChildren(hopperUpdates);
    }

    ArrayList<Cat> cats = new ArrayList<>();
    Hashtable<Integer, Vector2> codRecogidoBD = new Hashtable<>();

    @Override
    public void setOnValueChangedListener() {

        myref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<HashMap<String, Object>> roomData = (ArrayList<HashMap<String, Object>>) snapshot.getValue();
                if (roomData != null) {
                    roomData.forEach(cat -> {
                        Long id = (Long) cat.get("ID");
                        Double x = (Double) cat.get("X");
                        Double y = (Double) cat.get("Y");
                        if (x != null && y != null) {
                            codRecogidoBD.put(Math.toIntExact(id), new Vector2(x.floatValue(), y.floatValue()));
                        }


                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void writeCat(int codGato, Cat cat) {
        this.codGato = codGato;
        myref.child(codGato + "");
        myref.child(codGato + "").child("Y").setValue(cat.b2body.getPosition().y);
        myref.child(codGato + "").child("X").setValue(cat.b2body.getPosition().x);

    }

    int codGato;


    @Override
    public Hashtable<Integer, Vector2> getCodRecogido() {
        return codRecogidoBD;
    }

    @Override
    public ArrayList<Cat> getCats() {
        return cats;
    }
}
