package com.example.charging.messaging;

import com.example.charging.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChargingPublisher {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishCarUpdate(Car car) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(car);
            jmsTemplate.convertAndSend("fleet.car.updates", jsonMessage);
        } catch (Exception e) {
            System.err.println("Error serializing car update: " + e.getMessage());
        }
    }
}
