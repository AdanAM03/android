package com.example.juego.modelo;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.juego.BucleJuego;
import com.example.juego.MainActivity;
import com.example.juego.R;
import com.example.juego.modelo.Objetos.Bola;
import com.example.juego.modelo.Objetos.Fondo;
import com.example.juego.modelo.Objetos.JoyStick;
import com.example.juego.modelo.Objetos.Nave;
import com.example.juego.modelo.recursos.Constantes;

import java.util.ArrayList;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    private BucleJuego bucleJuego;
    private HiloBolas hiloBolas;
    DisplayMetrics displayMetrics;
    private Rect botonReinicia = new Rect();
    public boolean muerte = false;
    public boolean estado = false;
    Fondo f;
    private Nave nave;
    private Bola[] joystick = new Bola[2];
    private float velNavX, velNavY;

    public Juego(Context context) {
        super(context);

        // Superficie tocable)?
        SurfaceHolder surfaceHolder= getHolder();
        surfaceHolder.addCallback(this);

        // Asigno el ancho de pixeles de la pantalla y el alto
        displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constantes.altoPixeles = displayMetrics.heightPixels;
        Constantes.anchoPixeles = displayMetrics.widthPixels;

        Constantes.iniciaColores();
        // Inicializo todos los objetos con sus drawables
        inicializarObjetos();

        bucleJuego = new BucleJuego(this, surfaceHolder);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        bucleJuego.iniciarBucle();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        f.draw(canvas);
        canvas.drawRect(Constantes.marcoPausa[0], Constantes.marcoPausa[2], Constantes.marcoPausa[1], Constantes.marcoPausa[3], Constantes.colorBoton);
        canvas.drawText("Pausa", Constantes.marcoPausa[0], Constantes.marcoPausa[2] + 35, Constantes.colorTexto);
        joystick[0].draw(canvas);
        joystick[1].draw(canvas);
        drawFPS(canvas);
        drawContador(canvas);
        //drawUpdate(canvas);
        hiloBolas.draw(canvas);
        nave.draw(canvas);
    }

    public void drawGameOver(Canvas canvas) {
        // Pinto el fondo por que en teoría la nave ha sido destruida por un meteorito
        f.draw(canvas);

        // Creo el cuadro de la ventana donde aparecerá el texto y botón de reinicio
        Rect modal = new Rect();
        modal.set(100, 100, Constantes.anchoPixeles-100, Constantes.altoPixeles-100);
        botonReinicia.set(800, 800, 1000, 900);

        // Creo los mensajes de texto que informarán al usuario de su puntuación
        String mensaje = String.format("Fin del juego!!");
        String mensaje2 = String.format("Puntuacion: %d ptos.", bucleJuego.getPuntuacion());


        canvas.drawRect(modal, Constantes.colorModal);
        canvas.drawRect(botonReinicia, Constantes.colorBoton);
        canvas.drawText(mensaje, 540f, 450f, Constantes.colorTextoModal);
        canvas.drawText(mensaje2, 390f, 550f, Constantes.colorTextoModal);
        canvas.drawText("Reiniciar", 810, 850, Constantes.colorTextoBotonModal);


    }

    private void drawUpdate(Canvas canvas) {
        String upadtesMedias = String.format("%.1f", bucleJuego.getUpdatesMedias());
        canvas.drawText("Updates: " + upadtesMedias, 50, 70, Constantes.colorTexto);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = String.format("%.1f", bucleJuego.getAverageFPS());
        canvas.drawText("FPS: " + averageFPS, 50, 150, Constantes.colorTexto);
    }

    private void drawContador(Canvas canvas) {
        String puntuacion = String.format("%d", bucleJuego.getPuntuacion());
        canvas.drawText("Puntos: " + puntuacion, 50, 70, Constantes.colorTexto);
    }

    public void actualizacion() {
        for (Bola bola: hiloBolas.getMeteoritos()) {
            bola.colisiones();
        }
        nave.setVelXActual(velNavX);
        nave.setVelYActual(velNavY);
        nave.actualiza(0, 0);
    }

    public void comprobarVida() {
        muerte = nave.comprobarVida(hiloBolas.getMeteoritos());
    }

    public void pintaPausa(Canvas canvas) {
        f.draw(canvas);
        canvas.drawRect(Constantes.marcoInicio[0], Constantes.marcoInicio[2], Constantes.marcoInicio[1], Constantes.marcoInicio[3], Constantes.colorBoton);
        canvas.drawText("Iniciar", Constantes.marcoInicio[0] + 5, Constantes.marcoInicio[2] + 30, Constantes.colorTexto);
        joystick[0].draw(canvas);
        joystick[1].draw(canvas);
        hiloBolas.draw(canvas);
        nave.draw(canvas);
        try {
            sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (!muerte) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                joystick[0].setPosicionX(x);
                joystick[0].setPosicionY(y);
                joystick[1].setPosicionX(x);
                joystick[1].setPosicionY(y);
            }

            if (!estado && Constantes.compruebaBoton(x, y, false)) {
                estado = true;
                return true;
            }

            if (estado && Constantes.compruebaBoton(x, y, true)) {
                estado = false;
                return false;
            }

            float velX;
            float velY;

            velX = (x - joystick[0].getPosX()) / 5f;
            velY = (y - joystick[0].getPosY()) / 5f;
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    joystick[1].setPosicionX(x);
                    joystick[1].setPosicionY(y);
                    velNavX = velX;
                    velNavY = velY;
                    nave.recoloca();
                    break;

                case MotionEvent.ACTION_UP:
                    joystick[0].setPosicionX(-250);
                    joystick[0].setPosicionX(-250);
                    joystick[1].setPosicionX(-250);
                    joystick[1].setPosicionY(-250);
                    velNavX = 0f;
                    velNavY = 0f;
                    break;
            }
        } else {
            if (x >= botonReinicia.left && x <= botonReinicia.right &&
            y >= botonReinicia.top && y <= botonReinicia.bottom) {
                inicializarObjetos();
                bucleJuego.resetPuntos();
                estado = false;
                muerte = false;
            }

        }
        return true;
    }

    private void inicializarObjetos() {
        f = new Fondo(getResources(), R.drawable.fondo);
        Drawable[] arrayFotos = new Drawable[2];
        arrayFotos[0] = getResources().getDrawable(R.drawable.m1);
        arrayFotos[1] = getResources().getDrawable(R.drawable.m3);

        hiloBolas = new HiloBolas(arrayFotos);
        nave = new Nave(1500f, 600f, Constantes.colorTexto, 100f, 100f, getResources().getDrawable(R.drawable.nave));

        joystick[0] = new JoyStick( 150f, Constantes.altoPixeles - 150f, new Paint(), 125f);
        joystick[1] = new JoyStick( 150f, Constantes.altoPixeles - 150f, new Paint(), 75f);
        joystick[0].setColor(ContextCompat.getColor(getContext(), R.color.amarillo));
        joystick[1].setColor(ContextCompat.getColor(getContext(), R.color.gris));

        float[] marco = {Constantes.anchoPixeles - 300, Constantes.anchoPixeles - 150, 0, 60};
        Constantes.marcoPausa = marco;

    }
}
