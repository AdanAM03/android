package com.example.f1fan;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;

import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.pojos.Rol;
import com.example.f1fan.modelo.pojos.Usuario;
import com.example.f1fan.ui.recyclerView.MypilotoRecyclerViewAdapter;
import com.example.f1fan.ui.recyclerView.pilotoFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.f1fan.databinding.ActivityMainBinding;
import com.google.rpc.context.AttributeContext;

public class MainActivity extends AppCompatActivity {
    private BD bd = new BD();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Usuario user = new Usuario(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user.setRol(Rol.ADMIN);

        bd.getPilotos();
        bd.getEquipos();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        //if (user.getRol() == Rol.ANONIMO) {
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Para un acceso completo registrese en la app", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            //setContentView(R.layout.fragment_piloto_list);

        //} else {

            DrawerLayout drawer = binding.drawerLayout;

            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.inicio, R.id.nav_equipoFragment, R.id.nav_pilotoFragment, R.id.slideshowFragment)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}