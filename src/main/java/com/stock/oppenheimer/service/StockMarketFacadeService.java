package com.stock.oppenheimer.service;

import com.stock.oppenheimer.WebAPI.ApiService;
import com.stock.oppenheimer.domain.StockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<StockData> addStockMarketData(String stockName, String tickerName) {
        return stockDataService.addStockData(stockName, tickerName)
                .flatMap(savedStockData -> marketDataService.fetchMarketData(savedStockData)
                        .collectList()
                        .thenReturn(savedStockData));
    }





}
