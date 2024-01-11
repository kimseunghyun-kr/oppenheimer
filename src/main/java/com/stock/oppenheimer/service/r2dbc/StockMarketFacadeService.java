package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.domain.StockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class StockMarketFacadeService {
    private final StockDataService stockDataService;
    private final MarketDataService marketDataService;

    @Autowired
    public StockMarketFacadeService(StockDataService stockDataService, MarketDataService marketDataService) {
        this.stockDataService = stockDataService;
        this.marketDataService = marketDataService;
    }

    @Transactional
    public Mono<StockData> addStockMarketData(String stockName, String tickerName) {
        return stockDataService.addStockData(stockName, tickerName)
                .flatMap(savedStockData -> marketDataService.fetchMarketData(savedStockData, LocalDate.of(2023,12,1) , savedStockData.lastUpdatedDate)
                        .collectList()
                        .thenReturn(savedStockData));
    }





}
