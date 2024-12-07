package com.example.parking.spot;
import com.example.parking.*;
import java.util.*;

public abstract class Spot {
    protected int spotID;
    protected double hourRate;
    protected ArrayList<Slot> slots;

    public Spot(int spotID, double hourRate) {
        this.spotID = spotID;
        this.hourRate = hourRate;
    }

    public abstract boolean isSuitableFor(VehicleType vehicleType);
    public abstract VehicleType getSpotType();

    public void addSlot(Slot slot) {
        slots.add(slot.getSlotID(), slot);
    }
    public void removeSlot(int slotID) {
       slots.removeIf(slot -> slot.getSlotID() == slotID);
    }

    public Slot getSlot(int slotID) {
        return slots.get(slotID);
    }

    public int getSpotID() {
        return spotID;
    }
    public double getHourRate() {
        return hourRate;
    }

}

