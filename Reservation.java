package mypackage;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;
public class Reservation {
    static  Vector <Reservation> reservations = new Vector<>();
    static Scanner sc = new Scanner(System.in);
    static int reservationID=0;
    String ownerID;
    String ownerName;
    int resID;
    int spotID;
    LocalDate reservationDate;
    int slotID;
    boolean status;
    public Reservation(String ownerID,String ownerName) {
        this.resID = reservationID++;
        this.ownerName = ownerName;
        this.ownerID = ownerID;
    }
    void makeReservation(String vechileNO,int slotID) {
        this.slotID = slotID;
        this.status = true;
        reservationDate = LocalDate.now();
    }
    static void updateT() {

    }


}
