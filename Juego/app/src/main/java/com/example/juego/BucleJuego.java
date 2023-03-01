package com.example.juego;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.juego.modelo.Juego;


/*
* Maneja el juego por completo, implementa Runnable para poder para el juego,
* iniciarlo en un momento determinato, etc...
*/
public class BucleJuego extends Thread{
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;

    private boolean estaIniciado = false;
    private SurfaceHolder surfaceHolder;
    private Juego juego;
    private double ups, fps;
    private int puntuacion;


    public BucleJuego(Juego juego, SurfaceHolder surfaceHolder) {
        this.juego = juego;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageFPS() {
        return fps;
    }

    public double getUpdatesMedias() {
        return ups;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void iniciarBucle() {
        estaIniciado = true;
        start();
    }

    public void resetPuntos() {
        this.puntuacion = 0;
    }

    @Override
    public void run() {
        super.run();

        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        // Game loop
        Canvas canvas = null;

        startTime = System.currentTimeMillis();
        while(estaIniciado) {
            while (juego.muerte) {
                canvas = surfaceHolder.lockCanvas();
                juego.drawGameOver(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            while (!juego.estado) {
                canvas = surfaceHolder.lockCanvas();
                juego.pintaPausa(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
                // Try to update and render game
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        juego.actualizacion();
                        juego.comprobarVida();

                        updateCount++;
                        juego.draw(canvas);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    if(canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            frameCount++;
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Pause game loop to not exceed target UPS
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
                if(sleepTime > 0) {
                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Skip frames to keep up with target UPS
                while(sleepTime < 0 && updateCount < MAX_UPS - 1) {
                    juego.actualizacion();
                    updateCount++;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
                }

                // Calculate average UPS and FPS
                elapsedTime = System.currentTimeMillis() - startTime;
                if(elapsedTime >= 1000) {
                    puntuacion++;
                    ups = updateCount / (1E-3 * elapsedTime);
                    fps = frameCount / (1E-3 * elapsedTime);
                    updateCount = 0;
                    frameCount = 0;
                    startTime = System.currentTimeMillis();
                }


        }

    }
}
