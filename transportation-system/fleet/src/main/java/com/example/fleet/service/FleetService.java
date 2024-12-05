package com.example.fleet.service;

import com.example.fleet.model.Car;
import com.example.fleet.messaging.FleetPublisher;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FleetService {

    @Autowired
    private FleetPublisher fleetPublisher;

    private List<Car> fleet = new ArrayList<>();

    // Get all cars
    public List<Car> getAllCars() {
        return fleet;
    }

    // Get a car by ID
    public Car getCarById(String id) {
        return fleet.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

    // Add a car
    public boolean addCar(Car car) {
        if (fleet.stream().anyMatch(c -> c.getId().equals(car.getId()))) {
            return false; // Car with the same ID already exists
        }
        fleet.add(car);

        // Log the added car and the current size of the fleet
        System.out.println("Added a car to the fleet: " + fleet);
        System.out.println("Fleet now contains: " + fleet.size() + " cars.");

        // Publish fleet update without redundant battery update
        fleetPublisher.publishFleet(fleet);
        return true;
    }


    // Update a car
    public boolean updateCar(String id, Car updatedCar) {
        Optional<Car> existingCar = fleet.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (existingCar.isPresent()) {
            existingCar.get().setModel(updatedCar.getModel());
            existingCar.get().setBatteryCapacity(updatedCar.getBatteryCapacity());
            existingCar.get().setCurrentBatteryLevel(updatedCar.getCurrentBatteryLevel());
            existingCar.get().setCharging(updatedCar.isCharging());
            return true;
        }
        return false;
    }

    // Delete a car
    public boolean deleteCar(String id) {
        Optional<Car> carToRemove = fleet.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (carToRemove.isPresent()) {
            fleet.remove(carToRemove.get());

            // Log the deletion and current fleet size
            System.out.println("Car with id: " + id + " deleted. Fleet now contains: " + fleet.size() + " cars.");
            return true;
        }
        return false;
    }


    public void updateCarBattery(String carId, int newBatteryLevel) {
        for (Car car : fleet) {
            if (car.getId().equals(carId)) {
                if (car.getCurrentBatteryLevel() != newBatteryLevel) { // Only log if the level changes
                    car.setCurrentBatteryLevel(newBatteryLevel);
                    System.out.println("Updated car " + carId + " battery level to " + newBatteryLevel + "%.");
                }
                return;
            }
        }
        System.err.println("Car with id " + carId + " not found");
    }

}
