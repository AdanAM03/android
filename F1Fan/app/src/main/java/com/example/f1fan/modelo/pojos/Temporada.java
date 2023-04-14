package com.example.f1fan.modelo.pojos;

public class Temporada {
    private String id;
    private String piloto_campeon;
    private String equipo_campeon;
    private int num_carreras;
    private int anho;

    public Temporada(String id, String piloto_campeon, String equipo_campeon, int num_carreras, int anho) {
        this.id = id;
        this.piloto_campeon = piloto_campeon;
        this.equipo_campeon = equipo_campeon;
        this.num_carreras = num_carreras;
        this.anho = anho;
    }

    public Temporada() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPiloto_campeon() {
        return piloto_campeon;
    }

    public void setPiloto_campeon(String piloto_campeon) {
        this.piloto_campeon = piloto_campeon;
    }

    public String getEquipo_campeon() {
        return equipo_campeon;
    }

    public void setEquipo_campeon(String equipo_campeon) {
        this.equipo_campeon = equipo_campeon;
    }

    public int getNum_carreras() {
        return num_carreras;
    }

    public void setNum_carreras(int num_carreras) {
        this.num_carreras = num_carreras;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }
}
