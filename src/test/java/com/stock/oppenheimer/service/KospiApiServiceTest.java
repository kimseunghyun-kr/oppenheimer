package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.WebAPI.async.KospiApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
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
                null, "삼성전자", LocalDate.of(2023, 10, 1),  LocalDate.now().plusDays(1)
        );

        List<MktDataDTO> fluxDTO = fluxData.collectList().block();

        for(MktDataDTO mktData : fluxDTO){
            log.info("Received data list: {}", mktData);
        }

    }

}
