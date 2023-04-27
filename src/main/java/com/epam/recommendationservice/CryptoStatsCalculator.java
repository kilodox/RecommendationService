package com.epam.recommendationservice;

import java.util.List;

public class CryptoStatsCalculator {
    public static void calculateStats(List<Crypto> cryptos) {
        for (Crypto crypto : cryptos) {
            crypto.calculateOldNewMaxMinPrices();
        }
    }

    public static void printStats(List<Crypto> cryptos) {
        for (Crypto crypto : cryptos) {
            System.out.println("Crypto symbol: " + crypto.getSymbol());
            System.out.println("Oldest price: " + crypto.getOldestPrice());
            System.out.println("Newest price: " + crypto.getNewestPrice());
            System.out.println("Minimum price: " + crypto.getMinPrice());
            System.out.println("Maximum price: " + crypto.getMaxPrice());
            System.out.println();
        }
    }
}
