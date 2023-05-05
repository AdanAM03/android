package com.example.f1fan;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.DAO.DAORanking;
import com.example.f1fan.modelo.DAO.DAOcircuito;
import com.example.f1fan.modelo.DAO.DAOequipo;
import com.example.f1fan.modelo.DAO.DAOnoticia;
import com.example.f1fan.modelo.DAO.DAOpiloto;
import com.example.f1fan.modelo.DAO.DAOtemporada;
import com.example.f1fan.modelo.pojos.Rol;
import com.example.f1fan.modelo.pojos.Usuario;
import com.example.f1fan.ui.recyclerView.MypilotoRecyclerViewAdapter;
import com.example.f1fan.ui.recyclerView.pilotoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.f1fan.databinding.ActivityMainBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.rpc.context.AttributeContext;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Usuario user = new Usuario(null);

    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user.setRol(Rol.ADMIN);

        if (user.getRol() != Rol.ANONIMO) {
            DAOtemporada daOtemporada = new DAOtemporada();
            DAOequipo daOequipo = new DAOequipo();
            DAOpiloto daOpiloto = new DAOpiloto();
            daOpiloto.getPilotos();
            daOequipo.getEquipos();
            daOtemporada.getTemporadas();
            new DAORanking().getRanking();
            new DAOcircuito().getCircuitos();
        }
        new DAOnoticia().getNoticias();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setSupportActionBar(binding.appBarMain.toolbar);

        //if (user.getRol() == Rol.ANONIMO) {
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Usuario.getRol() == Rol.ANONIMO)
                    Snackbar.make(view, "Para un acceso completo registrese en la app", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (Usuario.getRol() == Rol.REGISTRADO)
                    Snackbar.make(view, "Todos los días nuevas noticias en F1 Fan", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
        findViewById(R.id.add).setVisibility(View.INVISIBLE);
            //setContentView(R.layout.fragment_piloto_list);

        //} else {

            DrawerLayout drawer = binding.drawerLayout;

            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.inicio, R.id.slideshowFragment, R.id.slideshowFragment2, R.id.noticiaFragment, R.id.mapsFragment)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            Toolbar t = (Toolbar) findViewById(R.id.toolbar);
            t.setTitle("Inicio");

        //}
        notificaciones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.setGroupEnabled(1, false);
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        if (user.getRol() == Rol.ANONIMO) {
            findViewById(R.id.slideshowFragment2).setEnabled(false);
            findViewById(R.id.slideshowFragment).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void notificaciones() {
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d("::TAG", msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("::TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast

                        Log.d("::TAG", token.toString());
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }



}