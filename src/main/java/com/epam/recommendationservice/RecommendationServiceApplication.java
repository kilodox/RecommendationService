package com.epam.recommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class RecommendationServiceApplication {

	public static void main(String[] args) throws IOException {
		CryptoDataReader reader = new CryptoDataReader("C:\\JavaPositionTechnicalTask\\prices");
		List<Crypto> cryptos = reader.readAllCryptos();
		CryptoStatsCalculator.calculateStats(cryptos);
		CryptoStatsCalculator.printStats(cryptos);
		SpringApplication.run(RecommendationServiceApplication.class);
	}

}
