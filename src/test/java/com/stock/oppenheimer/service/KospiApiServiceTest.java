package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.WebAPI.KospiApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class KospiApiServiceTest {
    @Autowired
    KospiApiService kospiApiService;


    @Test
    void fetchMarketData() {
        // Assuming kospiApiService.fetchMarketData returns Flux<MktDataDTO>

        Flux<MktDataDTO> fluxData = kospiApiService.fetchMarketData(
                null, "삼성전자", LocalDate.of(2023, 12, 1), LocalDate.now()
        );

        StepVerifier.create(fluxData)
                .recordWith(() -> new ArrayList<>())
                .expectNextCount(1)
                .consumeRecordedWith(values -> {
                    log.info("Received data list: {}", values);
                })
                .verifyComplete();
    }

}
