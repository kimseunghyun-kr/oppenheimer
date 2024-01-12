package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.repository.jpaRepository.MarketDataJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketDataSave {

    private final MarketDataJPARepository marketDataJPARepository;

    @Autowired
    public MarketDataSave(MarketDataJPARepository marketDataJPARepository) {
        this.marketDataJPARepository = marketDataJPARepository;
    }

    public MarketData marketDataSave(MarketData marketData) {
        marketDataJPARepository.save(marketData);
        return marketData;
    }
}
