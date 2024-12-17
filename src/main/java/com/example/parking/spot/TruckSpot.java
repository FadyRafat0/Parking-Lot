package com.example.parking.spot;

import com.example.parking.VehicleType;
import com.example.parking.json.JSONUtils;

import java.util.ArrayList;

public class TruckSpot extends Spot{
    public TruckSpot (int id){
        super(id, TRUCK_HOUR_RATE);
    }
    public VehicleType getVehicleType() {
        return VehicleType.Truck;
    }

    public static void saveSpots(ArrayList<TruckSpot> spots) {
        JSONUtils.saveToFile(spots, "truckSpots.json");
    }
    public static ArrayList<TruckSpot> loadSpots() {
        return JSONUtils.loadFromFile("truckSpots.json", new com.google.gson.reflect.TypeToken<ArrayList<TruckSpot>>() {}.getType());
    }

}