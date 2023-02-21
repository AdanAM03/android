package com.example.juego.modelo.recursos;

import android.graphics.Rect;

public class Constantes {
    public final static int VEL_X = 10;
    public final static int VEL_Y = 15;

    public static float[] marcoInicio = {600, 750, 400, 450};
    public static float[] marcoPausa = new float[4];

    public static int anchoPixeles = 0;
    public static int altoPixeles = 0;

    public static boolean compruebaBoton(float x, float y, boolean pausado) {
        if (!pausado && x >= marcoInicio[0] && x <= marcoInicio[1] && y >= marcoInicio[2] && y <= marcoInicio[3]){
            return true;
        }

        if (pausado && x >= marcoPausa[0] && x <= marcoPausa[1] && y >= marcoPausa[2] && y <= marcoPausa[3]){
            return true;
        }

        return false;
    }

}
