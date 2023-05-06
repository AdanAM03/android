package com.example.f1fan.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.f1fan.MainActivity;
import com.example.f1fan.modelo.DAO.DAOusuario;
import com.example.f1fan.modelo.pojos.Rol;
import com.example.f1fan.modelo.pojos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import com.example.f1fan.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;
    private String email;
    private String passwd;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
            "(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +                     // no white spaces
            ".{8,}" +                              // at least 4 characters
            "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.anonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anonimo();
            }
        });

        binding.iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

    }


    public void logIn() {
        email = binding.editTextText.getText().toString();
        passwd = binding.editTextTextPassword.getText().toString();
        final boolean[] navegar = {false};

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(passwd).matches()){
            Toast.makeText(this, "Contraseña no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Usuario u = new Usuario();
                            u.setUsuario(user);
                            u.setEmail(email);
                            u.setPasswd(passwd);
                            DAOusuario d = new DAOusuario();
                            d.getRol(u);

                            navegar[0] = true;

                        } else {
                            if (task.getException().getMessage() == "The password is invalid or the user does not have a password.")
                                Toast.makeText(LoginActivity.this, "Contraseña o email incorrecto", Toast.LENGTH_SHORT).show();
                            else {
                                mAuth.createUserWithEmailAndPassword(email, passwd);
                                DAOusuario d = new DAOusuario();
                                Usuario u = new Usuario();
                                u.setEmail(email);
                                u.setPasswd(passwd);

                                d.registrarUsuario(u);

                                Log.d("::TAG", "" + task.getException().getMessage());
                            }
                        }
                        borrarCampos();
                    }
                });

        if (navegar[0]) {
            Intent i = new Intent(this, MainActivity.class);
            onActivityResult(0, 0, null);
            startActivity(i);
        }
    }

    public void borrarCampos() {
        binding.editTextTextPassword.setText("");
        binding.editTextText.setText("");
    }

    public void anonimo() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Usuario u = new Usuario();
                            u.setRol(Rol.ANONIMO);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });

    }

}