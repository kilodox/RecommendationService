package com.epam.recommendationservice;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CryptoStatsCalculatorTest {

    @Test
    public void testEmptyCryptos() {
        List<Crypto> cryptos = new ArrayList<>();
        CryptoStatsCalculator.calculateStats(cryptos);
        assertTrue(cryptos.isEmpty());
    }

    @Test
    public void testSingleCrypto() {
        List<Crypto> cryptos = new ArrayList<>();
        Crypto crypto = new Crypto("BTC");
        crypto.addPriceTimestamp(1, 1000.00);
        crypto.addPriceTimestamp(2, 1500.00);
        cryptos.add(crypto);

        CryptoStatsCalculator.calculateStats(cryptos);
        assertEquals(1000.00, crypto.getMinPrice());
        assertEquals(1500.00, crypto.getMaxPrice());
        assertEquals(1000.00, crypto.getOldestPrice());
        assertEquals(1500.00, crypto.getNewestPrice());
    }

    @Test
    public void testEmptyPrintStats() {
        List<Crypto> cryptos = new ArrayList<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CryptoStatsCalculator.printStats(cryptos);

        assertEquals("", outContent.toString());
    }

}
