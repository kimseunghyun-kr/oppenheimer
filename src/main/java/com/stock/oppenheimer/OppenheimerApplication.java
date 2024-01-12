package com.stock.oppenheimer;

import com.stock.oppenheimer.repository.jpaRepository.TickerDataJPARepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = {"com.stock.oppenheimer.repository.r2dbcRepository"})
@EnableJpaRepositories(basePackages = {"com.stock.oppenheimer.repository.jpaRepository"})
public class OppenheimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OppenheimerApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(TickerDataJPARepository tickerDataJPARepository) {
		return new TestDataInit(tickerDataJPARepository);
	}

}
