package com.example.probargestionrutasfirebase.clases.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.probargestionrutasfirebase.MainActivity;
import com.example.probargestionrutasfirebase.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Observable;

public class DaoUsuario extends Observable {

    private FirebaseFirestore ff;
    private String coleccion;
    private final CollectionReference DB;

    public DaoUsuario(String coleccion){
        this.ff = FirebaseFirestore.getInstance();
        this.DB = ff.collection(coleccion);
        this.addObserver(new MainActivity());
    }

    public void getUsuarios(){
        ArrayList<Usuario> array=new ArrayList<Usuario>();

        DB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc: task.getResult()){
                    Log.d(":::TAG", "Nombre: "+doc.getString("nombre")+" Apellido: "+doc.getString("apellido"));
                    array.add(new Usuario(doc.getString("nombre"), doc.getString("apellido")));
                }
                setChanged();
                notifyObservers(array);
            }
        });
    }

    public void addUsuario(Usuario u){
        DB.add(u);
    }

    public void deleteUsuario(Usuario u){
        DB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc: task.getResult()){
                    Usuario compara = new Usuario(doc.getString("nombre"), doc.getString("apellido"));
                    if(u.equals(compara)){
                        DB.document(doc.getId())
                                .delete();
                    }
                }
            }
        });
    }

    public void updateUsuario(Usuario u, String nombre, String apellido){
        if(nombre==null){
            DB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot doc: task.getResult()){
                        Usuario compara = new Usuario(doc.getString("nombre"), doc.getString("apellido"));
                        if(u.equals(compara)){
                            DB.document(doc.getId())
                                    .update("apellido", apellido);
                        }
                    }
                }
            });
        } else if (apellido==null){
            DB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot doc: task.getResult()){
                        Usuario compara = new Usuario(doc.getString("nombre"), doc.getString("apellido"));
                        if(u.equals(compara)){
                            DB.document(doc.getId())
                                    .update("nombre", nombre);
                        }
                    }
                }
            });
        } else {
            DB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot doc: task.getResult()){
                        Usuario compara = new Usuario(doc.getString("nombre"), doc.getString("apellido"));
                        if(u.equals(compara)){
                            DB.document(doc.getId())
                                    .update("nombre", nombre, "apellido", apellido);
                        }
                    }
                }
            });
        }
    }
}
