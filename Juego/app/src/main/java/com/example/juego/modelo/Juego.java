package com.example.juego.modelo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.juego.BucleJuego;
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
    private Paint colorTexto, colorBoton;
    DisplayMetrics displayMetrics;
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

        // Inicializo todos los objetos con sus drawables
        inicializarObjetos();

        colorBoton = new Paint();
        colorBoton.setColor(ContextCompat.getColor(getContext(), R.color.verde));

        colorTexto = new Paint();
        colorTexto.setColor(ContextCompat.getColor(getContext(), R.color.magenta));
        colorTexto.setTextSize(40f);
        colorTexto.setElegantTextHeight(true);
        colorTexto.setLetterSpacing(0.1f);

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
        canvas.drawRect(Constantes.marcoPausa[0], Constantes.marcoPausa[2], Constantes.marcoPausa[1], Constantes.marcoPausa[3], colorBoton);
        canvas.drawText("Pausa", Constantes.marcoPausa[0], Constantes.marcoPausa[2] + 35, colorTexto);
        joystick[0].draw(canvas);
        joystick[1].draw(canvas);
        drawFPS(canvas);
        drawContador(canvas);
        //drawUpdate(canvas);
        hiloBolas.draw(canvas);
        nave.draw(canvas);

        if (muerte)
            drawGameOver(canvas);
    }

    private void drawGameOver(Canvas canvas) {
        String mensaje = String.format("Fin del juego!! \nPuntuacion: %d ptos.", bucleJuego.getPuntuacion());
        colorTexto.setTextSize(100f);
        colorTexto.setColor(ContextCompat.getColor(getContext(), R.color.verde));
        canvas.drawText(mensaje, 300f, 500f, colorTexto);
    }

    private void drawUpdate(Canvas canvas) {
        String upadtesMedias = String.format("%.1f", bucleJuego.getUpdatesMedias());
        canvas.drawText("Updates: " + upadtesMedias, 50, 70, colorTexto);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = String.format("%.1f", bucleJuego.getAverageFPS());
        canvas.drawText("FPS: " + averageFPS, 50, 150, colorTexto);
    }

    private void drawContador(Canvas canvas) {
        String puntuacion = String.format("%d", bucleJuego.getPuntuacion());
        canvas.drawText("Puntos: " + puntuacion, 50, 70, colorTexto);
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
        canvas.drawRect(Constantes.marcoInicio[0], Constantes.marcoInicio[2], Constantes.marcoInicio[1], Constantes.marcoInicio[3], colorBoton);
        canvas.drawText("Iniciar", Constantes.marcoInicio[0] + 5, Constantes.marcoInicio[2] + 30, colorTexto);
        joystick[0].draw(canvas);
        joystick[1].draw(canvas);
        hiloBolas.draw(canvas);
        nave.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

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


        if ((x >= joystick[0].getPosX() - joystick[0].getRadio() && x <= joystick[0].getPosX() + joystick[0].getRadio()) &&
                (y >= joystick[0].getPosY() - joystick[0].getRadio() && y <= joystick[0].getPosY() + joystick[0].getRadio())) {
            velX = (x - joystick[0].getPosX()) / 5f;
            velY = (y - joystick[0].getPosY()) / 5f;
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    joystick[1].setPosicionX(x);
                    joystick[1].setPosicionY(y);
                    if ((nave.getAncho() + nave.getPosX()) >= 5 && nave.getPosX() <= Constantes.anchoPixeles - 5)
                        velNavX = velX;
                    else {
                        nave.recoloca();
                    }

                    if ((nave.getAlto() + nave.getPosY()) >= 5 && nave.getPosY() <= Constantes.altoPixeles - 5)
                        velNavY = velY;
                    else {
                        nave.recoloca();
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    joystick[1].setPosicionX(joystick[0].getPosX());
                    joystick[1].setPosicionY(joystick[0].getPosY());
                    velNavX = 0f;
                    velNavY = 0f;
                    break;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            joystick[1].setPosicionX(joystick[0].getPosX());
            joystick[1].setPosicionY(joystick[0].getPosY());
            velNavX = 0f;
            velNavY = 0f;
        }

        return true;
    }

    private void inicializarObjetos() {
        f = new Fondo(getResources(), R.drawable.fondo);
        Drawable[] arrayFotos = new Drawable[2];
        arrayFotos[0] = getResources().getDrawable(R.drawable.m1);
        arrayFotos[1] = getResources().getDrawable(R.drawable.m3);

        hiloBolas = new HiloBolas(arrayFotos);
        nave = new Nave(1500f, 600f, colorTexto, 100f, 100f, getResources().getDrawable(R.drawable.nave));

        joystick[0] = new JoyStick( 150f, Constantes.altoPixeles - 150f, new Paint(), 125f);
        joystick[1] = new JoyStick( 150f, Constantes.altoPixeles - 150f, new Paint(), 75f);
        joystick[0].setColor(ContextCompat.getColor(getContext(), R.color.amarillo));
        joystick[1].setColor(ContextCompat.getColor(getContext(), R.color.gris));

        float[] marco = {Constantes.anchoPixeles - 300, Constantes.anchoPixeles - 150, 0, 60};
        Constantes.marcoPausa = marco;

    }
}
