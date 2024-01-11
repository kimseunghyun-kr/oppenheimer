package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockDataSave {

    private final TickerDataRepository tickerDataRepository;

    @Autowired
    public StockDataSave(TickerDataRepository tickerDataRepository) {
        this.tickerDataRepository = tickerDataRepository;
    }

    public StockData stockDataSave(StockData stockData) {
        tickerDataRepository.save(stockData);
        return stockData;
    }
}
