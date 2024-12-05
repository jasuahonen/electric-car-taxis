package com.example.charging.controller;

import com.example.charging.model.Car;
import com.example.charging.service.ChargingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/charging")
public class ChargingController {

    @Autowired
    private ChargingService chargingService;

    @PostMapping("/start/{carId}")
    public ResponseEntity<String> startCharging(
            @PathVariable String carId,
            @RequestParam int currentBatteryLevel,
            @RequestParam int batteryCapacity
    ) {
        // Fetch car details from the Fleet module
        String fleetUrl = "http://localhost:8080/fleet/cars/" + carId;
        RestTemplate restTemplate = new RestTemplate();

        try {
            Car car = restTemplate.getForObject(fleetUrl, Car.class);

            // If car exists, validate and start charging
            if (car != null) {
                // Update car's battery levels and charging status
                car.setCurrentBatteryLevel(currentBatteryLevel);
                car.setBatteryCapacity(batteryCapacity);
                car.setCharging(true);

                // Start charging
                chargingService.startCharging(car);
                return ResponseEntity.ok("Charging process completed for car: " + carId);
            }
        } catch (Exception e) {
            // Handle errors like car not found or Fleet module unavailable
            return ResponseEntity.status(404).body("Car with ID " + carId + " not found in the fleet.");
        }
        return ResponseEntity.status(404).body("Car with ID " + carId + " not found in the fleet.");
    }
}
