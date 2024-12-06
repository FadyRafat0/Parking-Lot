package mypackage;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;
public class Reservation {
    static int reservationID=0;
    private String ownerID;
    private String ownerName;
    private int resID;
    private int spotID;
    private LocalDate reservationDate;
    private int slotID;
    private boolean status;
    private int amount;
    public Reservation(String ownerID,String ownerName,int spotID,int slotID,int amount) {
        this.resID = reservationID++;
        this.ownerName = ownerName;
        this.ownerID = ownerID;
        this.spotID = spotID;
        this.slotID = slotID;
        this.status = true;
        this.reservationDate = LocalDate.now();
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSlotID() {
        return slotID;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getSpotID() {
        return spotID;
    }

    public void setSpotID(int spotID) {
        this.spotID = spotID;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public static int getReservationID() {
        return reservationID;
    }
    public int getAmount() {
        return amount;
    }
}
