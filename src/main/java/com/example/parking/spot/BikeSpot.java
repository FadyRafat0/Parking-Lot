package com.example.parking.spot;

import com.example.parking.VehicleType;
import com.example.parking.json.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class BikeSpot extends Spot{
    public  BikeSpot(int id){
        super(id, BIKE_HOUR_RATE);
    }

    public VehicleType getSpotType() {
        return VehicleType.Bike;
    }

    public static void saveSpots(ArrayList<Spot> spots) {
        JSONUtils.saveToFile(spots, "bikeSpots.json");
    }
    public static ArrayList<Spot> loadSpots() {
        return JSONUtils.loadFromFile("bikeSpots.json", new com.google.gson.reflect.TypeToken<List<Spot>>() {}.getType());
    }
}