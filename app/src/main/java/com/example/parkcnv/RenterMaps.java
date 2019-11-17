package com.example.parkcnv;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    }

    private void assignValuesTolists() {
        spotNames.add("Basement Spot1");
        spotNames.add("Basement spot 2");
        spotNames.add("Outdoor spot 1");

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
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this,spotNames,spotImages,spotDistances, spotPrices);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
