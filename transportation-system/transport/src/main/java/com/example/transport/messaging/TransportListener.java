package com.example.transport.messaging;

import com.example.transport.service.TransportService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TransportListener {

    private final TransportService transportService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransportListener(TransportService transportService) {
        this.transportService = transportService;
    }

    @JmsListener(destination = "fleet.updates")
    public void handleFleetUpdates(String jsonMessage) {
        try {
            List<Map<String, Object>> fleetData = objectMapper.readValue(jsonMessage, new TypeReference<>() {});
            System.out.println("Received fleet update: " + fleetData.size() + " cars.");
            transportService.updateFleet(fleetData);
        } catch (Exception e) {
            System.err.println("Error processing fleet update: " + e.getMessage());
        }
    }
}
