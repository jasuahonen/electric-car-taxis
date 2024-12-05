package com.example.charging.model;

import java.time.LocalDateTime;

public class Price {

    private LocalDateTime timestamp;
    private double price; // in cents/kWh

    public Price() {
    }

    public Price(LocalDateTime timestamp, double price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "timestamp=" + timestamp +
                ", price=" + price +
                '}';
    }
}
