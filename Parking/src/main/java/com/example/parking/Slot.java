package com.example.parking;

public class Slot {
    private int slotID;
    private String date;
    private int hours;
    private boolean isAvailable;

    public Slot(int id, String date, int hours) {
        this.slotID = id;
        this.date = date;
        this.hours = hours;
        this.isAvailable = true;
    }

    public void displaySlot() {
        System.out.println("Slot ID: " + slotID + ", Date: " + date + ", Hours: " + hours + ", Available: " + isAvailable);
    }
    public void bookSlot() {
        isAvailable = false;
    }
    public void cancelBooking() {
        isAvailable = true;
    }

    public int getSlotID() { return slotID; }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public boolean isAvailable() { return isAvailable; }
}