package com.example.juego.modelo.Objetos;

import android.graphics.Paint;

public class JoyStick extends Bola{
    public JoyStick(float posicionX, float posicionY, Paint paint, float radio) {
        super(posicionX, posicionY, paint, null);
        super.setRadio(radio);
    }
}
