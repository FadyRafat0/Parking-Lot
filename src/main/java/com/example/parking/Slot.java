package com.example.parking;
import com.example.parking.json.JSONUtils;
import com.example.parking.spot.Spot;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

public class Slot {
    private final int slotID, spotID;
    private LocalDateTime startDate, endDate; // 2024-12-07T14:30:00 --> yyyy-MM-dd'T'HH:mm:ss
    private final int hours;
    private boolean isAvailable;

    public Slot(int slotID, int spotID, LocalDateTime startDate, LocalDateTime endDate) {
        this.slotID = slotID;
        this.spotID = spotID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = (int)Duration.between(startDate,endDate).toHours();
        this.isAvailable = true;
    }


    public void bookSlot() {
        isAvailable = false;
    }
    public void cancelBooking() {
        isAvailable = true;
    }

    public int getSlotID() {
        return slotID;
    }
    public int getSpotID() {
        return spotID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public VehicleType getSpotType() {
        return SystemManager.getSpot(spotID).getSpotType();
    }

    public int getHours() {
        return hours;
    }
    public boolean isAvailable() { return isAvailable; }

    // Save all Slots to a JSON file
    public static void saveSlots(ArrayList<Slot> slots) {
        JSONUtils.saveToFile(slots, "slots.json");
    }

    // Load all Slots from a JSON file
    public static void loadSlots() {
        ArrayList<Slot> loadedSlots = JSONUtils.loadFromFile("slots.json", new com.google.gson.reflect.TypeToken<ArrayList<Slot>>() {}.getType());

        for (Slot slot : loadedSlots) {
            Spot spot = SystemManager.getSpot(slot.getSpotID());
            if (spot != null) {
                spot.addSlot(slot);
            }
        }
    }

}