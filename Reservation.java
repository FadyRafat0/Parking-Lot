package mypackage;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;
public class Reservation {
    static int reservationID=0;
    String ownerID;
    String ownerName;
    int resID;
    int spotID;
    LocalDate reservationDate;
    int slotID;
    boolean status;
    public Reservation(String ownerID,String ownerName,int spotID,int slotID) {
        this.resID = reservationID++;
        this.ownerName = ownerName;
        this.ownerID = ownerID;
        this.spotID = spotID;
        this.slotID = slotID;
        this.status = true;
        this.reservationDate = LocalDate.now();

    }
}
