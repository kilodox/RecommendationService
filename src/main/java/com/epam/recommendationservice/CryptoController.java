package com.epam.recommendationservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CryptoController {
    private static final String DIRECTORY_PATH = "C:\\JavaPositionTechnicalTask\\prices";

    @GetMapping("/crypto-stats")
    public ResponseEntity<Map<String, Double>> getCryptoStats() throws IOException {
        List<Crypto> cryptos = getAllCryptos();
        CryptoStatsCalculator.calculateStats(cryptos);
        Map<String, Double> stats = new HashMap<>();

        // normalized range calculation
        for (Crypto crypto : cryptos) {
            double normalizedRange = (crypto.getMaxPrice() - crypto.getMinPrice()) / crypto.getMinPrice();
            crypto.setNormalizedRange(normalizedRange);
            stats.put(crypto.getSymbol(), normalizedRange);
        }

        // desc sort for normalized range
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(stats.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map<String, Double> sortedStats = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : sortedEntries) {
            sortedStats.put(entry.getKey(), entry.getValue());
        }

        return ResponseEntity.ok(sortedStats);
    }

    @GetMapping("/{symbol}/stats")
    public ResponseEntity<Map<String, Double>> getCryptoStats(@PathVariable String symbol) throws IOException {
        List<Crypto> cryptos = getAllCryptos();
        CryptoStatsCalculator.calculateStats(cryptos);

        // finding the requested crypto by symbol
        Optional<Crypto> optionalCrypto = cryptos.stream()
                .filter(c -> c.getSymbol().equalsIgnoreCase(symbol))
                .findFirst();

        if (optionalCrypto.isPresent()) {
            Crypto crypto = optionalCrypto.get();

            Map<String, Double> stats = new HashMap<>();
            stats.put("oldestPrice", crypto.getOldestPrice());
            stats.put("newestPrice", crypto.getNewestPrice());
            stats.put("minPrice", crypto.getMinPrice());
            stats.put("maxPrice", crypto.getMaxPrice());

            return ResponseEntity.ok(stats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private List<Crypto> getAllCryptos() throws IOException {
        CryptoDataReader reader = new CryptoDataReader(DIRECTORY_PATH);
        return reader.readAllCryptos();
    }
}