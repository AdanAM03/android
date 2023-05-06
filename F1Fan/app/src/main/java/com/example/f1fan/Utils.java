package com.example.f1fan;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.f1fan.modelo.DAO.DAOnoticia;
import com.example.f1fan.modelo.DAO.DAOtemporada;
import com.example.f1fan.modelo.pojos.Rol;
import com.example.f1fan.modelo.pojos.Usuario;
import com.example.f1fan.ui.noticiaView.NuevaNoticiaFragment;
import com.example.f1fan.ui.temporadaView.NuevaTemporadaFragment;
import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void botones(FragmentActivity a) {
        a.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        if (a.findViewById(R.id.add).isEnabled())
            a.findViewById(R.id.add).setVisibility(View.INVISIBLE);
        a.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Usuario.getRol() == Rol.ANONIMO)
                    Snackbar.make(view, "Para un acceso completo registrese en la app", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Todos los días nuevas noticias en F1 Fan", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    public static void botonesPiloto(FragmentActivity a) {
        a.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        if (a.findViewById(R.id.add).isEnabled())
            a.findViewById(R.id.add).setVisibility(View.INVISIBLE);

        a.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Para ver toda la info pulsa sobre un piloto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void botonesHistorico(FragmentActivity a) {
        a.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        if (Usuario.getRol() == Rol.ADMIN) {
            a.findViewById(R.id.add).setVisibility(View.VISIBLE);
            a.findViewById(R.id.add).setEnabled(true);
            a.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = a.getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
                    ft.replace(R.id.drawer_layout, new NuevaTemporadaFragment(a.getSupportFragmentManager(), new DAOtemporada()));
                    ft.addToBackStack(null);
                    ft.setReorderingAllowed(true).commit();
                }
            });
        }

        a.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Usuario.getRol() == Rol.ANONIMO)
                    Snackbar.make(view, "Para un acceso completo registrese en la app", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Todos los días nuevas noticias en F1 Fan", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    public static void botonesMapa(FragmentActivity a) {
        a.findViewById(R.id.add).setVisibility(View.INVISIBLE);
        a.findViewById(R.id.fab).setVisibility(View.INVISIBLE);
    }

    public static void botonesNoticia(FragmentActivity a) {
        a.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        if (Usuario.getRol() == Rol.ADMIN) {
            a.findViewById(R.id.add).setVisibility(View.VISIBLE);
            a.findViewById(R.id.add).setEnabled(true);
            a.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = a.getSupportFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
                    ft.replace(R.id.nav_host_fragment_content_main, new NuevaNoticiaFragment(a.getSupportFragmentManager(), new DAOnoticia()));
                    ft.addToBackStack(null);
                    ft.setReorderingAllowed(true).commit();
                }
            });
        }

        a.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Usuario.getRol() == Rol.ANONIMO)
                    Snackbar.make(view, "Para un acceso completo registrese en la app", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Todos los días nuevas noticias en F1 Fan", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }
}
