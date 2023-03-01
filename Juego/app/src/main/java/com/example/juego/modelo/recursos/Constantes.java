package com.example.juego.modelo.recursos;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.juego.MainActivity;
import com.example.juego.R;
import com.example.juego.modelo.Juego;

public class Constantes {
    public final static int VEL_X = 10;
    public final static int VEL_Y = 15;

    public static float[] marcoInicio = {600, 750, 400, 450};
    public static float[] marcoPausa = new float[4];

    public static int anchoPixeles = 0;
    public static int altoPixeles = 0;

    public static Paint colorTexto, colorTextoBotonModal, colorBoton, colorModal, colorTextoModal;

    public static void iniciaColores() {
        colorBoton = new Paint();
        colorTexto = new Paint();
        colorModal = new Paint();
        colorTextoBotonModal = new Paint();
        colorTextoModal = new Paint();

        colorBoton.setARGB(100, 188, 151, 252);

        colorTextoBotonModal.setARGB(100, 255, 61, 61);
        colorTextoBotonModal.setTextSize(45f);


        colorTexto.setARGB(100, 255, 100, 117);
        colorTexto.setTextSize(40f);
        colorTexto.setLetterSpacing(0.1f);

        colorModal.setARGB(99, 1, 135, 134);

        colorTextoModal.setARGB(100, 70, 255, 231);
        colorTextoModal.setTextSize(100f);
        colorTextoModal.setLetterSpacing(0.2f);

    }

    public static boolean compruebaBoton(float x, float y, boolean pausado) {
        if (!pausado && x >= marcoInicio[0] && x <= marcoInicio[1] && y >= marcoInicio[2] && y <= marcoInicio[3]){
            return true;
        }

        if (pausado && x >= marcoPausa[0] && x <= marcoPausa[1] && y >= marcoPausa[2] && y <= marcoPausa[3]){
            return true;
        }

        return false;
    }

    public static void reinicia(Context c) {
        MainActivity.juego = new Juego(c);
    }

}
