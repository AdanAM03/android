package com.example.f1fan.modelo.pojos;

import com.google.firebase.auth.FirebaseUser;

public class Usuario {
    private FirebaseUser usuario;
    private Rol rol;

    public Usuario(FirebaseUser usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
