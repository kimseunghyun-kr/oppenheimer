package com.stock.oppenheimer.service.jpa;

import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class StockMarketDataServiceSyncTest {
    private final StockMarketFacadeServiceSync stockMarketFacadeService;
    private final StockDataServiceSync stockDataService;
    private final MarketDataServiceSync marketDataService;

    @Autowired
    public StockMarketDataServiceSyncTest(StockMarketFacadeServiceSync stockMarketFacadeService, StockDataServiceSync stockDataService, MarketDataServiceSync marketDataService) {
        this.stockMarketFacadeService = stockMarketFacadeService;
        this.stockDataService = stockDataService;
        this.marketDataService = marketDataService;
    }

    @Test
    void testAddByStockName() {
        // Call the method and verify the outcome
        StockData result = stockMarketFacadeService.addStockMarketData("삼성전자",null);
        assertThat(result.stockName).isEqualTo("삼성전자");
        StockData sd = stockDataService.findByStockName("삼성전자");
        log.info("stockData = , {}", sd);
        List<MarketData> md = marketDataService.findByStockName("삼성전자");
        log.info("marketData = , {}", md);
    }
}
