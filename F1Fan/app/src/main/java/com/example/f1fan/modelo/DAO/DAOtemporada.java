package com.example.f1fan.modelo.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.pojos.BDestatica;
import com.example.f1fan.modelo.pojos.Temporada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DAOtemporada {
    private BD bd;
    private FirebaseFirestore fb;

    public DAOtemporada() {
        bd = new BD();
        fb = bd.getDB();
    }

    public void getTemporadas() {
        fb.collection("temporadas").orderBy("anho").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("temporada", document.getId() + " => " + document.getData());
                                Temporada t = new Temporada();
                                t.setId(document.getId());
                                t.setAnho(document.get("anho", Integer.class));
                                t.setEquipo_campeon(document.get("equipo", String.class));
                                t.setPiloto_campeon(document.get("piloto", String.class));
                                t.setNum_carreras(document.get("n_carreras", Integer.class));

                                BDestatica.addTemporada(t);
                            }
                        } else {
                            Log.d("temporada", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addTemporada(Temporada t) {
        fb.collection("temporadas").add(t).addOnSuccessListener(new OnSuccessListener() {

                    @Override
                    public void onSuccess(Object o) {
                        Log.d("temporada", "DocumentSnapshot successfully written!");
                        BDestatica.addTemporada(t);
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
