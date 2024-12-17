package com.example.parking;
import com.example.parking.json.JSONUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reservation {
    private final int reservationID, ownerID;
    private final Slot slot;
    private final LocalDateTime reservationDate; // 2024-12-07T14:30:00 --> yyyy-MM-dd'T'HH:mm:ss
    private boolean status;
    // The Amount of Reservation Diff From Slot Amount , Because Free Hours
    private final double amount;

    public Reservation(int reservationID, int ownerID, Slot slot, double amount) {
        this.reservationID = reservationID;
        this.ownerID = ownerID;
        this.slot = slot;
        this.amount = amount;

        this.status = true;
        this.reservationDate = LocalDateTime.now();
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }
    public int getOwnerID() {
        return ownerID;
    }

    public int getReservationID() {
        return reservationID;
    }
    public double getAmount() {
        return amount;
    }
    public int getSpotID() {
        return slot.getSpotID();
    }
    public VehicleType getSpotType() {
        return slot.getVehicleType();

    }

    public Slot getSlot() {
        return slot;
    }
    public boolean getStatus() {
        return status;
    }

    public boolean isActive() {
        return status;
    }
    public void cancelReservation() {
        this.status = false;
    }

    // Save all reservations to a file
    public static void saveReservations(ArrayList<Reservation> reservations) {
        JSONUtils.saveToFile(reservations, "reservations.json");
    }
    // Load reservations from a file
    public static ArrayList<Reservation> loadReservations() {
        return JSONUtils.loadFromFile("reservations.json", new com.google.gson.reflect.TypeToken<ArrayList<Reservation>>() {}.getType());
    }
}