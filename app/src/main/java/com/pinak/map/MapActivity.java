package com.pinak.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "map";
    private GoogleMap mMap;
    private EditText et_langitude, et_latitude;
    private Button btn_setMarker;
    private double longitude = 23.0225, latitude = 72.5714;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        et_langitude = findViewById(R.id.et_lang);
        et_latitude = findViewById(R.id.et_lat);
        btn_setMarker = findViewById(R.id.btn_set_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_setMarker.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


//        longitude = Double.parseDouble(et_langitude.getText().toString().trim());
//      latitude = Double.parseDouble(et_latitude.getText().toString().trim());
        // Add a marker in Sydney and move the camera
        ;
    }

    @Override
    public void onClick(View view) {
        if (mMap != null){
            mMap.clear();
        }
        try{
            longitude = Double.parseDouble(et_langitude.getText().toString());
        } catch (final NumberFormatException e) {
            longitude = 1.0;
        }
        try{
            latitude = Double.parseDouble(et_latitude.getText().toString());
        } catch (final NumberFormatException e) {
            latitude = 1.0;
        }

        LatLng sydney = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //String Title = new MarkerOptions().position(sydney).getTitle();
        //Toast.makeText(this, "Location " + Title, Toast.LENGTH_SHORT).show();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        getAddress(this,longitude,latitude);
       /* Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null){
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            Toast.makeText(this, "Address : " +
                    "\n" + address +
                    "\n" + state +
                    "\n" + city +
                    "\n" + country +
                    "\n" + knownName , Toast.LENGTH_SHORT).show();
        }
*/
    }
    public static void getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                Toast.makeText(context, "Address : " + address
                        + "\n" + city  , Toast.LENGTH_SHORT).show();
                Log.d(TAG, "getAddress:  address" + address);
                Log.d(TAG, "getAddress:  city" + city);
                Log.d(TAG, "getAddress:  state" + state);
                Log.d(TAG, "getAddress:  postalCode" + postalCode);
                Log.d(TAG, "getAddress:  knownName" + knownName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}

