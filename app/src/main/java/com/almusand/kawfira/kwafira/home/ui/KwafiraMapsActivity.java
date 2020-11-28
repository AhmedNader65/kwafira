package com.almusand.kawfira.kwafira.home.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.almusand.kawfira.R;
import com.almusand.kawfira.kwafira.home.KwafiraMainActivity;
import com.almusand.kawfira.utils.PermissionUtils;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class KwafiraMapsActivity extends FragmentActivity implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "MAPPP";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private GoogleMap mMap;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private Marker myLocationMarker;
    private Polyline polyline;
    float zoom = 12f;
    int initialized = 0;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraMapsActivity.class);
        return intent;
    }

    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kwafira_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        mLocationRequest = createLocationRequest();
        mapFragment.getMapAsync(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.e("my current location",location.getLatitude()+"."+location.getLongitude());
                    showPointKwafiraPoint(new LatLng(location.getLatitude(),location.getLongitude()));
                }
            }
        };
        startLocationUpdates();
        checkForPermission();
    }

    private void checkForPermission() {

        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (mMap != null) {
                startLocationUpdates();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            EasyPermissions.requestPermissions(this, getString(R.string.permission_rationale_location), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(mLocationRequest,
                    locationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
//            Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showPointClientPoint();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(lat),
                        Double.valueOf(lng)));
        CameraUpdate zoomCam=CameraUpdateFactory.zoomTo(zoom);

        mMap.moveCamera(center);
        mMap.animateCamera(zoomCam);
    }

    private void moveToCurrentLocation(LatLng KwafiraLocation,String clientLat,String clientLng) {

        drawPath(KwafiraLocation, clientLat,clientLng);

    }


    private void drawPath(LatLng clientLocation, String clientLat,String clientLng) {

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAFE8D11s7CnPik7kvJsMXW4fVdgpmSDHc")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, clientLocation.latitude + "," + clientLocation.longitude , clientLat+","+clientLng);
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Log.e(TAG, ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
           if(polyline!=null){
               polyline.remove();
           }
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(10);
            polyline = mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        startLocationUpdates();
//        getMyLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    private void showPointClientPoint() {

        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.onmap_client));
        markerOptions.position(latLng);
        Marker locationMarker = mMap.addMarker(markerOptions);
        locationMarker.setDraggable(true);
        locationMarker.showInfoWindow();
    }


    private void showPointKwafiraPoint(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation_ic)).anchor(.5f,.5f);
        markerOptions.position(latLng);
        if(myLocationMarker==null) {
            myLocationMarker = mMap.addMarker(markerOptions);
            myLocationMarker.setDraggable(true);
            myLocationMarker.showInfoWindow();
        }else{
            myLocationMarker.setPosition(latLng);
        }
        moveToCurrentLocation(latLng,lat,lng);
    }


}