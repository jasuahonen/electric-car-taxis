package com.example.fleet.messaging;

import com.example.fleet.messaging.FleetPublisher;
import com.example.fleet.messaging.FleetPublisher.FleetUpdate;
import com.example.fleet.model.Car;
import com.example.fleet.service.FleetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Component
public class FleetListener {

    @Autowired
    private FleetService fleetService;

    private final ObjectMapper objectMapper = new ObjectMapper();

        @JmsListener(destination = "fleet.car.updates")
        public void receiveCarUpdate(String jsonMessage) {
            try {
                Car car = objectMapper.readValue(jsonMessage, Car.class);
                if (car.getId() != null) {
                    fleetService.updateCarBattery(car.getId(), car.getCurrentBatteryLevel());
                } else {
                    System.err.println("Received car update with null ID: " + jsonMessage);
                }
            } catch (Exception e) {
                System.err.println("Error processing car update: " + e.getMessage());
                System.err.println("Failed message: " + jsonMessage);
            }
        }





    @JmsListener(destination = "trips.started")
public void handleTripStarted(String jsonMessage) {
    try {
        TripEvent tripEvent = objectMapper.readValue(jsonMessage, TripEvent.class);
        System.out.println("Received trip started event: " + tripEvent);

        // Fetch existing car details
        Car existingCar = fleetService.getCarById(tripEvent.getCarId());
        if (existingCar != null) {
            // Create a new Car object with updated details
            Car updatedCar = new Car();
            updatedCar.setId(existingCar.getId());
            updatedCar.setModel(existingCar.getModel());
            updatedCar.setBatteryCapacity(existingCar.getBatteryCapacity());
            updatedCar.setCurrentBatteryLevel(existingCar.getCurrentBatteryLevel());
            updatedCar.setCharging(existingCar.isCharging());

            // Call updateCar with the updated Car object
            fleetService.updateCar(tripEvent.getCarId(), updatedCar);
        } else {
            System.err.println("Car not found for ID: " + tripEvent.getCarId());
        }
    } catch (Exception e) {
        System.err.println("Error processing trip started event: " + e.getMessage());
        System.err.println("Failed message: " + jsonMessage);
    }
}

@JmsListener(destination = "trips.completed")
public void handleTripCompleted(String jsonMessage) {
    try {
        TripEvent tripEvent = objectMapper.readValue(jsonMessage, TripEvent.class);
        System.out.println("Received trip completed event: " + tripEvent);

        // Fetch existing car details
        Car existingCar = fleetService.getCarById(tripEvent.getCarId());
        if (existingCar != null) {
            // Create a new Car object with updated details
            Car updatedCar = new Car();
            updatedCar.setId(existingCar.getId());
            updatedCar.setModel(existingCar.getModel());
            updatedCar.setBatteryCapacity(existingCar.getBatteryCapacity());
            updatedCar.setCurrentBatteryLevel(existingCar.getCurrentBatteryLevel());
            updatedCar.setCharging(existingCar.isCharging());

            // Call updateCar with the updated Car object
            fleetService.updateCar(tripEvent.getCarId(), updatedCar);
        } else {
            System.err.println("Car not found for ID: " + tripEvent.getCarId());
        }
    } catch (Exception e) {
        System.err.println("Error processing trip completed event: " + e.getMessage());
        System.err.println("Failed message: " + jsonMessage);
    }
}

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TripEvent {
        private String status; // "STARTED", "COMPLETED"
        private String carId;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        @Override
        public String toString() {
            return "TripEvent{status='" + status + "', carId='" + carId + "'}";
        }
    }
}
