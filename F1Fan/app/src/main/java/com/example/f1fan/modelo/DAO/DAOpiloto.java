package com.example.f1fan.modelo.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.pojos.BDestatica;
import com.example.f1fan.modelo.pojos.Piloto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DAOpiloto {
    private BD bd;
    private FirebaseFirestore fb;

    public DAOpiloto() {
        bd = new BD();
        fb = bd.getDB();
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
                                p.setId(document.getId());
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

                                BDestatica.addPiloto(p);
                            }
                        } else {
                            Log.d("piloto", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void modificaPiloto(Piloto pilotoNuevo) {
        fb.collection("pilotos").document(String.valueOf(pilotoNuevo.getId()))
                .set(pilotoNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("piloto", "DocumentSnapshot successfully written!");
                        BDestatica.modificaPiloto(pilotoNuevo);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("piloto", "Error writing document", e);
                    }
                });
    }
}
