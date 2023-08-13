package com.stock.oppenheimer;

import com.stock.oppenheimer.referenceMaterials.oddHolic;
import com.stock.oppenheimer.repository.TickerDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class OppenheimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OppenheimerApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(TickerDataRepository tickerDataRepository) {
		return new TestDataInit(tickerDataRepository);
	}

}
