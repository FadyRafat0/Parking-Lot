package com.example.parking;
import com.example.parking.spot.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.max;

public class SystemManager {
    public static Map<Integer, Owner> allOwners;
    public static Map<Integer, Spot> allSpots;
    public static Map<Integer, Reservation> allReservations;
    public static  ArrayList<Feedback> feedbacksList;
    public static int nextOwnerID, nextSpotID, nextReservationID, nextSlotID;

    public static void initialize() {
        allSpots = new HashMap<>();
        allOwners = new HashMap<>();
        allReservations = new HashMap<>();
        feedbacksList = new ArrayList<>();
//
//        Spot spot = new CarSpot(10);
//        Spot spot2 = new BikeSpot(12);
//
//        spot.addSlot(new Slot(1, 10, LocalDateTime.now(), LocalDateTime.now()));
//        spot.addSlot(new Slot(13, 10, LocalDateTime.now(), LocalDateTime.now()));
//        spot2.addSlot(new Slot(15, 12, LocalDateTime.now(), LocalDateTime.now()));
//
//        ArrayList<Vehicle> vehicles = new ArrayList<>();
//        vehicles.add(new Vehicle(VehicleType.Car, "AD-456"));
//        Owner owner = new Owner("Fady", "123456", 1, "ASDA", vehicles, 1000);
//        allOwners.put(owner.getOwnerID(), owner);
//        owner = new Owner("Bousy", "123456", 3, "ASDA", vehicles, 1000);
//        allOwners.put(owner.getOwnerID(), owner);
//
//        Reservation res = new Reservation(1, 1, spot.getSlot(1),  10, 15);
//        allReservations.put(res.getReservationID(), res);
//        res = new Reservation(2, 3, spot2.getSlot(15),  100, 155);
//        allReservations.put(res.getReservationID(), res);
//

//
//        allSpots.put(spot.getSpotID(), spot);
//        allSpots.put(spot2.getSpotID(), spot2);

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

    // Get all slots from all spots
    public static ArrayList<Slot> getAllSlots() {
        ArrayList<Slot> allSlots = new ArrayList<>();
        for (Spot spot : allSpots.values()) {
            allSlots.addAll(spot.getSlots()); // assuming getSlots() returns a map
        }
        return allSlots;
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


    // Files
    // Save all data to file
    public static void save_data_to_file() {
        // Save all spots, owners, and reservations
        ArrayList<Spot> carSpots, bikeSpots, truckSpots;
        carSpots = bikeSpots = truckSpots = new ArrayList<>();
        for (Spot spot : allSpots.values()) {
            if (spot.getSpotType() == VehicleType.Car)
                carSpots.add(spot);
            else if (spot.getSpotType() == VehicleType.Bike)
                bikeSpots.add(spot);
            else
                truckSpots.add(spot);
        }

        CarSpot.saveSpots(carSpots);
        BikeSpot.saveSpots(bikeSpots);
        TruckSpot.saveSpots(truckSpots);
        Owner.saveOwners(new ArrayList<>(allOwners.values()));  // Convert Map to List for owners
        Reservation.saveReservations(new ArrayList<>(allReservations.values()));  // Convert Map to List for reservations
        Slot.saveSlots(new ArrayList<>(getAllSlots()));  // Convert all slots to List
        Feedback.saveFeedbackToFile();
    }

    public static void load_data_from_file() {
        // Load data from JSON files
        ArrayList<Spot> spots = CarSpot.loadSpots(); // Load as List
        spots.addAll(BikeSpot.loadSpots());
        spots.addAll(TruckSpot.loadSpots());
        // Populate maps from the lists
        for (Spot spot : spots) {
            allSpots.put(spot.getSpotID(), spot);
        }

        ArrayList<Owner> owners = Owner.loadOwners();  // Load as List
        ArrayList<Reservation> reservations = Reservation.loadReservations();  // Load as List

        for (Owner owner : owners) {
            allOwners.put(owner.getOwnerID(), owner);
        }
        for (Reservation reservation : reservations) {
            allReservations.put(reservation.getReservationID(), reservation);
        }
        // Load slots and feedback
        Slot.loadSlots();  // Assuming slots are loaded correctly
        feedbacksList = Feedback.loadFeedbacks();  // Assuming feedbacks are loaded correctly
    }
}