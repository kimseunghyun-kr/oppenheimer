package com.stock.oppenheimer;


import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.ArrayList;


@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final TickerDataRepository tickerDataRepository;
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    @Profile("local")
    public void initData() {
        log.info("test data init");
        StockData stockData = new StockData();
        stockData.ticker= "AAPL";
        stockData.lastUpdatedDate= LocalDate.of(2023,8,3);
        stockData.mktCtg ="NASDAQ";
        stockData.stockName = "Apple";
        stockData.associatedIndicators = new ArrayList<>();
        tickerDataRepository.save(stockData);
    }

}
