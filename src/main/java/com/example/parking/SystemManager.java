package com.example.parking;
import com.example.parking.spot.*;

import java.util.HashMap;
import java.util.Map;

public class SystemManager {
    static Map<Integer, Spot> spots;

    public SystemManager() {
    }

    static Spot getSpot(int spotId) {
        return spots.get(spotId);
    }
}
