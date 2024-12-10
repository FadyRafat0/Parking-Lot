package com.example.parking;
import com.example.parking.spot.*;

import java.time.LocalDateTime;

public class Admin extends Person {
    public Admin() {
        super("Admin", "Admin");
    }


    public void removeOwner(int ownerID) {
        SystemManager.allOwners.remove(ownerID);
    }

    public void addSlot(int spotID, LocalDateTime startDate, LocalDateTime endDate) {
        Slot slot = new Slot(SystemManager.nextSlotID, spotID, startDate, endDate);
        SystemManager.allSpots.get(slot.getSpotID()).addSlot(slot);
        SystemManager.nextSlotID++;
    }
    public void removeSlot(int slotID) {
        for (Spot spot : SystemManager.allSpots.values()) {
            if (spot.isSlotExists(slotID)) {
                spot.removeSlot(slotID);
                break;
            }
        }
    }

    // Will Add Empty Spot
    public void addNewSpot(VehicleType vehicleType) {
        Spot spot;
        if (vehicleType == VehicleType.Car) {
            spot = new CarSpot(SystemManager.nextSpotID);
        }
        else if (vehicleType == VehicleType.Bike) {
            spot = new BikeSpot(SystemManager.nextSpotID);
        }
        else {
            spot = new TruckSpot(SystemManager.nextSpotID);
        }
        SystemManager.allSpots.put(spot.getSpotID(), spot);
        SystemManager.nextSpotID++;
    }
    public void removeSpot(int spotID) {
        SystemManager.allSpots.remove(spotID);
    }

    public double calculateTotalAmount(VehicleType vehicleType) {
        double totalAmount = 0;
        for (Reservation reservation : SystemManager.allReservations.values()) {
            if (reservation.isActive()  &&
                    SystemManager.allSpots.get(reservation.getSpotID()).getSpotType() == vehicleType)
            {
                totalAmount += reservation.getTotalAmount();
            }
        }
        return totalAmount;
    }
}