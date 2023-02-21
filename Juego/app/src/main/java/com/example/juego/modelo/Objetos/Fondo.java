package com.example.juego.modelo.Objetos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.example.juego.R;
import com.example.juego.modelo.Juego;
import com.example.juego.modelo.recursos.Constantes;

public class Fondo implements Runnable{
    private int fondo;
    private Canvas c;
    private Bitmap bitmap;
    private Thread hilo;

    public Fondo(Resources r, int foto) {
        fondo = foto;
        bitmap = BitmapFactory.decodeResource(r, fondo);
        bitmap = Bitmap.createScaledBitmap(bitmap, Constantes.anchoPixeles /*Ancho*/, Constantes.altoPixeles /*Alto*/, false /* filter*/);
        hilo = new Thread(this);
    }

    public void draw(Canvas canvas) {
        c = canvas;
        hilo.run();
    }


    @Override
    public void run() {
        c.drawBitmap(bitmap, 0, 0, null);
    }
}
