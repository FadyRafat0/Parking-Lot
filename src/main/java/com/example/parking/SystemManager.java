package com.example.parking;
import com.example.parking.spot.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.max;

public class SystemManager {
    public static Map<Integer, Spot> allSpots;
    public static Map<Integer, Owner> allOwners;
    public static Map<Integer, Reservation> allReservations;
    public static  ArrayList<Feedback> feedbacksList;
    public static int nextOwnerID, nextSpotID, nextReservationID, nextSlotID;

    public static void initialize() {
        allSpots = new HashMap<>();
        allOwners = new HashMap<>();
        allReservations = new HashMap<>();
        feedbacksList = new ArrayList<>();

        Spot spot = new CarSpot(10);
        Spot spot2 = new BikeSpot(12);

        spot.addSlot(new Slot(1, 10, LocalDateTime.now(), LocalDateTime.now()));
        spot.addSlot(new Slot(13, 10, LocalDateTime.now(), LocalDateTime.now()));
        spot2.addSlot(new Slot(15, 12, LocalDateTime.now(), LocalDateTime.now()));

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(VehicleType.Car, "AD-456"));
        Owner owner = new Owner("Fady", "123456", 1, "ASDA", vehicles, 1000);
        allOwners.put(owner.getOwnerID(), owner);
        owner = new Owner("Bousy", "123456", 3, "ASDA", vehicles, 1000);
        allOwners.put(owner.getOwnerID(), owner);

        Reservation res = new Reservation(1, 1, spot.getSlot(1),  10, 15);
        allReservations.put(res.getReservationID(), res);
        res = new Reservation(2, 3, spot2.getSlot(15),  100, 155);
        allReservations.put(res.getReservationID(), res);

        Feedback feedback = new Feedback(1, 1, 5, "So Good");
        feedbacksList.add(feedback);
        feedback = new Feedback(2, 2, 3, "It's Ok");

        allSpots.put(spot.getSpotID(), spot);
        allSpots.put(spot2.getSpotID(), spot2);

        setIDs();
    }
    // Set The Initial IDs
    public static void setIDs() {
        nextOwnerID = nextSpotID = nextReservationID = nextSlotID = 1;
        for (Owner owner : allOwners.values()) {
            nextOwnerID = max(nextOwnerID, owner.getOwnerID() + 1);
        }
        for (Spot spot : allSpots.values()) {
            nextSpotID = max(nextSpotID, spot.getSpotID() + 1);

            for (Slot slot : spot.getSlots()) {
                nextSlotID = max(nextSlotID, slot.getSlotID() + 1);
            }
        }
        for (Reservation reservation : allReservations.values()) {
            nextReservationID = max(nextReservationID, reservation.getReservationID() + 1);
        }
    }

    // Spots & Slots
    public static Spot getSpot(int spotId) {
        return allSpots.get(spotId);
    }
    public static ArrayList<Spot> getSpotsByType(VehicleType vehicleType) {
        ArrayList<Spot> spots = new ArrayList<>();
        for (Spot spot : allSpots.values()) {
            if (spot.getSpotType() == vehicleType) {
                spots.add(spot);
            }
        }
        return spots;
    }

    public static ArrayList<Slot> getSlotsBySpotID(int spotID) {
        return allSpots.get(spotID).getSlots();
    }

    // Owners
    public static ArrayList<Owner> getOwners() {
        return new ArrayList<Owner>(allOwners.values());
    }
    public static Owner getOwner(String userName) {
        for (Owner owner : allOwners.values()) {
            if (owner.getUserName().equals(userName)) {
                return owner;
            }
        }
        return null;
    }

    public static boolean isOwnerExist(String userName, String password) {
        for (Owner owner : allOwners.values()) {
            if (userName.equals(owner.getUserName()) && password.equals(owner.getPassword()))
                return true;
        }
        return false;
    }
    public static boolean isOwnerExist(String userName) {
        for (Owner owner : allOwners.values()) {
            if (userName.equals(owner.getUserName()))
                return true;
        }
        return false;
    }

    public static void addOwner(String userName, String Password, String licenceNumber,
                                ArrayList<Vehicle> vehicles, double balance)
    {
        Owner newOwner = new Owner(userName, Password, nextOwnerID, licenceNumber, vehicles, balance);
        allOwners.put(newOwner.getOwnerID(), newOwner);
        nextOwnerID++;
    }

    // FeedBacks
    public static ArrayList<Feedback> getAllFeedBacks(){
        return feedbacksList;
    }

    // Reservations
    public static ArrayList<Reservation> getAllReservations(){
        return new ArrayList<Reservation>(allReservations.values());
    }
    public static ArrayList<Reservation> getReservationsWithType(VehicleType vehicleType) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : allReservations.values()) {
            if (reservation.getSpotType() == vehicleType) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}