package com.example.transport.model;

public class Task {
    private String id;
    private String assignedCarId;
    private String destination;
    private int requiredBatteryLevel; // Minimum battery level required for the trip
    private boolean isCompleted;
    private int tripDuration;
    private int batteryDrain;

    public Task() {}

    public Task(String id, String assignedCarId, String destination, int requiredBatteryLevel, boolean isCompleted, int tripDuration, int batteryDrain) {
        this.id = id;
        this.assignedCarId = assignedCarId;
        this.destination = destination;
        this.requiredBatteryLevel = requiredBatteryLevel;
        this.isCompleted = isCompleted;
        this.tripDuration = tripDuration;
        this.batteryDrain = batteryDrain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignedCarId() {
        return assignedCarId;
    }

    public void setAssignedCarId(String assignedCarId) {
        this.assignedCarId = assignedCarId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getRequiredBatteryLevel() {
        return requiredBatteryLevel;
    }

    public void setRequiredBatteryLevel(int requiredBatteryLevel) {
        this.requiredBatteryLevel = requiredBatteryLevel;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration (int tripDuration){
        this.tripDuration = tripDuration;
    }

    public int getBatteryDrain() {
        return batteryDrain;
    }

    public void setBatteryDrain (int batteryDrain){
        this.batteryDrain = batteryDrain;
    }
}
