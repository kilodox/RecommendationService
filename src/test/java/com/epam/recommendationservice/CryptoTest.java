package com.epam.recommendationservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class CryptoTest {
    private Crypto crypto;

    @BeforeEach
    void setUp() {
        crypto = new Crypto("BTC");
        crypto.addPriceTimestamp(1622157000L, 50000.0);
        crypto.addPriceTimestamp(1622243400L, 51000.0);
        crypto.addPriceTimestamp(1622329800L, 52000.0);
        crypto.calculateOldNewMaxMinPrices();
    }

    @Test
    void testCalculateOldestAndNewestPrices() {
        crypto.calculateOldNewMaxMinPrices();
        Assertions.assertEquals(50000.0, crypto.getOldestPrice());
        Assertions.assertEquals(52000.0, crypto.getNewestPrice());
    }
    @Test
    void testGetMinPrice() {
        Assertions.assertEquals(50000.0, crypto.getMinPrice());
    }

    @Test
    void testGetMaxPrice() {
        Assertions.assertEquals(52000.0, crypto.getMaxPrice());
    }

    @Test
    void testGetNormalizedRange() {
        crypto.calculateOldNewMaxMinPrices();
        crypto.setNormalizedRange((crypto.getMaxPrice() - crypto.getMinPrice()) / crypto.getMinPrice());
        Assertions.assertEquals(0.04, crypto.getNormalizedRange());
    }

    @Test
    void testGetSymbol() {
        Assertions.assertEquals("BTC", crypto.getSymbol());
    }

    @Test
    void testAddPriceTimestamp() {
        HashMap<Long, Double> expectedPrices = new HashMap<>();
        expectedPrices.put(1622157000L, 50000.0);
        expectedPrices.put(1622243400L, 51000.0);
        expectedPrices.put(1622329800L, 52000.0);
        Assertions.assertEquals(expectedPrices, crypto.getPrices());
    }
}
