package com.example.f1fan.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.f1fan.R;
import com.example.f1fan.Utils;
import com.example.f1fan.modelo.pojos.BDestatica;
import com.example.f1fan.modelo.pojos.Circuito;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d("::TAG", "hola mapa000");
            ArrayList<Circuito> c = BDestatica.getCircuitos();
            int verde = 0;
            int azul = 0;
            int alpha = 255;

            for (int i = 0; i < c.size(); i++) {
                LatLng l = new LatLng(c.get(i).getLat(), c.get(i).getLon());

                if (i < c.size() -1) {
                    LatLng l2 = new LatLng(c.get(i + 1).getLat(), c.get(i + 1).getLon());
                    googleMap.addPolyline(new PolylineOptions().add(l, l2).width(10).color(Color.argb(alpha, 255, verde, azul)).jointType(JointType.ROUND));
                }

                googleMap.addMarker(new MarkerOptions().position(l).title(c.get(i).getNombre()));
                verde += 10;
                azul += 15;
                alpha -= 5;
                Log.d("::TAG", "" + c.get(i).getNombre());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar t = getActivity().findViewById(R.id.toolbar);
        t.setTitle("Mapa de circuitos");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.botones(getActivity());
        Toolbar t = getActivity().findViewById(R.id.toolbar);
        t.setTitle("Mapa de circuitos");
    }
}