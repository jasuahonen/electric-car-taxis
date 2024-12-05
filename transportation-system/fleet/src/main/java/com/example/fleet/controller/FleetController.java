package com.example.fleet.controller;

import com.example.fleet.model.Car;
import com.example.fleet.service.FleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fleet")
public class FleetController {

    @Autowired
    private FleetService fleetService;

    // Get all cars
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return fleetService.getAllCars();
    }

    // Get a specific car by ID
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        Car car = fleetService.getCarById(id);
        return car != null ? ResponseEntity.ok(car) : ResponseEntity.notFound().build();
    }

    // Add a new car
    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        if (fleetService.addCar(car)) {
            return ResponseEntity.status(201).body("Car added successfully.");
        } else {
            return ResponseEntity.status(409).body("Car with the same ID already exists.");
        }
    }

    // Update an existing car
    @PutMapping("/cars/{id}")
    public ResponseEntity<String> updateCar(@PathVariable String id, @RequestBody Car updatedCar) {
        if (fleetService.updateCar(id, updatedCar)) {
            return ResponseEntity.ok("Car updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a car
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable String id) {
        if (fleetService.deleteCar(id)) {
            return ResponseEntity.ok("Car deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
