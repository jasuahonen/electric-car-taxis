package com.example.charging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.charging.service.ElectricityPriceService;
import com.example.charging.messaging.ChargingPublisher;
import com.example.charging.model.Car;

import java.util.concurrent.CompletableFuture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class ChargingService {

    private static final int CHARGING_RATE = 10; // % per simulated charging period (5 minutes)

    @Autowired
    private ElectricityPriceService electricityPriceService;

    @Autowired
    private ChargingPublisher chargingPublisher;


    public void startCharging(Car car) {
        try {
            // Fetch the cheapest hours for charging
            List<Map<String, Object>> cheapestHours = electricityPriceService.getCheapestHours(
                    LocalDate.now().toString(),
                    LocalDate.now().toString()
            );

            if (cheapestHours == null || cheapestHours.isEmpty()) {
                System.err.println("No cheap hours available for charging.");
                return;
            }

            // Select the cheapest hour
            Map<String, Object> cheapestHour = cheapestHours.get(0);
            int startHour = (int) cheapestHour.get("hour");
            double price = (double) cheapestHour.get("price");

            System.out.println("Cheapest charging hour for car " + car.getId() + ": Hour " +
                    startHour + " with price: " + price + " cents/kWh.");

            // Wait until the cheapest hour (if required)
            int currentHour = LocalTime.now().getHour();
            if (currentHour < startHour) {
                long delay = (startHour - currentHour) * 60 * 60 * 1000L; // Delay in milliseconds
                System.out.println("Waiting " + (startHour - currentHour) + " hours for the cheapest charging window.");
                Thread.sleep(delay);
            }

            // Start charging
            while (car.getCurrentBatteryLevel() < car.getBatteryCapacity()) {
                Thread.sleep(500); // Simulate
                car.setCurrentBatteryLevel(car.getCurrentBatteryLevel() + CHARGING_RATE);

                if (car.getCurrentBatteryLevel() > car.getBatteryCapacity()) {
                    car.setCurrentBatteryLevel(car.getBatteryCapacity());
                }

                // Publish progress update
                chargingPublisher.publishCarUpdate(car);
                System.out.println("Car " + car.getId() + " charging: " + car.getCurrentBatteryLevel() + "%");
            }

            // Publish completion update
            System.out.println("Car " + car.getId() + " is fully charged!");
            car.setCharging(false); // Update charging status
            chargingPublisher.publishCarUpdate(car);

        } catch (InterruptedException e) {
            System.err.println("Charging interrupted for car: " + car.getId());
            car.setCharging(false);
            chargingPublisher.publishCarUpdate(car);
        }
    }
}
