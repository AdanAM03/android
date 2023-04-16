package com.example.f1fan.modelo.pojos;

public class PilotoRanking {
    private String nombre;
    private int puesto;
    private float puntos;

    public PilotoRanking(String nombre, int puesto, float puntos) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.puntos = puntos;
    }

    public PilotoRanking() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public float getPuntos() {
        return puntos;
    }

    public void setPuntos(float puntos) {
        this.puntos = puntos;
    }
}
