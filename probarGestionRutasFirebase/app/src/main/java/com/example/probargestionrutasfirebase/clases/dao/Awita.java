package com.example.probargestionrutasfirebase.clases.dao;

import android.util.Log;

import java.util.Observer;

public class Awita implements Observer {
    @Override
    public void update(java.util.Observable o, Object arg) {
        Log.d("gasolina", "vaporeon - ");
    }
}
