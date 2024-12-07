package com.example.parking;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
public class Slot {
    private int spotID;
    private LocalDateTime startDate,endDate; // 2024-12-07T14:30:00 --> yyyy-MM-dd'T'HH:mm:ss
    private int hours;
    private boolean isAvailable;
    public Slot(int spotID,LocalDateTime startDate , LocalDateTime endDate) {
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

    public int getspotID() { return spotID; }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public int getHours() {
        return hours;
    }
    public boolean isAvailable() { return isAvailable; }
}