package com.example.transport.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransportPublisher {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishTripStarted(String carId) {
        try {
            TripEvent tripEvent = new TripEvent("STARTED", carId);
            String jsonMessage = objectMapper.writeValueAsString(tripEvent);
            jmsTemplate.convertAndSend("trips.started", jsonMessage);
            System.out.println("Published trip started: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing trip started event: " + e.getMessage());
        }
    }

    public void publishTripCompleted(String carId) {
        try {
            TripEvent tripEvent = new TripEvent("COMPLETED", carId);
            String jsonMessage = objectMapper.writeValueAsString(tripEvent);
            jmsTemplate.convertAndSend("trips.completed", jsonMessage);
            System.out.println("Published trip completed: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing trip completed event: " + e.getMessage());
        }
    }

    public void publishCarUpdate(String carId, int batteryLevel) {
        try {
            CarUpdate carUpdate = new CarUpdate(carId, batteryLevel);
            String jsonMessage = objectMapper.writeValueAsString(carUpdate);
            jmsTemplate.convertAndSend("fleet.car.updates", jsonMessage);
            System.out.println("Published car update: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing car update: " + e.getMessage());
        }
    }

    static class TripEvent {
        private String status; // "STARTED", "COMPLETED"
        private String carId;

        public TripEvent(String status, String carId) {
            this.status = status;
            this.carId = carId;
        }

        public String getStatus() {
            return status;
        }

        public String getCarId() {
            return carId;
        }
    }

    static class CarUpdate {
        private String carId;
        private int batteryLevel;

        public CarUpdate(String carId, int batteryLevel) {
            this.carId = carId;
            this.batteryLevel = batteryLevel;
        }

        public String getCarId() {
            return carId;
        }

        public int getBatteryLevel() {
            return batteryLevel;
        }
    }
}
