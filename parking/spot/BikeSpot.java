package com.example.parking.spot;

import com.example.parking.VehicleType;

public class BikeSpot extends Spot{
    public  BikeSpot(int id, double houtRate){
        super(id,houtRate);
    }

    @Override
    public boolean isSuitableFor(VehicleType vehicleType){
        return vehicleType == VehicleType.Bike;
    }
}