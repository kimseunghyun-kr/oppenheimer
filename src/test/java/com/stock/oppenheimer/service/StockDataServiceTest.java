package com.stock.oppenheimer.service;

import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataJPARepository;
import com.stock.oppenheimer.service.r2dbc.StockDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class StockDataServiceTest {

    @Autowired
    TickerDataJPARepository tickerDataJPARepository;
    @Autowired
    StockDataService stockDataService;


    private StockData stockData;

    @BeforeEach
    void beforeEach() {
        StockData stockData = new StockData();
        stockData.ticker= "AAPL";
        stockData.lastUpdatedDate= LocalDate.of(2023,8,3);
        stockData.mktCtg ="NASDAQ";
        stockData.stockName = "Apple";
        stockData.associatedIndicators = new ArrayList<>();
        tickerDataJPARepository.save(stockData);
        this.stockData = stockData;

        log.info("beforeEach");
    }
    @AfterEach
    void afterEach() {
        tickerDataJPARepository.deleteAll();
    }

    @Test
    void pageablefindAllTest() {
        Pageable pageable = PageRequest.of(0, 10);
        TickerSearchConditionDTO scDto = new TickerSearchConditionDTO();
        Page<StockData> stockDataPage = stockDataService.findAllMatching(scDto, pageable);
        assertThat(stockDataPage.getTotalElements()).isEqualTo(1);
        assertThat(stockDataPage.get().findFirst().get()).isEqualTo(stockData);
    }

    @Test
    void pageablefindNameContainsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        TickerSearchConditionDTO scDto = new TickerSearchConditionDTO();

        scDto.nameContains = "Ap";
        Page<StockData> stockDataPage = stockDataService.findAllMatching(scDto, pageable);
        assertThat(stockDataPage.get().findFirst().get()).isEqualTo(stockData);

        scDto.nameContains = "Btwin";
        stockDataPage = stockDataService.findAllMatching(scDto, pageable);
        assertThat(stockDataPage.getTotalElements()).isEqualTo(0);
    }



}
