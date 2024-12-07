package com.example.parking;
import com.example.parking.spot.Spot;
import java.util.Map;

public class Payment {
    private int ownerID, totalHours;
    private double balance, penalty;
    private final int REWARD_HOURS = 6; // Every 6 Hours Get 1 Hour Free
    private final double PENALTY_AMOUNT = 3; // Three Dollars

    public Payment(int ownerID, double balance) {
        this.ownerID = ownerID;
        this.balance = balance;
        this.totalHours = 0;
    }

    void load_data_from_file(String filename) {

    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTotalHours() {
        return totalHours;
    }
    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public double getPenalty() {
        return penalty;
    }
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double calc_fees(int hours, double hour_rate) {
        return hours * hour_rate;
    }
    public double reservationAmount(int spotID, int slotID)  {
        Spot spot = SystemManager.getSpot(spotID);
        Slot slot = spot.getSlot(slotID);

        double amount = calc_fees(slot.getHours(), spot.getHourRate());
        int free_hours = (int)((totalHours + slot.getHours()) / REWARD_HOURS);
        return amount + calc_fees(free_hours, spot.getHourRate()) + getPenalty();
    }

    public void confirmReservation(int spotID, int slotID) {
        Spot spot = SystemManager.getSpot(spotID);
        Slot slot = spot.getSlot(slotID);
        double amount = reservationAmount(spotID, slotID);

        setTotalHours((getTotalHours() + slot.getHours()) % REWARD_HOURS);
        setBalance(getBalance() - amount);
        setPenalty(0);
    }
    public void cancelReservation(Reservation reservation) {
        Spot spot = SystemManager.getSpot(reservation.getSpotID());
        Slot slot = spot.getSlot(reservation.getSlotID());

        setBalance(getBalance() + reservation.getAmount());
        setTotalHours(((getTotalHours() % REWARD_HOURS) - (slot.getHours() % REWARD_HOURS) + REWARD_HOURS) % REWARD_HOURS);
        setPenalty(PENALTY_AMOUNT);
    }
}
