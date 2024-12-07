package com.example.parking.spot;
import com.example.parking.*;

import java.util.ArrayList;
import java.util.Map;

public abstract class Spot {
    protected int spotID;
    protected double hourRate;
    protected ArrayList<Slot> slots;

    public Spot(int spotID, double hourRate) {
        this.spotID = spotID;
        this.hourRate = hourRate;
    }

    public abstract boolean isSuitableFor(VehicleType vehicleType);
    public void removeSlot(int slotID) {
        slots.remove(slotID);
    }
    public Slot getSlot(int slotID) {
        return slots.get(slotID);
    }
    public void  addSlot(Slot slot){
        slots.add(slot);
    }

    public int getSpotID() { return spotID; }
    public double getHourRate() { return hourRate; }
}

