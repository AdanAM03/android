package com.example.juego.modelo;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.juego.modelo.Objetos.Bola;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HiloBolas implements Runnable {
    private ArrayList<Bola> meteoritos = new ArrayList<>();
    private Canvas c;
    private Thread hilo;
    private Drawable[] fotos;

    public HiloBolas(Drawable[] fotos) {
        this.fotos = fotos;
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random()*this.fotos.length);
            Bola bola = new Bola(getXRandom(), getYRandom(), null, this.fotos[index]);
            meteoritos.add(bola);
        }
        hilo = new Thread(this);
    }

    @Override
    public void run() {
        pinta();
    }

    public void draw(Canvas c) {
        this.c = c;
        hilo.run();
    }
    private void pinta() {
        for (Bola bola : meteoritos)
            bola.draw(c);
    }

    private float getXRandom() {
        return ((float) Math.random() * 300) + 50;
    }

    private float getYRandom() {
        return ((float) Math.random() * 500) + 50;
    }

    public ArrayList<Bola> getMeteoritos() {
        return meteoritos;
    }

    public void addBola() {
        Bola bola = new Bola(getXRandom(), getYRandom(), null, fotos[((int)(Math.random()*fotos.length))]);
        meteoritos.add(bola);
    }
}
