package com.example.charging.model;

import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = 1L; // Add a unique identifier

    private String id;
    private String model;
    private int batteryCapacity; // kWh
    private int currentBatteryLevel; // %
    private boolean charging;

    public Car() {}

    public Car(String id, String model, int batteryCapacity, int currentBatteryLevel, boolean charging) {
        this.id = id;
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.currentBatteryLevel = currentBatteryLevel;
        this.charging = charging;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    public void setCurrentBatteryLevel(int currentBatteryLevel) {
        this.currentBatteryLevel = currentBatteryLevel;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }
}
