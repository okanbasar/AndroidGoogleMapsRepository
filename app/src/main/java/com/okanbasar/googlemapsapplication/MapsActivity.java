package com.okanbasar.googlemapsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final float ZOOM = 17.0F; //default: 0 (WorldView) +closeToGround (no negative values)
    private static final float TILT = 30.0F; //default: 0 (DirectlytoUp) +closeToGround (no negative values) in degrees
    private static final float BEARING = 0.0F; //default: 0 (DirectlytoNorth) +clockwise -counterclockwise in degrees
    private GoogleMap mMap;
    private String title;
    private LatLng latLngPos;

    private float minZoomLevel;
    private float maxZoomLevel;
    private float mapType;
    private IndoorBuilding focusedBuilding;
    private UiSettings uiSettings;

    /**
     * An activity that displays a Google map with a marker (pin) to indicate a particular location.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();

        title = extras.get("title").toString();
        String latitude = extras.get("latitude").toString();
        String longitude = extras.get("longitude").toString();
        latLngPos = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        minZoomLevel = mMap.getMinZoomLevel();
        maxZoomLevel = mMap.getMaxZoomLevel();
        mapType = mMap.getMapType();
        focusedBuilding = mMap.getFocusedBuilding();
        uiSettings = mMap.getUiSettings();
        addMarkers();
        setListeners();
    }

    private void addMarkers() {
        // Add a marker and move the camera.
        mMap.addMarker(new MarkerOptions()
                        .position(latLngPos)
                        .title(title)
                //.zIndex(0.0F) //default
        ).showInfoWindow();
        mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLngPos.latitude+0.01,latLngPos.longitude+0.01))
                        .title(title + " 2")
                //.zIndex(10.0F) //10 unit up
        ).showInfoWindow();
        mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLngPos.latitude+0.02,latLngPos.longitude+0.02))
                        .title(title + " 3")
                        .draggable(true)
                //.zIndex(20.0F) //20 unit up
        ).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory
                //.newLatLng(latLngPos)
                //.newLatLngZoom(latLngPos,ZOOM)
                .newCameraPosition(new CameraPosition(latLngPos,ZOOM,TILT,BEARING))
        );
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setTrafficEnabled(true);
    }

    private void setListeners() {
        //MARKER
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("GoogleMap","OnMarkerClickListener.onMarkerClick");
                return false;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.e("GoogleMap","OnMarkerDragListener.onMarkerDragStart\n"+marker.getTitle()+"-"+marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.e("GoogleMap","OnMarkerDragListener.onMarkerDrag\n"+marker.getTitle()+"-"+marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e("GoogleMap","OnMarkerDragListener.onMarkerDragEnd\n"+marker.getTitle()+"-"+marker.getPosition());
            }
        });
        //MARKER

        //MAP
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e("GoogleMap","OnMapClickListener.onMapClick");
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.e("GoogleMap","OnMapLongClickListener.onMapLongClick");
            }
        });
        //MAP

        //CAMERA
        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                //Kamera hareketi basladi
                Log.e("GoogleMap","OnCameraMoveStartedListener.onCameraMoveStarted");
            }
        });
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                //Kamera hareketi devam ediyor
                //Log.e("GoogleMap","OnCameraIdleListener.onCameraMove");
            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //Kamera hareketi tamamlandi
                Log.e("GoogleMap","OnCameraIdleListener.onCameraIdle");
            }
        });
        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                Log.e("GoogleMap","OnCameraMoveCanceledListener.onCameraMoveCanceled");
            }
        });
        //CAMERA

        //INFO WINDOW
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.e("GoogleMap","OnInfoWindowClickListener.onInfoWindowClick");
            }
        });
        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Log.e("GoogleMap","OnInfoWindowLongClickListener.onInfoWindowLongClick");
            }
        });
        mMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                Log.e("GoogleMap","OnInfoWindowCloseListener.onInfoWindowClose");
            }
        });
        //INFO WINDOW

        //OTHERS
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                Log.e("GoogleMap","OnCircleClickListener.onCircleClick");
            }
        });
        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {
                Log.e("GoogleMap","OnPoiClickListener.onPoiClick");
            }
        });
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                Log.e("GoogleMap","OnPolygonClickListener.onPolygonClick");
            }
        });
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Log.e("GoogleMap","OnPolylineClickListener.onPolylineClick");
            }
        });
        mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                Log.e("GoogleMap","OnGroundOverlayClickListener.onGroundOverlayClick");
            }
        });
        mMap.setOnIndoorStateChangeListener(new GoogleMap.OnIndoorStateChangeListener() {
            @Override
            public void onIndoorBuildingFocused() {
                Log.e("GoogleMap","OnIndoorStateChangeListener.onIndoorBuildingFocused");
            }

            @Override
            public void onIndoorLevelActivated(IndoorBuilding ındoorBuilding) {
                Log.e("GoogleMap","OnIndoorStateChangeListener.onIndoorLevelActivated");
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.e("GoogleMap","OnMyLocationButtonClickListener.onMyLocationButtonClick");
                return false;
            }
        });
        //OTHERS
    }
}
