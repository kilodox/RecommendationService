package com.epam.recommendationservice;

import java.util.HashMap;
import java.util.Map;

public class Crypto {
    private String symbol;
    private double normalizedRange;
    private HashMap<Long, Double> prices;
    private long oldestTimestamp;
    private long newestTimestamp;
    private double oldestPrice;
    private double newestPrice;
    private double minPrice;
    private double maxPrice;

    public Crypto(String symbol) {
        this.symbol = symbol;
        this.prices = new HashMap<>();
        this.oldestTimestamp = Long.MAX_VALUE;
        this.newestTimestamp = Long.MIN_VALUE;
        this.oldestPrice = Double.MAX_VALUE;
        this.newestPrice = Double.MIN_VALUE;
        this.minPrice = Double.MAX_VALUE;
        this.maxPrice = Double.MIN_VALUE;
    }

    public String getSymbol() {
        return symbol;
    }

    public HashMap<Long, Double> getPrices() {
        return prices;
    }

    public void setOldestPrice(double oldestPrice) {
        this.oldestPrice = oldestPrice;
    }

    public void setNewestPrice(double newestPrice) {
        this.newestPrice = newestPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getOldestPrice() {
        return oldestPrice;
    }

    public double getNewestPrice() {
        return newestPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }
    public double getNormalizedRange() {
        return normalizedRange;
    }

    public void setNormalizedRange(double normalizedRange) {
        this.normalizedRange = normalizedRange;
    }

    public void addPriceTimestamp(long timestamp, double price) {
        prices.put(timestamp,price);
    }

    public void calculateOldNewMaxMinPrices() {
        for (Map.Entry<Long, Double> entry : prices.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();
            if(key < oldestTimestamp){
                oldestTimestamp = key;
                oldestPrice = value;
            }
            if(key > newestTimestamp){
                newestTimestamp = key;
                newestPrice = value;
            }
            if(value < minPrice){
                minPrice = value;
            }
            if(value> maxPrice){
                maxPrice = value;
            }
        }
    }
}
