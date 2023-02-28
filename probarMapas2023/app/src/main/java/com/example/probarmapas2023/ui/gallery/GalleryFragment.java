package com.example.probarmapas2023.ui.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;



import com.example.probarmapas2023.R;

import com.example.probarmapas2023.databinding.FragmentGalleryBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GalleryFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private FragmentGalleryBinding binding;
    private GoogleMap map;
    private LatLng previa, siguiente, ultima;
    private double distancia, distanciaTotal;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationManager locationManager;
    private String provider;
    private List<Address> direccion = null;
    private Polyline ruta;
    private Polyline polyline;
    private ArrayList<LatLng> posiciones = new ArrayList<>();
    private Location referencia;
    private Location location;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerPosicion(view);
            }
        });
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);



        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        obtenerPermisos();
        pos();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        previa = new LatLng(-13, 13);
        map.addMarker(new MarkerOptions().position(previa).title("Sevilla"));
        map.moveCamera(CameraUpdateFactory.newLatLng(previa));
        posiciones.add(previa);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng ultima) {
                posiciones.add(ultima);
                if (ultima != null) {
                    distancia = SphericalUtil.computeDistanceBetween(ultima, previa);
                    distancia /= 1000;
                    distanciaTotal += distancia;
                    double rumbo=SphericalUtil.computeHeading(previa, ultima);
                    if (rumbo<0)rumbo+=360;
                    previa = ultima;
                    MarkerOptions markerOptions = new MarkerOptions().position(ultima);
                    map.addMarker(markerOptions);

                    DecimalFormat df = new DecimalFormat("#.##");
                    String distanciaFormat = df.format(distancia);

                    Toast.makeText(getContext(), "Distancia: " + distanciaFormat+ "Rumbo: "+rumbo, Toast.LENGTH_SHORT).show();

                    PolylineOptions polylineOptions = new PolylineOptions().addAll(posiciones).color(Color.RED);
                    map.addPolyline(polylineOptions);


                }
            }
        });
    }

    public void obtenerPosicion(View view) {
        Log.d("sexo", "posicion " + view.toString());
        obtenerPermisos();
        pos();
    }


    @SuppressLint("MissingPermission")
    private void pos() {
        //Inicializar el manager que nos va a dar la geoposición en base al GPS

        //Se usa la clase Criteria para obtener el mejor proveedor de localización
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //false se establece para que no esté activo permanentemente
        provider = locationManager.getBestProvider(criteria, false);

        // Se verifica si la aplicación tiene los permisos para acceder
        // a la ubicación del dispositivo (ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION).
        // Si no tiene permisos, se solicita al usuario que los permita mediante
        // la función requestPermissions()
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000
            );

            //map.setMyLocationEnabled(true);
            if (locationManager.isProviderEnabled(provider)) {
       //         referencia = locationManager.getLastKnownLocation(provider);
            }
        }
        referencia = locationManager.getLastKnownLocation(provider);
        //Obtenemos la primera localización que nos sirve de referencia

        previa = new LatLng(referencia.getLatitude(), referencia.getLongitude());
        map.addMarker(new MarkerOptions().position(previa).title("Sevilla"));
        map.moveCamera(CameraUpdateFactory.newLatLng(previa));
        posiciones.add(previa);
        Log.d("aaaaa", "" + referencia.getLatitude() + " - " + referencia.getLongitude());
    }


    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        //Inicializar el manager que nos va a dar la geoposición en base al GPS
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        //Se usa la clase Criteria para obtener el mejor proveedor de localización
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //false se establece para que no esté activo permanentemente
        provider = locationManager.getBestProvider(criteria, false);

        obtenerPermisos();
        // requestLocationUpdates registra un "listener" para recibir actualizaciones de la ubicación.
        // el intervalo de tiempo para actualizar argumentos (en milisegundos)
        // y la distancia mínima que debe recorrer el usuario para que se genere una actualización (en metros).
        // Finalmente, la actividad actual se pasa como argumento, ya que implementa la interface "LocationListener",
        // para recibir las actualizaciones de ubicación.
        locationManager.requestLocationUpdates(provider, 500 /*milisegundos de update*/, 1 /*metros de recorrido del usuario*/, this);
    }


    @Override
    //Evento de cálculo de la nueva posición
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        this.location = location;

        //Para calcular distancias entre dos puntos en metros
        //float distancia=location.distanceTo(referencia);
        //Log.d("/posicionamiento"," "+distancia);

        //Paquete de Android para resolver coordenadas a partir de una dirección y
        //viceversa
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

        direccion = null;
        try {
            int x = 0;
            direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Altitud en metros sobre el nivel del mar
        //double alt = location.getAltitude();

/*        txtLat.setText(String.valueOf(lat));
        txtLong.setText(String.valueOf(lng));
        txtSrc.setText("Source = "+provider);
        assert direccion != null;
        txtDir.setText(direccion.get(0).getAddressLine(0));
*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        //txtSrc.setText("Source = "+provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        //txtSrc.setText("Source = "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //txtSrc.setText("Source = "+provider);
    }


    private void obtenerPermisos() {
        // Se verifica si la aplicación tiene los permisos para acceder
        // a la ubicación del dispositivo (ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION).
        // Si no tiene permisos, se solicita al usuario que los permita mediante
        // la función requestPermissions()
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000
            );

        }
    }
}






