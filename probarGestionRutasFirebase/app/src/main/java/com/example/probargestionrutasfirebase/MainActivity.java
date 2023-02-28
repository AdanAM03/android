package com.example.probargestionrutasfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.probargestionrutasfirebase.clases.Usuario;
import com.example.probargestionrutasfirebase.dao.DaoUsuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoUsuario dao = new DaoUsuario("Usuario");
/*
        dao.addUsuario(new Usuario("Miguel Angel", "Garrido"));
        dao.deleteUsuario(new Usuario("Francisco", "Franco"));
        dao.updateUsuario(new Usuario("Cisco", "Cornelius"), "Francisco", null);
*/

        ArrayList<Usuario> array = dao.getUsuarios();

        for(Usuario u:array){
            Log.d(":::TAG", u.toString());
        }

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
}