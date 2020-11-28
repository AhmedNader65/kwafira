package com.almusand.kawfira.ui.map.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragHomeBinding;
import com.almusand.kawfira.utils.PermissionUtils;

import com.github.islamkhsh.BR;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends BaseFragment<FragHomeBinding, MapViewModel> implements OnMapReadyCallback, MapsNavigator {

    // New variables for Current Place Picker
    private static final String TAG = "MapsActivity";
    FragHomeBinding binding;
    MapViewModel viewModel;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public GoogleMap map;
    private boolean permissionDenied;
    onAddressConfirmed mListner;
    String latStr,lngStr;
    int hasOrder = 0;

    public MapFragment(onAddressConfirmed mListner,int hasOrder) {
        this.mListner = mListner;
        this.hasOrder = hasOrder;

    }

    public MapFragment() {

    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    public MapViewModel getViewModel() {
        viewModel = ViewModelProviders.of(requireActivity()).get(MapViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Bundle bundle = getArguments();
            this.hasOrder = bundle.getInt("x");

        }catch (Exception e){

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        try {
            enableMyLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(hasOrder!=0) {
            map.getUiSettings().setAllGesturesEnabled(false);
        }else {
            map.setOnMapClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinaddressicon));
                markerOptions.position(latLng);

                map.clear();

                Geocoder gcd = new Geocoder(getContext(), new Locale("ar"));
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    binding.address.setText(addresses.get(0).getAddressLine(0).replaceAll("\\d", "").replace("Unnamed Road,", ""));
                    latStr = latLng.latitude + "";
                    lngStr = latLng.longitude + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (addresses != null) {
                    if (addresses.size() > 0) {
                        markerOptions.title(addresses.get(0).getLocality());

                    } else {
                        // do your stuff
                    }
                }
                Marker locationMarker = map.addMarker(markerOptions);
                locationMarker.setDraggable(true);
                locationMarker.showInfoWindow();

            });
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                getMyLocation();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            showMissingPermissionError();
        }
    }

    private void getMyLocation() {
        Criteria criteria = new Criteria();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // TODO: Check if location is enabled

        Log.e("getting location", "yes");
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng myLocation = new LatLng(latitude,
                longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                16));
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getChildFragmentManager(), "dialog");
        permissionDenied = false;
    }


    @Override
    public void showToast(String validateText) {
        Toast.makeText(getContext(), validateText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showType(String address, String homeNum, String apartNum) {
        map.getUiSettings().setAllGesturesEnabled(false);
        viewModel.confirmAddress(binding.address.getText().toString(),binding.home.getText().toString(),binding.apartment.getText().toString(),latStr,lngStr);
        binding.userChoiceDialog.setVisibility(View.GONE);
    }

    @Override
    public void showType(String address, String homeNum, String apartNum, String lat, String lng) {

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.userChoiceDialog.setVisibility(View.VISIBLE);
        if(hasOrder!=0){
            binding.userChoiceDialog.setVisibility(View.GONE);
            Log.e("has order",hasOrder+"");
        }
    }

    public interface onAddressConfirmed{

        void onAddressClicked(String address,String homeNum,String apart,String lat,String lng);
    }
}
