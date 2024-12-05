package com.example.fleet.messaging;

import com.example.fleet.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FleetPublisher {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishFleet(List<Car> fleet) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(fleet);
            jmsTemplate.convertAndSend("fleet.updates", jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing fleet update: " + e.getMessage());
        }
    }


    public void publishCarUpdate(String action, Car car) {
        try {
            // Publish the Car object as JSON directly
            String jsonMessage = objectMapper.writeValueAsString(car);
            jmsTemplate.convertAndSend("fleet.car.updates", jsonMessage);
            System.out.println("Published car update: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing car update: " + e.getMessage());
        }
    }


    public static class FleetUpdate {
        private String action; // "ADD", "UPDATE", "DELETE"
        private Car car;

        public FleetUpdate(String action, Car car) {
            this.action = action;
            this.car = car;
        }

        public String getAction() {
            return action;
        }

        public Car getCar() {
            return car;
        }
    }
}
