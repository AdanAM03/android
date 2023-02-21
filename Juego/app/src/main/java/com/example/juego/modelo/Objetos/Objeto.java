package com.example.juego.modelo.Objetos;

import android.graphics.Paint;
import android.util.Log;

import com.example.juego.modelo.recursos.Constantes;

public abstract class Objeto {
    //Controla la dirección del objeto
    private int factorX = 1, factorY = 1;
    private float posicionX;
    private float posicionY;
    private Paint paint;
    private float velXActual = Constantes.VEL_X;
    private float velYActual = Constantes.VEL_Y;

    public Objeto(float posicionX, float posicionY, Paint paint) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.paint = paint;
    }

    public void actualizaVelocidad(float x, float y, float factorX, float factorY) {

        posicionX = (int) (posicionX + x * factorX);
        posicionY = (int) (posicionY + y * factorY);
    }

    public void actualiza( int dirX, int dirY){

        switch (dirX) {
            case 1: factorX = 1; break;
            case -1: factorX = -1; break;
        }

        switch (dirY) {
            case 1: factorY = 1; break;
            case -1: factorY = -1; break;
        }

        actualizaVelocidad(velXActual, velYActual, factorX, factorY);
    }

    public Paint getPaint() {
        return paint;
    }

    public float getPosX() {
        return posicionX;
    }

    public float getPosY() {
        return posicionY;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }

    public void setVelXActual(float velXActual) {
        this.velXActual = velXActual;
    }

    public void setVelYActual(float velYActual) {
        this.velYActual = velYActual;
    }

    public void setColor(int idColor) {
        paint.setColor(idColor);
    }
}
