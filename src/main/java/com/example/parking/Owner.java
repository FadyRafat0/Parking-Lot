package com.example.parking;
import com.example.parking.json.JSONUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class Owner extends Person {
    private final int ownerID;
    private String licenseNumber;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Reservation> reservations;
    private ArrayList<Feedback> feedbacks;
    private Payment payment;

    Owner(String userName, String password, int ownerID,  String licenseNumber, ArrayList<Vehicle> vehicles,
          double balance)
    {
        super(userName, password);

        this.ownerID = ownerID;
        this.licenseNumber = licenseNumber;
        this.vehicles = vehicles;
        this.payment = new Payment(ownerID, balance);
        this.reservations = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    void makeReservation(Reservation reservation) {
        // Confirm Payment
        payment.confirmReservation(reservation);
        reservations.add(reservation);
    }
    void cancelReservation(Reservation reservation) {
        // Cancel Payment
        payment.cancelReservation(reservation);
        reservation.cancelReservation();
        reservations.remove(reservation);
    }

    // Dont Know The Logic
    void updateReservation(Reservation reservation) {
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
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Payment getPayment() {
        return payment;
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