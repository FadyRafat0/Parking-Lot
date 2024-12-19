package com.example.parking;
import com.example.parking.json.JSONUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class Owner extends Person {
    private final int ownerID;
    private String licenseNumber;
    private ArrayList<Vehicle> vehicles;
    private final ArrayList<Reservation> reservations;
    private final Payment payment;

    public Owner(String userName, String password, int ownerID,  String licenseNumber, ArrayList<Vehicle> vehicles,
          double balance)
    {
        super(userName, password);

        this.ownerID = ownerID;
        this.licenseNumber = licenseNumber;
        this.vehicles = vehicles;
        this.payment = new Payment(ownerID, balance);
        this.reservations = new ArrayList<>();
    }

    public int getOwnerID() {
        return ownerID;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    public ArrayList<Reservation> getReservations(){ return reservations;}

    public Payment getPayment() {
        return payment;
    }

    // Reservations
    public Reservation getReservation(int reservationID) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationID() == reservationID) {
                return reservation;
            }
        }
        return null;
    }
    public void makeReservation(Slot slot) {
        double reservationAmount = payment.reservationAmount(slot);
        Reservation reservation = new Reservation(SystemManager.nextReservationID, getOwnerID(), slot,
                reservationAmount);

        // Confirm Payment
        reservations.add(reservation);
        payment.confirmReservation(reservation);
        Slot systemSlot = SystemManager.getSlot(slot.getSpotID(), slot.getSlotID());
        systemSlot.bookSlot();

        SystemManager.reservations.put(reservation.getReservationID(), reservation);
        SystemManager.nextReservationID++;
    }
    public void cancelReservation(Reservation reservation) {
        // Cancel Payment
        payment.cancelReservation(reservation);
        Slot systemSlot = SystemManager.getSlot(reservation.getSpotID(), reservation.getSlot().getSlotID());
        systemSlot.cancelBooking();
        reservation.cancelReservation();
    }

    // Dont Know The Logic
    public void updateReservation(Reservation reservation) {
    }

    // Feedback
    public void makeFeedback(int ownerID, int reservationID, double rate, String message) {
        Feedback feedback = new Feedback(ownerID, reservationID, rate, message);
        SystemManager.feedbacksList.add(feedback);
    }

    // Owners
    public static boolean isOwnerExist(String userName, String password) {
        for (Owner owner : SystemManager.owners.values()) {
            if (userName.equals(owner.getUserName()) && password.equals(owner.getPassword()))
                return true;
        }
        return false;
    }
    public static boolean isOwnerExist(String userName) {
        for (Owner owner : SystemManager.owners.values()) {
            if (userName.equals(owner.getUserName()))
                return true;
        }
        return false;
    }
    public static void addOwner(String userName, String Password, String licenceNumber,
                                ArrayList<Vehicle> vehicles, double balance)
    {
        Owner newOwner = new Owner(userName, Password, SystemManager.nextOwnerID, licenceNumber, vehicles, balance);
        SystemManager.owners.put(newOwner.getOwnerID(), newOwner);
        SystemManager.nextOwnerID++;
    }
    public static Owner getOwner(String userName) {
        for (Owner owner : SystemManager.owners.values()) {
            if (owner.getUserName().equals(userName)) {
                return owner;
            }
        }
        return null;
    }

    // Save all Owners to a JSON file
    public static void saveOwners(ArrayList<Owner> owners) {
        JSONUtils.saveToFile(owners, "owners.json");
    }
    // Load all Owners from a JSON file
    public static ArrayList<Owner> loadOwners() {
        Type ownerListType = new TypeToken<ArrayList<Owner>>() {}.getType();
        return JSONUtils.loadFromFile("owners.json", ownerListType);
    }
}