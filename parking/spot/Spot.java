package com.example.parking.spot;
import com.example.parking.*;
import java.util.Map;

public abstract class Spot {
    protected int spotID;
    protected double hourRate;
    protected Map<Integer, Slot> slots;

    public Spot(int spotID, double hourRate) {
        this.spotID = spotID;
        this.hourRate = hourRate;
    }

    public abstract boolean isSuitableFor(VehicleType vehicleType);

    public void addSlot(Slot slot) {
        slots.put(slot.getSlotID(), slot);
    }
    public void removeSlot(int slotID) {
        slots.remove(slotID);
    }

    public Slot getSlot(int slotID) {
        return slots.get(slotID);
    }

    public void displaySlots() {
        for (Slot slot : slots.values()) {
            slot.displaySlot();
        }
    }

    public int getSpotID() { return spotID; }
    public double getHourRate() { return hourRate; }
}

