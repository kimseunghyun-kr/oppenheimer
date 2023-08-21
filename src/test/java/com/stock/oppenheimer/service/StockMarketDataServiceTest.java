package com.stock.oppenheimer.service;

import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class StockMarketDataServiceTest {


    private final StockMarketFacadeService stockMarketFacadeService;
    private final StockDataService stockDataService;
    private MarketDataService marketDataService;

    @Autowired
    public StockMarketDataServiceTest(StockMarketFacadeService stockMarketFacadeService, StockDataService stockDataService, MarketDataService marketDataService) {
        this.stockMarketFacadeService = stockMarketFacadeService;
        this.stockDataService = stockDataService;
        this.marketDataService = marketDataService;
    }

    @Test
    void testAddByStockName() {
        // Call the method and verify the outcome
        Mono<StockData> resultMono = stockMarketFacadeService.addStockMarketData("삼성전자",null);
        StepVerifier.create(resultMono)
                .expectNextCount(1)
                .verifyComplete();
        StockData sd = stockDataService.findByStockName("삼성전자");
        log.info("stockData = , {}", sd);
        List<MarketData> md = marketDataService.findByStockName("삼성전자");
        log.info("marketData = , {}", md);
    }
}
