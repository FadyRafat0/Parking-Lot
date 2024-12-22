package com.example.parking;

import com.example.parking.spot.*;
import java.time.*;

public class Admin extends Person {
    public Admin() {
        super("admin", "admin");
    }

    // Slots
    public void addSlot(int spotID, LocalDateTime startDate, LocalDateTime endDate) {
        Slot slot = new Slot(SystemManager.nextSlotID, spotID, startDate, endDate);
        SystemManager.spots.get(spotID).addSlot(slot);
        SystemManager.nextSlotID++;
    }
    public void removeSlot(int slotID) {
        for (Spot spot : SystemManager.spots.values()) {
            if (spot.isSlotExists(slotID)) {
                spot.removeSlot(slotID);
                break;
            }
        }
    }

    public void removeOwner(int ownerID) {
        SystemManager.owners.remove(ownerID);
    }

    // Spots
    public void addSpot(VehicleType vehicleType) {
        Spot spot;
        // -> children
        if (vehicleType == VehicleType.Car) {
            spot = new CarSpot(SystemManager.nextSpotID);
        }
        else if (vehicleType == VehicleType.Bike) {
            spot = new BikeSpot(SystemManager.nextSpotID);
        }
        else {
            spot = new FourByFourSpot(SystemManager.nextSpotID);
        }
        SystemManager.spots.put(spot.getSpotID(), spot);
        SystemManager.nextSpotID++;
    }
    public void removeSpot(int spotID) {
        SystemManager.spots.remove(spotID);
    }

    // Reservations
    public void cancelReservation(int reservationID) {
        Reservation reservation = SystemManager.getReservation(reservationID);
        reservation.cancelReservation();
        Owner owner = SystemManager.getOwner(reservation.getOwnerID());
        // Return Payment
        owner.getPayment().adminCancelReservation(reservation);
    }
    public double calculateTotalAmountByType(VehicleType vehicleType) {
        double totalAmount = 0;
        for (Reservation reservation : SystemManager.getReservations()) {
            if (reservation.isActive()  && reservation.getVehicleType() == vehicleType)
                totalAmount += reservation.getAmount();
        }
        return totalAmount;
    }
}