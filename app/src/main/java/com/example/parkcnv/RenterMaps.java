package com.example.parkcnv;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

public class RenterMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> spotNames = new ArrayList<>();
    private ArrayList<Integer> spotImages = new ArrayList<>();
    private ArrayList<Float> spotDistances = new ArrayList<>();
    private ArrayList<Integer>spotPrices = new ArrayList<>();
    private static  final int REQUEST_LOCATION=1;
    String latitude,longitude;
    double lat ,longi;
    TextView withinRange;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.renter_map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        assignValuesTolists();
        initRecyclerView();
        SeekBar seekBar = findViewById(R.id.SeekBar);
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double progress1 = (seekBar.getProgress())/10;
                // updated continuously as the user slides the thumb
                withinRange.setText("Within Range "+progress1+" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
                double progress1 = seekBar.getProgress()/10;
                withinRange.setText("Within Range "+progress1+" km");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
            }
        };
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        withinRange = findViewById(R.id.within_range);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            getLocation();
        }


    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(RenterMaps.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RenterMaps.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                lat=LocationGps.getLatitude();
                longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);


            }
            else if (LocationNetwork !=null)
            {
                lat=LocationNetwork.getLatitude();
                longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);


            }
            else if (LocationPassive !=null)
            {
                lat=LocationPassive.getLatitude();
                longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);


            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



    private void assignValuesTolists() {
        spotNames.add("Basement Spot 1");
        spotNames.add("Basement spot 2");
        spotNames.add("Basement spot 3");

        spotPrices.add(25);
        spotPrices.add(35);
        spotPrices.add(15);

        spotImages.add(R.drawable.parking_spot_one);
        spotImages.add(R.drawable.parking_spot_three);
        spotImages.add(R.drawable.parking_spot_two);

        spotDistances.add(0.5f);
        spotDistances.add(0.3f);
        spotDistances.add(0.2f);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView =findViewById(R.id.spots_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        Context mcontext = this;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this,spotNames,spotImages,spotDistances, spotPrices,mcontext);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLng currentPosition = new LatLng(lat,longi);
        googleMap.addMarker(new MarkerOptions().position(currentPosition).title("Current Position"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,13));

       /* LatLng parkingSpot1 = new LatLng(longi,lat);
        googleMap.addMarker(new MarkerOptions().position(parkingSpot1).title("Parking Spot 1"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkingSpot1,13));*/
        LatLng parkingSpot2 = new LatLng((longi+0.1),(lat+0.2));
        googleMap.addMarker(new MarkerOptions().position(parkingSpot2).title("Parking spot 2"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkingSpot2,13));




    }
}
