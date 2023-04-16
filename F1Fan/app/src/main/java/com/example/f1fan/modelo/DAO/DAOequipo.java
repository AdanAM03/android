package com.example.f1fan.modelo.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.pojos.BDestatica;
import com.example.f1fan.modelo.pojos.Equipo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DAOequipo {
    private BD bd;
    private FirebaseFirestore fb;

    public DAOequipo() {
        bd = new BD();
        fb = bd.getDB();
    }

    public void getEquipos() {
        fb.collection("equipos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("equipo", document.getId() + " => " + document.getData());
                                Equipo e = new Equipo();
                                e.setId(document.getId());
                                e.setNombre(document.get("nombre", String.class));
                                e.setVictorias(document.get("victorias", Integer.class));
                                e.setUrl_foto(document.get("url_foto", String.class));
                                e.setAnhos_activo(document.get("anhos_activo", Integer.class));
                                e.setTeam_principal(document.get("team_principal", String.class));

                                BDestatica.addEquipo(e);
                            }
                        } else {
                            Log.d("piloto", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void modificaEquipo(Equipo equipoNuevo) {
        fb.collection("equipos").document(equipoNuevo.getId())
                .set(equipoNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("equipo", "DocumentSnapshot successfully written!");
                        BDestatica.modificaEquipo(equipoNuevo);
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
