package com.example.f1fan.modelo;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.f1fan.modelo.pojos.BDestatica;
import com.example.f1fan.modelo.pojos.Equipo;
import com.example.f1fan.modelo.pojos.Piloto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BD {
    private FirebaseFirestore fb;

    public BD() {
        fb = FirebaseFirestore.getInstance();
    }

    public void getPilotos() {
        fb.collection("pilotos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("piloto", document.getId() + " => " + document.getData());
                                Piloto p = new Piloto();
                                Log.d("error", "" + document.get("nombre", String.class));
                                p.setId(Long.valueOf(document.getId()));
                                p.setNombre(document.get("nombre", String.class));
                                p.setApellidos(document.get("apellidos", String.class));
                                p.setEdad(document.get("edad", Integer.class));
                                p.setEquipo(document.get("equipo", String.class));
                                p.setGp_terminados(document.get("gp_terminados", Integer.class));
                                p.setVictorias(document.get("victorias", Integer.class));
                                p.setPole_positions(document.get("pole_positions", Integer.class));
                                p.setPuntos(document.get("puntos", Float.class));
                                p.setPodios(document.get("podios", Integer.class));
                                p.setUrl_foto(document.get("url_foto", String.class));

                                BDestatica.addPiloto(document.toObject(Piloto.class));
                            }
                        } else {
                            Log.d("piloto", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void modificaPiloto(Piloto pilotoNuevo, Piloto pilotoAntiguo) {
        fb.collection("pilotos").document(pilotoAntiguo.getNombre())
                .set(pilotoNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("piloto", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("piloto", "Error writing document", e);
                    }
                });
    }

    public void modificaEquipo(Equipo equipoNuevo, Equipo equipoAntiguo) {
        fb.collection("pilotos").document(equipoAntiguo.getNombre())
                .set(equipoNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("equipo", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("equipo", "Error writing document", e);
                    }
                });
    }

}
