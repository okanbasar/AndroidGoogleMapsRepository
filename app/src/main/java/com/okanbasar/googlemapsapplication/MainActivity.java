package com.okanbasar.googlemapsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private static final float ZOOM = 17.0F; //default: 0 (WorldView) +closeToGround (no negative values)
    private static final float TILT = 45.0F; //default: 0 (DirectlytoUp) +closeToGround (no negative values) in degrees
    private static final float BEARING = 0.0F; //default: 0 (DirectlytoNorth) +clockwise -counterclockwise in degrees
    private GoogleMap mMap;
    private String title;
    private LatLng latLngPos;

    EditText titleText, latitudeText, longitudeText;
    Button showButton;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (EditText) findViewById(R.id.titleText);
        latitudeText = (EditText) findViewById(R.id.latitudeText);
        longitudeText = (EditText) findViewById(R.id.longitudeText);
        showButton = (Button) findViewById(R.id.showButton);
        mapView = (MapView) findViewById(R.id.mapView);

        title = "Home";
        String latitude = "41.04711496";
        String longitude = "28.79502118";
        latLngPos = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        setButtonOnClickListener(showButton);
        initMapView(mapView, savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initMapView(MapView mapView, Bundle savedInstanceState) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.addMarker(new MarkerOptions()
                                .position(latLngPos)
                                .title(title)
                        //.zIndex(0.0F) //default
                ).showInfoWindow();

                mMap.moveCamera(CameraUpdateFactory
                        //.newLatLng(latLngPos)
                        //.newLatLngZoom(latLngPos,ZOOM)
                        .newCameraPosition(new CameraPosition(latLngPos,ZOOM,TILT,BEARING))
                );
            }
        });
        mapView.onCreate(savedInstanceState);
    }

    private void setButtonOnClickListener(Button showButton) {
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,MapsActivity.class);
                i.putExtra("title",titleText.getText().toString().trim());
                i.putExtra("latitude",latitudeText.getText().toString().trim());
                i.putExtra("longitude",longitudeText.getText().toString().trim());
                startActivity(i);
            }
        });
    }
}
