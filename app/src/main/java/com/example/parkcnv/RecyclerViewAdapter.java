package com.example.parkcnv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> spotNames = new ArrayList<>();
    private ArrayList<Integer> spotImages = new ArrayList<>();
    private ArrayList<Float> spotDistances = new ArrayList<>();
    private ArrayList<Integer>spotPrices = new ArrayList<>();


    public RecyclerViewAdapter(RenterMaps renterMaps, ArrayList<String> spotNames, ArrayList<Integer> spotImages, ArrayList<Float> spotDistances, ArrayList<Integer> spotPrices) {
        this.spotPrices = spotPrices;
        this.spotDistances = spotDistances;
        this.spotImages = spotImages;
        this.spotNames = spotNames;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spots_individual_design,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.spot_name.setText(spotNames.get(position));
        holder.parking_spot_image.setImageResource(spotImages.get(position));
        holder.distance_from_destination.setText(""+spotDistances.get(position));
        holder.price_for_the_spot.setText(""+spotPrices.get(position));

        holder.parking_spot_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }

    @Override
    public int getItemCount() {
        return spotImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView parking_spot_image;
        TextView spot_name , distance_from_destination,price_for_the_spot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.parking_spot_image = itemView.findViewById(R.id.parking_spot_image);
            this.spot_name = itemView.findViewById(R.id.spot_name);
            this.distance_from_destination = itemView.findViewById(R.id.distance_from_destination);
            this.price_for_the_spot = itemView.findViewById(R.id.price_for_the_spot);
        }
    }

}

