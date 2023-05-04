package com.example.f1fan.ui.recyclerView;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.f1fan.R;
import com.example.f1fan.databinding.FragmentFullscreenPilotoBinding;
import com.example.f1fan.modelo.BD;
import com.example.f1fan.modelo.DAO.DAOpiloto;
import com.example.f1fan.modelo.pojos.Piloto;
import com.example.f1fan.modelo.pojos.Rol;
import com.example.f1fan.modelo.pojos.Usuario;
import com.google.android.material.snackbar.Snackbar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenPiloto extends Fragment {

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private Piloto piloto;
    private DAOpiloto daoPiloto;
    private Drawable imagenPiloto;
    private EditText[] datos;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private FragmentManager fragmentManager;

    public FullscreenPiloto(Piloto piloto, Drawable drawable, FragmentManager fragmentManager, DAOpiloto dao) {
        this.piloto = piloto;
        imagenPiloto = drawable;
        this.fragmentManager = fragmentManager;
        daoPiloto = dao;
    }
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

            Activity activity = getActivity();
            if (activity != null
                    && activity.getWindow() != null) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            }
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }

        }
    };
    private View mContentView;
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private FragmentFullscreenPilotoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFullscreenPilotoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVisible = true;

        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.layoutPilotoFull;

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        binding.nombreEdit.setText(piloto.getNombre());
        binding.apellidoEdit.setText(piloto.getApellidos());
        binding.edadEdit.setText(String.valueOf(piloto.getEdad()));
        binding.equipoEdit.setText(piloto.getEquipo());
        binding.puntosEdit.setText(String.valueOf(piloto.getPuntos()));
        binding.gpEdit.setText(String.valueOf(piloto.getGp_terminados()));
        binding.victoriasEdit.setText(String.valueOf(piloto.getVictorias()));
        binding.polesEdit.setText(String.valueOf(piloto.getPole_positions()));
        binding.podiosEdit.setText(String.valueOf(piloto.getPodios()));

        if (Usuario.getRol() != Rol.ADMIN) {
            binding.nombreEdit.setFocusable(false);
            binding.apellidoEdit.setFocusable(false);
            binding.edadEdit.setFocusable(false);
            binding.equipoEdit.setFocusable(false);
            binding.puntosEdit.setFocusable(false);
            binding.gpEdit.setFocusable(false);
            binding.victoriasEdit.setFocusable(false);
            binding.polesEdit.setFocusable(false);
            binding.podiosEdit.setFocusable(false);
        } else {
            binding.guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    piloto.setNombre(binding.nombreEdit.getText().toString());
                    piloto.setApellidos(binding.apellidoEdit.getText().toString());
                    piloto.setEdad(Integer.parseInt(binding.edadEdit.getText().toString()));
                    piloto.setEquipo(binding.equipoEdit.getText().toString());
                    piloto.setPuntos(Float.parseFloat(binding.puntosEdit.getText().toString()));
                    piloto.setGp_terminados(Integer.parseInt(binding.gpEdit.getText().toString()));
                    piloto.setVictorias(Integer.parseInt(binding.victoriasEdit.getText().toString()));
                    piloto.setPole_positions(Integer.parseInt(binding.polesEdit.getText().toString()));
                    piloto.setPodios(Integer.parseInt(binding.podiosEdit.getText().toString()));
                    Log.d("piloto", "" + piloto.getId());

                    daoPiloto.modificaPiloto(piloto);

                    cerrarFragment();
                }
            });
        }

        binding.imageView2.setImageDrawable(imagenPiloto);
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.


        binding.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarFragment();
            }
        });


    }

    private void cerrarFragment() {
        //fragmentManager.saveBackStack("piloto");
        fragmentManager.beginTransaction().remove(this).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            // Clear the systemUiVisibility flag
            getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        }
        show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContentView = null;
        mControlsView = null;
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Nullable
    private ActionBar getSupportActionBar() {
        ActionBar actionBar = null;
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            actionBar = activity.getSupportActionBar();
        }
        return actionBar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}