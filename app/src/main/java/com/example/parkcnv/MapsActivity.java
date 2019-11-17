package com.example.parkcnv;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static  final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    String latitude,longitude;
    double lat ,longi;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText spotStartDate,spotEndDate;
    Button moveToAnotherActivityButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rente_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        spotStartDate = findViewById(R.id.spot_available_start_date);
        spotEndDate=findViewById(R.id.spot_available_end_date);
        moveToAnotherActivityButton = findViewById(R.id.proceed_to_docs_and_time_details_button);
        moveToAnotherActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,DocsAndTime.class);
                startActivity(intent);
            }
        });





        //Check gps is enable or not

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
    public void setStartDatePicker(View view){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth=c.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                spotStartDate.setText(dayOfMonth + "/" + month + "/" + year);

            }
        },mYear,mMonth,mDay);

        datePickerDialog.show();



    }
    public void setEndDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                spotEndDate.setText(dayOfMonth + "/" + ((month+2)%11) + "/" + year);

            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,

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


    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        LatLng currentPosition = new LatLng(lat,longi);
        googleMap.addMarker(new MarkerOptions().position(currentPosition).title("Current Position"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,11));
    }
}