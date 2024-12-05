package com.example.transport.service;

import com.example.transport.messaging.TransportPublisher;
import com.example.transport.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransportService {

    private List<Car> cars = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    @Autowired
    private TransportPublisher transportPublisher;

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(String id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean createTask(Task task) {
        Optional<Car> suitableCar = cars.stream()
                .filter(car -> !car.isCharging() && car.getCurrentBatteryLevel() >= task.getRequiredBatteryLevel())
                .findFirst();

        if (suitableCar.isPresent()) {
            Car assignedCar = suitableCar.get();
            task.setAssignedCarId(assignedCar.getId());
            tasks.add(task);

            System.out.println("Task created and assigned to car: " + assignedCar.getId());
            simulateTransport(task, assignedCar);
            return true;
        }

        System.out.println("No suitable car available for the task.");
        return false;
    }

    @Async
    public void simulateTransport(Task task, Car car) {
        System.out.println("Starting trip for car " + car.getId() + " to " + task.getDestination());
        try {
            // Publish trip started to ActiveMQ
            transportPublisher.publishTripStarted(car.getId());

            int duration = task.getTripDuration();
            int batteryDrain = task.getBatteryDrain();

            while (duration > 0) {
                Thread.sleep(500); // Simulate 5 seconds per minute of trip
                car.setCurrentBatteryLevel(car.getCurrentBatteryLevel() - batteryDrain);
                duration--;

                if (car.getCurrentBatteryLevel() <= 0) {
                    car.setCurrentBatteryLevel(0);
                    System.out.println("Car " + car.getId() + " has run out of battery!");
                    break;
                }

                System.out.println("Car " + car.getId() + " battery level: " + car.getCurrentBatteryLevel() + "%");
            }

            task.setCompleted(true);
            System.out.println("Trip for car " + car.getId() + " to " + task.getDestination() + " completed.");

            // Publish trip completed and car update to ActiveMQ
            transportPublisher.publishTripCompleted(car.getId());
            transportPublisher.publishCarUpdate(car.getId(), car.getCurrentBatteryLevel());

        } catch (InterruptedException e) {
            System.err.println("Trip simulation interrupted for task " + task.getId());
        }
    }

    public boolean completeTask(String id) {
        Task task = getTaskById(id);
        if (task != null && !task.isCompleted()) {
            task.setCompleted(true);
            System.out.println("Task " + id + " marked as completed.");
            return true;
        }
        return false;
    }

    public void updateFleet(List<Map<String, Object>> fleetData) {
        cars.clear();
        for (Map<String, Object> carData : fleetData) {
            cars.add(new Car(
                (String) carData.get("id"),
                (String) carData.get("model"),
                (int) carData.get("batteryCapacity"),
                (int) carData.get("currentBatteryLevel"),
                (boolean) carData.get("charging")
            ));
        }
        System.out.println("Fleet details updated!");
    }

    // Car class used internally in TransportService
    public static class Car {
        private String id;
        private String model;
        private int batteryCapacity; // kWh
        private int currentBatteryLevel; // %
        private boolean isCharging;

        public Car(String id, String model, int batteryCapacity, int currentBatteryLevel, boolean isCharging) {
            this.id = id;
            this.model = model;
            this.batteryCapacity = batteryCapacity;
            this.currentBatteryLevel = currentBatteryLevel;
            this.isCharging = isCharging;
        }

        public String getId() {
            return id;
        }

        public int getCurrentBatteryLevel() {
            return currentBatteryLevel;
        }

        public void setCurrentBatteryLevel(int currentBatteryLevel) {
            this.currentBatteryLevel = currentBatteryLevel;
        }

        public boolean isCharging() {
            return isCharging;
        }
    }
}
