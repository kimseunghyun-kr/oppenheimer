package com.stock.oppenheimer;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final TickerDataRepository tickerDataRepository;
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        StockTickerData tickerData = new StockTickerData();
        tickerData.ticker= "AAPL";
        tickerData.lastUpdatedDate= new Date(2023,3,8);
        tickerData.region ="US";
        tickerData.associatedIndicators = new ArrayList<>();
        tickerDataRepository.save(tickerData);
    }

}
