package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.repository.jpaRepository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketDataSave {

    private final MarketDataRepository marketDataRepository;

    @Autowired
    public MarketDataSave(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    public MarketData marketDataSave(MarketData marketData) {
        marketDataRepository.save(marketData);
        return marketData;
    }
}
