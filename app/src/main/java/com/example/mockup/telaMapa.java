package com.example.mockup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class telaMapa extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout drawer;

    private GoogleMap mMap;
    //declara variaveis para permissão location
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-29.832428, -51.122184);
        LatLng Advogado = new LatLng(-29.836957, -51.131815);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sapucaia do Sul").icon(BitmapDescriptorFactory.fromResource(R.drawable.home)));
        mMap.addMarker(new MarkerOptions().position(Advogado).title("Marker in Advogado").icon(BitmapDescriptorFactory.fromResource(R.drawable.advogado)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //setar o zoom do mapa
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
        //mostra controles do zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //verifica se é permitido ao aplicativo pegar a localização atual do dispositivo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //caso ainda não tenha sido dada a permissão, solicitar a permissão
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
            }
        }

        //adiciona rota
        mMap.addPolyline(new PolylineOptions()
                .add(sydney, Advogado)
                .width(5)
                .color(Color.RED));

        mMap.addCircle(
                new CircleOptions()
                .center(sydney)
                .radius(1580.0)
                .strokeWidth(3f)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70,150,50,50))


        );
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

    }
}
