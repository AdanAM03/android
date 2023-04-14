package com.example.f1fan.modelo.pojos;

import java.util.ArrayList;

public class BDestatica {
    private static ArrayList<Circuito> circuitos = new ArrayList<>();
    private static ArrayList<Equipo> equipos = new ArrayList<>();
    private static ArrayList<Noticia> noticias = new ArrayList<>();
    private static ArrayList<Piloto> pilotos = new ArrayList<>();
    private static ArrayList<Temporada> temporadas = new ArrayList<>();

    public Piloto getPiloto(int id) {
        return pilotos.get(id);
    }

    public static ArrayList<Piloto> getPilotos() {
        return pilotos;
    }

    public static void addPiloto(Piloto piloto) {
        pilotos.add(piloto);
    }

    public void modificaPiloto(Piloto pilotoNuevo, Piloto pilotoAntiguo) {
        pilotos.remove(pilotoAntiguo);
        pilotos.add(pilotoNuevo);
    }
}