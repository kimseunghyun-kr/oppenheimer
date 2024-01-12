package com.stock.oppenheimer.service;

import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.service.r2dbc.MarketDataService;
import com.stock.oppenheimer.service.r2dbc.StockDataService;
import com.stock.oppenheimer.service.r2dbc.StockMarketFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
public class StockMarketDataServiceTest {


    private final StockMarketFacadeService stockMarketFacadeService;
    private final StockDataService stockDataService;
    private final MarketDataService marketDataService;

    @Autowired
    public StockMarketDataServiceTest(StockMarketFacadeService stockMarketFacadeService, StockDataService stockDataService, MarketDataService marketDataService) {
        this.stockMarketFacadeService = stockMarketFacadeService;
        this.stockDataService = stockDataService;
        this.marketDataService = marketDataService;
    }

    @Test
    @Rollback
    void testAddByStockName() {
        // Call the method and verify the outcome
        Mono<StockData> resultMono = stockMarketFacadeService.addStockMarketData("삼성전자",null);
        StepVerifier.create(resultMono)
                .expectNextCount(1)
                .verifyComplete();
//        StockData stockData = resultMono.block();
        StockData sd = stockDataService.findByStockName("삼성전자");
        log.info("stockData = , {}", sd);
        List<MarketData> md = marketDataService.findByStockName("삼성전자");
        log.info("marketData = , {}", md);
    }
}
