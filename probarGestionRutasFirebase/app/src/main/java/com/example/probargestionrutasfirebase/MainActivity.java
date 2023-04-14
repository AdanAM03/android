package com.example.probargestionrutasfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.probargestionrutasfirebase.clases.Usuario;
import com.example.probargestionrutasfirebase.clases.dao.DaoUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private ArrayList<Usuario> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        dao.addUsuario(new Usuario("Miguel Angel", "Garrido"));
        dao.deleteUsuario(new Usuario("Francisco", "Franco"));
        dao.updateUsuario(new Usuario("Cisco", "Cornelius"), "Francisco", null);
*/

        /*
        // Añadir
        // db.collection("Usuario").add(new Usuario("Cisco", "Cornelius"));

        // Get

        db.collection("Usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Usuario u= new Usuario();
                for(DocumentSnapshot doc: task.getResult()){
                    id = doc.getId();
                    Log.d(":::TAG", id);

                    // Log.d(":::TAG", "Usuario: "+u.toString());
                }
                // Update
                db.collection("Usuario").document(id)
                        .update("nombre", "Francisco", "apellido", "Franco");

                // Delete
                db.collection("Usuario").document(id)
                        .delete();
            }
        });
        */
    }

    @Override
    public void update(Observable o, Object arg) {
        a = (ArrayList<Usuario>) arg;
        for (Usuario u: a)
            Log.d("gasolina", u.getApellido() + " " + u.getNombre());

    }

}