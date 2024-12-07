package com.example.parking.spot;

import com.example.parking.VehicleType;

public class CarSpot extends Spot {
    public CarSpot(int id, double hourRate) {
        super(id, hourRate);
    }

    @Override
    public boolean isSuitableFor(VehicleType vehicleType) {
        return vehicleType == VehicleType.Car;
    }
    public VehicleType getSpotType()  {
        return VehicleType.Car;
    }
}