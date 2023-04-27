package com.epam.recommendationservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CryptoDataReader {
    private static final String CSV_FILE_EXTENSION = ".csv";
    private static final int TIMESTAMP_COLUMN_NUMBER = 0;
    private static final int SYMBOL_COLUMN_NUMBER = 1;
    private static final int PRICE_COLUMN_NUMBER = 2;

    private final String directoryPath;

    public CryptoDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Crypto> readAllCryptos() throws IOException {
        List<Crypto> cryptos = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] csvFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(CSV_FILE_EXTENSION));

        if (csvFiles != null) {
            for (File csvFile : csvFiles) {
                String symbol = csvFile.getName().replace(CSV_FILE_EXTENSION, "");
                String symbolWithoutValues = csvFile.getName().replace("_values"+CSV_FILE_EXTENSION, "");
                Crypto crypto = new Crypto(symbolWithoutValues);
                cryptos.add(crypto);

                try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                    String line = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        if (fields.length >= 3) {
                            long timestamp = Long.parseLong(fields[TIMESTAMP_COLUMN_NUMBER]);
                            String cryptoSymbol = fields[SYMBOL_COLUMN_NUMBER];
                            double price = Double.parseDouble(fields[PRICE_COLUMN_NUMBER]);

                            if (cryptoSymbol.equals(symbolWithoutValues)) {
                                crypto.addPriceTimestamp(timestamp,price);
                            } else {
                                System.err.println("Unexpected symbol " + cryptoSymbol + " here: " + csvFile.getName());
                            }
                        } else {
                            System.err.println("Invalid line format in file " + csvFile.getName());
                        }
                    }
                }
            }
        }

        return cryptos;
    }
}
