package com.example.charging.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class ElectricityPriceService {

    @Autowired
    private PricingService pricingService;

    private List<Map<String, Object>> cachedPrices;

    public void fetchElectricityPrices(String start, String end) {
        cachedPrices = pricingService.getHourlyPrices(start, end);
        if (cachedPrices.isEmpty()) {
            System.err.println("No electricity prices available for the given period.");
        } else {
            System.out.println("Electricity prices fetched successfully.");
        }
    }

    public List<Map<String, Object>> getCachedPrices() {
        return cachedPrices;
    }

    public List<Map<String, Object>> getCheapestHours(String start, String end) {
        return pricingService.getCheapestHours(start, end);
    }

    public List<Map<String, Object>> findTopCheapestHours(int topCount) {
        return pricingService.findTopCheapestHours(cachedPrices, topCount);
    }
}
