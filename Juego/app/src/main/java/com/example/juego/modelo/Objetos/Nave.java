package com.example.juego.modelo.Objetos;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.juego.modelo.recursos.Constantes;

import java.util.ArrayList;

public class Nave extends Objeto{
    private float alto;
    private float ancho;
    private float posAlto, posAncho;
    Drawable fondo;

    public Nave(float posicionX, float posicionY, Paint paint, float alto, float ancho, Drawable fondo) {
        super(posicionX, posicionY, paint);
        this.alto = alto;
        this.ancho = ancho;
        posAlto = alto + posicionY;
        posAncho = ancho + posicionX;
        this.fondo = fondo;
        this.fondo.setBounds((int) getPosX(), (int) getPosY(), (int) posAncho, (int) posAlto);
    }

    public void draw(Canvas canvas) {
        fondo.draw(canvas);
    }

    @Override
    public void actualizaVelocidad(float x, float y, float factorX, float factorY) {

        this.setPosicionX(this.getPosX() + x);
        this.setPosicionY(this.getPosY() + y);
        posAncho = this.getPosX() + ancho;
        posAlto = this.getPosY() + alto;
        this.fondo.setBounds((int) getPosX(), (int) getPosY(), (int) posAncho, (int) posAlto);
    }

    public float getAlto() {
        return posAlto;
    }

    public float getAncho() {
        return posAncho;
    }

    public boolean comprobarVida(ArrayList<Bola> meteoritos) {
        // Los 4 puntos del cuadrado/rectángulo
        float izquierda = getPosX(), derecha = getAncho(), bajo = getPosY(), alto = getAlto();

        float eIzquierda, eDerecha, eAlto, eBajo;

        // Compruebo si alguno de los meteoritos ha colisionado con la nave
        for (Bola bola : meteoritos) {
            eIzquierda = bola.getPosX() - bola.getRadio();
            eDerecha = bola.getPosX() + bola.getRadio();
            eBajo = bola.getPosY() - bola.getRadio();
            eAlto = bola.getPosY() + bola.getRadio();

            // Compruebo si x esta colisionando
            if (((eIzquierda >= izquierda && eIzquierda <= derecha) || (eDerecha >= izquierda && eDerecha <= derecha)) &&
                    // Compruebo si y esta colisionando
                    ((eAlto >= bajo && eAlto <= alto) || (eBajo >= bajo && eBajo <= alto))){
                Log.d("momento","eAlto:" + eAlto + " eBajo:"+ eBajo + " alto:"+ alto + " bajo:"+ bajo + " eIz"+ eIzquierda +
                        " eDer:" + eDerecha + " der:" + derecha + " izq:" + izquierda);
                return true;
            }

        }
        return false;
    }

    public void recoloca() {
        if (getAncho() <= 0)
            setPosicionX(Constantes.anchoPixeles - 10);
        else if (getPosX() >= Constantes.anchoPixeles)
            setPosicionX(getAncho() + 10);

        if (getAlto() <= 0)
            setPosicionY(Constantes.altoPixeles - 10);
        else if (getPosY() >= Constantes.altoPixeles)
            setPosicionY(getAlto() + 10);
    }

}
