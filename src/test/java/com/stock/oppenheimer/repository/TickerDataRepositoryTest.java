package com.stock.oppenheimer.repository;


import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class TickerDataRepositoryTest {
    @Autowired
    TickerDataRepository tickerDataRepository;

    private StockData stockData;

    @BeforeEach
    void beforeEach() {
        StockData stockData = new StockData();
        stockData.ticker= "AAPL";
        stockData.lastUpdatedDate= LocalDate.of(2023,8,3);
        stockData.mktCtg ="NASDAQ";
        stockData.stockName = "Apple";
        stockData.associatedIndicators = new ArrayList<>();
        tickerDataRepository.save(stockData);
        this.stockData = stockData;
        log.info("beforeEach");
    }
    @AfterEach
    void afterEach() {
        tickerDataRepository.deleteAll();
    }

    @Test
    void testFindTicker(){
        StockData foundStock = tickerDataRepository.findByTicker("AAPL");
        assertThat(foundStock).isEqualTo(this.stockData);
    }

}
