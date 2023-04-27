package com.epam.recommendationservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CryptoDataReaderTest {

    @TempDir
    Path tempDir;

    private CryptoDataReader cryptoDataReader;

    @BeforeEach
    public void setup() {
        cryptoDataReader = new CryptoDataReader(tempDir.toString());
    }

    @Test
    public void testEmptyDirectory() throws IOException {
        List<Crypto> cryptos = cryptoDataReader.readAllCryptos();
        assertTrue(cryptos.isEmpty());
    }

    @Test
    public void testValidData() throws IOException {
        File file1 = new File(tempDir.toFile(), "BTC_values.csv");
        FileWriter writer1 = new FileWriter(file1);
        writer1.write("timestamp,symbol,price\n");
        writer1.write("1,BTC,10000.0\n");
        writer1.write("2,BTC,12000.0\n");
        writer1.close();

        File file2 = new File(tempDir.toFile(), "ETH_values.csv");
        FileWriter writer2 = new FileWriter(file2);
        writer2.write("timestamp,symbol,price\n");
        writer2.write("1,ETH,100.0\n");
        writer2.write("2,ETH,120.0\n");
        writer2.close();

        List<Crypto> cryptos = cryptoDataReader.readAllCryptos();
        CryptoStatsCalculator.calculateStats(cryptos);
        assertEquals(2, cryptos.size());

        Crypto btc = cryptos.get(0);

        assertEquals("BTC", btc.getSymbol());
        assertEquals(2, btc.getPrices().size());
        assertEquals(10000.0, btc.getMinPrice());
        assertEquals(12000.0, btc.getMaxPrice());

        Crypto eth = cryptos.get(1);
        assertEquals("ETH", eth.getSymbol());
        assertEquals(2, eth.getPrices().size());
        assertEquals(100.0, eth.getOldestPrice());
        assertEquals(120.0, eth.getNewestPrice());
    }

    @Test
    public void testInvalidData() throws IOException {
        File file = new File(tempDir.toFile(), "invalid.csv");
        FileWriter writer = new FileWriter(file);
        writer.write("timestamp,symbol,price\n");
        writer.write("1,btc\n"); // invalid line format, should have 3 fields
        writer.write("2,btc,12000.0\n");
        writer.close();

        List<Crypto> cryptos = cryptoDataReader.readAllCryptos();
        assertEquals(1, cryptos.size());
    }
}