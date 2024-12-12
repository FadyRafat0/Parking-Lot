package com.example.parking.spot;

import com.example.parking.VehicleType;
import com.example.parking.json.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class CarSpot extends Spot {
    public CarSpot(int id) {
        super(id, CAR_HOUR_RATE);
    }
    public VehicleType getSpotType()  {
        return VehicleType.Car;
    }

    public static void saveSpots(ArrayList<Spot> spots) {
        JSONUtils.saveToFile(spots, "carSpots.json");
    }
    public static ArrayList<Spot> loadSpots() {
        return JSONUtils.loadFromFile("carSpots.json", new com.google.gson.reflect.TypeToken<ArrayList<Spot>>() {}.getType());
    }
}