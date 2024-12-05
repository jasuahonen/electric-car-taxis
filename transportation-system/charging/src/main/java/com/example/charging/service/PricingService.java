package com.example.charging.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PricingService {

    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);
    private static final String API_BASE_URL = "https://api.porssisahko.net/v1/price.json";
    private static final LocalDate MIN_DATE = LocalDate.of(2021, 1, 1);

    public List<Map<String, Object>> getHourlyPrices(String start, String end) {
        List<Map<String, Object>> hourlyPrices = new ArrayList<>();
        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            LocalDate today = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            // Validate date range
            if (startDate.isBefore(MIN_DATE)) {
                throw new IllegalArgumentException("Start date must not be earlier than " + MIN_DATE);
            }
            if (endDate.isAfter(today) && currentTime.isBefore(LocalTime.of(14, 0))) {
                throw new IllegalArgumentException("Future dates are allowed only after 2 PM.");
            }

            while (!startDate.isAfter(endDate)) {
                for (int hour = 0; hour < 24; hour++) {
                    String apiUrl = String.format("%s?date=%s&hour=%d", API_BASE_URL, startDate, hour);
                    logger.info("Fetching data from API: {}", apiUrl);

                    try {
                        String response = new RestTemplate().getForObject(apiUrl, String.class);
                        if (response != null) {
                            JSONObject jsonResponse = new JSONObject(response); // Parse the response as JSON
                            double price = jsonResponse.getDouble("price"); // Extract the price value
                            Map<String, Object> priceData = Map.of(
                                "date", startDate.toString(),
                                "hour", hour,
                                "price", price
                            );
                            hourlyPrices.add(priceData);
                        }
                    } catch (Exception e) {
                        logger.warn("API error for {} hour {}: {}", startDate, hour, e.getMessage());
                    }
                }
                startDate = startDate.plusDays(1);
            }
        } catch (Exception e) {
            logger.error("Error retrieving hourly prices: ", e);
        }
        return hourlyPrices;
    }

    public List<Map<String, Object>> getCheapestHours(String start, String end) {
        List<Map<String, Object>> hourlyPrices = getHourlyPrices(start, end);

        if (hourlyPrices.isEmpty()) {
            return Collections.emptyList();
        }

        // Find the minimum price
        double minPrice = hourlyPrices.stream()
                .mapToDouble(priceData -> (double) priceData.get("price"))
                .min()
                .orElse(Double.MAX_VALUE);

        // Collect all hours with the minimum price
        return hourlyPrices.stream()
                .filter(priceData -> (double) priceData.get("price") == minPrice)
                .collect(Collectors.toList());
    }
    public List<Map<String, Object>> findTopCheapestHours(List<Map<String, Object>> hourlyPrices, int topCount) {
        return hourlyPrices.stream()
                .sorted(Comparator.comparingDouble(entry -> (double) entry.get("price")))
                .limit(topCount)
                .toList();
    }

}
