package com.example.parking;
import com.example.parking.spot.*;

import java.util.*;

import static java.lang.Math.*;

public class SystemManager {
    public static Map<Integer, Owner> owners;
    public static Map<Integer, Spot> spots;
    public static Map<Integer, Reservation> reservations;
    public static ArrayList<Feedback> feedbacksList;
    public static int nextOwnerID, nextSpotID, nextReservationID, nextSlotID;

    public static void initialize() {
        spots = new HashMap<>();
        owners = new HashMap<>();
        reservations = new HashMap<>();
        feedbacksList = new ArrayList<>();
        load_data_from_file();

        setIDs();
    }
    // Set The Initial IDs
    public static void setIDs() {
        nextOwnerID = nextSpotID = nextReservationID = nextSlotID = 1;
        for (Owner owner : owners.values()) {
            nextOwnerID = max(nextOwnerID, owner.getOwnerID() + 1);
        }
        for (Spot spot : spots.values()) {
            nextSpotID = max(nextSpotID, spot.getSpotID() + 1);

            for (Slot slot : spot.getSlots()) {
                nextSlotID = max(nextSlotID, slot.getSlotID() + 1);
            }
        }
        for (Reservation reservation : reservations.values()) {
            nextReservationID = max(nextReservationID, reservation.getReservationID() + 1);
        }
    }

    // Spots & Slots
    public static Spot getSpot(int spotId) {
        return spots.get(spotId);
    }
    public static ArrayList<Spot> getSpotsByType(VehicleType vehicleType) {
        ArrayList<Spot> spots = new ArrayList<>();
        for (Spot spot : SystemManager.spots.values()) {
            if (spot.getVehicleType() == vehicleType) {
                spots.add(spot);
            }
        }
        return spots;
    }
    public static ArrayList<Slot> getSlotsBySpotID(int spotID) {
        return spots.get(spotID).getSlots();
    }

    // Get All slots from all spots
    public static ArrayList<Slot> getAllSlots() {
        ArrayList<Slot> allSlots = new ArrayList<>();
        for (Spot spot : spots.values()) {
            allSlots.addAll(spot.getSlots()); // assuming getSlots() returns a map
        }
        return allSlots;
    }
    // Get All Available Slots For Owner
    public static ArrayList<Slot> getAvailableSlots(Owner owner) {
        Map<VehicleType, Boolean> currentVehicles = new HashMap<>();
        for (Vehicle vehicle : owner.getVehicles())
            currentVehicles.put(vehicle.getType(), true);

        ArrayList<Slot> slots = new ArrayList<>();
        for (Spot spot : spots.values()) {
            if (currentVehicles.containsKey(spot.getVehicleType())) {
                for (Slot slot : spot.getSlots()) {
                    if (slot.isAvailable()) {
                        slots.add(slot);
                    }
                }
            }
        }
        return slots;
    }
    public static Slot getSlot(int spotID, int slotID) {
        return spots.get(spotID).getSlot(slotID);
    }

    // Owners
    public static ArrayList<Owner> getOwners() {
        return new ArrayList<Owner>(owners.values());
    }

    // FeedBacks
    public static ArrayList<Feedback> getFeedbacks(){
        return feedbacksList;
    }

    // Reservations
    public static ArrayList<Reservation> getReservationsWithType(VehicleType vehicleType) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : SystemManager.reservations.values()) {
            if (reservation.getSpotType() == vehicleType) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    // Files
    // Save all data to file
    public static void save_data_to_file() {
        ArrayList<CarSpot> carSpots = new ArrayList<>();
        ArrayList<BikeSpot> bikeSpots = new ArrayList<>();
        ArrayList<TruckSpot> truckSpots = new ArrayList<>();

        for (Spot spot : spots.values()) {
            if (spot instanceof CarSpot) {
                carSpots.add((CarSpot) spot);
            } else if (spot instanceof BikeSpot) {
                bikeSpots.add((BikeSpot) spot);
            } else if (spot instanceof TruckSpot) {
                truckSpots.add((TruckSpot) spot);
            }
        }

        // Save each type of spot
        CarSpot.saveSpots(carSpots);
        BikeSpot.saveSpots(bikeSpots);
        TruckSpot.saveSpots(truckSpots);

        // Save other components
        Owner.saveOwners(new ArrayList<>(owners.values()));
        Reservation.saveReservations(new ArrayList<>(reservations.values()));
        Slot.saveSlots(new ArrayList<>(getAllSlots()));
        Feedback.saveFeedbackToFile();
    }
    public static void load_data_from_file() {
        // Load spots by type
        ArrayList<CarSpot> carSpots = CarSpot.loadSpots();
        ArrayList<BikeSpot> bikeSpots = BikeSpot.loadSpots();
        ArrayList<TruckSpot> truckSpots = TruckSpot.loadSpots();

        // Merge all spots into allSpots
        for (CarSpot carSpot : carSpots) {
            spots.put(carSpot.getSpotID(), carSpot);
        }
        for (BikeSpot bikeSpot : bikeSpots) {
            spots.put(bikeSpot.getSpotID(), bikeSpot);
        }
        for (TruckSpot truckSpot : truckSpots) {
            spots.put(truckSpot.getSpotID(), truckSpot);
        }

        // Load other components
        ArrayList<Owner> owners = Owner.loadOwners();
        ArrayList<Reservation> reservations = Reservation.loadReservations();

        for (Owner owner : owners) {
            SystemManager.owners.put(owner.getOwnerID(), owner);
        }
        for (Reservation reservation : reservations) {
            SystemManager.reservations.put(reservation.getReservationID(), reservation);
        }

        // Reload feedbacks
        feedbacksList = Feedback.loadFeedbacks();
    }

    public static int getTotalSpots() {
        return spots.size();
    }
    public static int getTotalOwners(){
        return owners.size();
    }

    public static int getTotalReservations() {
        return reservations.size();
    }
}