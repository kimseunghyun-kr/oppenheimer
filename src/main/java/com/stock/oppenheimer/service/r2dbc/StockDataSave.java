package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockDataSave {

    private final TickerDataJPARepository tickerDataJPARepository;

    @Autowired
    public StockDataSave(TickerDataJPARepository tickerDataJPARepository) {
        this.tickerDataJPARepository = tickerDataJPARepository;
    }

    public StockData stockDataSave(StockData stockData) {
        tickerDataJPARepository.save(stockData);
        return stockData;
    }
}
