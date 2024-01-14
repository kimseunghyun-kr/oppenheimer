package com.stock.oppenheimer.service.jpa;


import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StockMarketFacadeServiceSync {
    private final StockDataServiceSync stockDataService;
    private final MarketDataServiceSync marketDataService;

    @Autowired
    public StockMarketFacadeServiceSync(StockDataServiceSync stockDataService, MarketDataServiceSync marketDataService) {
        this.stockDataService = stockDataService;
        this.marketDataService = marketDataService;
    }

    @Transactional
    public StockData addStockMarketData(String stockName, String tickerName) {
        StockData sd = stockDataService.addStockData(stockName, tickerName);
        List<MarketData> md = marketDataService.fetchMarketData(sd, LocalDate.of(2023,12,1) , sd.lastUpdatedDate);
        return sd;
    }
}
