package com.example.parking;
import java.time.LocalDateTime;
public class Reservation {
    private int resID,ownerID,amount;
    private Slot slot;
    private LocalDateTime reservationDate;
    private boolean status;
    public Reservation(int resID,int ownerID,int spotID,Slot slot,int amount) {
        this.resID = resID;this.ownerID = ownerID;
        this.slot = slot;
        this.status = true;
        this.reservationDate = LocalDateTime.now();
        this.amount = amount;
    }
    public boolean isActive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }
    public int getOwnerID() {
        return ownerID;
    }

    public int getResID() {
        return resID;
    }
    public int getAmount() {
        return amount;
    }

}