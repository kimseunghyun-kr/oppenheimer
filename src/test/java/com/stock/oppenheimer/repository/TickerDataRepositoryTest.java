package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.StockTickerData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@Transactional
public class TickerDataRepositoryTest {
    @Autowired
    TickerDataRepository tickerDataRepository;
    //트랜잭션 관련 코드

    @BeforeEach
    void beforeEach() {
        StockTickerData tickerData = new StockTickerData();
        tickerData.ticker= "AAPL";
        tickerData.lastUpdatedDate= new Date(2023,3,8);
        tickerData.region ="US";
        tickerData.associatedIndicators = new ArrayList<>();
        tickerDataRepository.save(tickerData);
    }
    @AfterEach
    void afterEach() {
        tickerDataRepository.deleteAll();
    }

}
