package com.example.juego.modelo.Objetos;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.juego.modelo.recursos.Constantes;

public class Bola extends Objeto{
    private float radio;
    private Drawable foto;


    public Bola(float posicionX, float posicionY, Paint paint, Drawable foto) {
        super(posicionX, posicionY, paint);
        this.foto = foto;
        this.radio = ((float) Math.random() * 45) + 15;
        super.setVelYActual(5);
        super.setVelXActual(5);
    }

    public void draw(Canvas canvas) {
        if (foto == null)
            canvas.drawCircle(getPosX(), getPosY(), radio, getPaint());
        else {
            foto.setBounds((int)getPosX()-(int)radio, (int)getPosY()-(int)radio, (int)getPosX()+(int)radio, (int)getPosY()+(int)radio);
            foto.draw(canvas);
        }
    }


    public void colisiones() {
            if ((this.getPosX() - radio <= 0) || (this.getPosX() + radio >= Constantes.anchoPixeles)
                    || (this.getPosY() - radio <= 0) || (this.getPosY() + radio >= Constantes.altoPixeles)) {
                super.setVelXActual((float) (Math.random() * Constantes.VEL_X) + 5 - (radio/10));
                super.setVelYActual((float) (Math.random() * Constantes.VEL_Y) + 5 - (radio/10));
            }

            // Colisiones y redirección del objeto
            if (this.getPosX() - radio <= 0)
                actualiza( 1, 0);
            else if (this.getPosX() + radio >= Constantes.anchoPixeles)
                actualiza(-1, 0);
            else if (this.getPosY() - radio <= 0)
                actualiza( 0, 1);
            else if (this.getPosY() + radio >= Constantes.altoPixeles)
                actualiza(0, -1);
            else
                actualiza(  0, 0);

    }

    public float getRadio() {
        return radio;
    }

    protected void setRadio(float radio) {
        this.radio = radio;
    }
}