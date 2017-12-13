package com.example.android.park;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.park.utilities.JsonUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.android.park.utilities.NetworkUtils;
import com.example.android.park.utilities.JsonUtils;
import com.example.android.park.models.ParkInfo;

/**
 * Created by dell on 29-Nov-17.
 */



public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCAION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Marker mMarker;
    private FusedLocationProviderClient mFuesdLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private LatLng park;
    public ParkInfo parkFinal;

    private static final String usrName = "rahul";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map);



//        mGps = (ImageView) findViewById(R.id.ic_gps);

        getLocationPermission();
        makeQuery();
    }

    private void makeQuery(){
        Log.d(TAG, "makeQuery: Query made");
        URL url = NetworkUtils.buildUrl(usrName, "find");
        new Login().execute(url);
    }

    private void placeMarker(Double x, Double y, int id, ParkInfo parkInfo){
        park = new LatLng(x, y);
        parkFinal = parkInfo;
        mMarker = mMap.addMarker(new MarkerOptions().position(park).title("Parking").snippet(parkInfo.getAvail() + " Plots available. Tap to Book!"));
        mMarker.setTag(id);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(park));
        mMap.setOnMarkerClickListener(this);


    }

    public ParkInfo getParkInfo(){
        try{
            return parkFinal;
        } catch (NullPointerException e){
            Log.e(TAG, "getParkInfo: parkFinal is null" + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: Marker Clicked!!");
//        Integer markerId = (Integer) marker.getTag();
        marker.showInfoWindow();
        mMap.setOnInfoWindowClickListener(this);
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.d(TAG, "onInfoWindowClick: InfoWindow Clicked!!");

        ParkInfo temp = getParkInfo();

        if(Integer.parseInt(temp.getAvail())!= 0) {
            Log.d(TAG, "onInfoWindowClick: changing activity");
            Intent myIntent = new Intent(MapActivity.this, BookPark.class);
            startActivityForResult(myIntent, 0);
        } else {
            marker.hideInfoWindow();
        }
    }

    public class Login extends AsyncTask<URL, Void, ParkInfo> {


        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: Login class called");
        }

        @Override
        protected ParkInfo doInBackground(URL... urls) {
            Log.d(TAG, "doInBackground: making http request");
            URL searchUrl = urls[0];
            String parkJson = null;
            try {
                 parkJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);



                 ParkInfo mParkInfo = JsonUtils
                         .getInfo(MapActivity.this, parkJson);

//                mParkInfo.setParkId("24.5968106");
//                mParkInfo.setParkId("73.7236456");

                Log.d(TAG, "doInBackground: parsed JSON: " + mParkInfo.toString());

                 return mParkInfo;

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: error between request n receive" + e.getMessage() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(ParkInfo parkInfo) {

            placeMarker(Double.parseDouble(parkInfo.getLat()),Double.parseDouble(parkInfo.getLon()),Integer.parseInt(parkInfo.getParkId()), parkInfo);
//            park = new LatLng(Double.parseDouble(parkInfo.getLat()), Double.parseDouble(parkInfo.getLon()));
//            mMarker = mMap.addMarker(new MarkerOptions().position(park));
//            mMarker.setTag(0);

        }
    }

    private void initMap(){
        Log.d(TAG, "initMap: initalizing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(grantResults.length>0)
                for( int i = 0; i<grantResults.length; i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        Log.d(TAG, "onRequestPermissionsResult: permission failed");
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                mLocationPermissionsGranted = true;
                initMap();
            }
        }
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCAION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

        initMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            placeMarker(24.5968106, 73.7236456,1);
//            init();
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting device's current location");

        mFuesdLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                Task locationResult = mFuesdLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Log.e(TAG, "onComplete: %s", task.getException());

                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch(SecurityException e) {
            Log.e(TAG, "getDeviceLocation: " +e.getMessage());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
//
//    private void init(){
//        Log.d(TAG, "init: initializing");
//
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Place.PLACE_DETECTION_API)
//                .enabeAutoManage(this, this)
//                .build();
//    }
}
