package com.example.parking.spot;

import com.example.parking.VehicleType;
import com.example.parking.json.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class TruckSpot extends Spot{
    public TruckSpot (int id){
        super(id, TRUCK_HOUR_RATE);
    }
    public VehicleType getSpotType() {
        return VehicleType.Truck;
    }

    public static void saveSpots(ArrayList<Spot> spots) {
        JSONUtils.saveToFile(spots, "truckSpots.json");
    }
    public static ArrayList<Spot> loadSpots() {
        return JSONUtils.loadFromFile("truckSpots.json", new com.google.gson.reflect.TypeToken<List<Spot>>() {}.getType());
    }

}