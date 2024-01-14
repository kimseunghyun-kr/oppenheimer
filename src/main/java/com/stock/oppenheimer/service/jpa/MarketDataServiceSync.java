package com.stock.oppenheimer.service.jpa;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.WebAPI.sync.ApiServiceSync;
import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.jpaRepository.MarketDataJPARepository;
import com.stock.oppenheimer.service.MarketDataSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MarketDataServiceSync {

    private final ApiServiceSync apiService;
    private final ConversionService conversionService;
    private final MarketDataSave marketDataSave;

    private final MarketDataJPARepository marketDataJPARepository;

    @Autowired
    public MarketDataServiceSync(@Qualifier("webclientAdapter") ApiServiceSync apiService, ConversionService conversionService, MarketDataSave marketDataSave, MarketDataJPARepository marketDataJPARepository) {
        this.apiService = apiService;
        this.conversionService = conversionService;
        this.marketDataSave = marketDataSave;
        this.marketDataJPARepository = marketDataJPARepository;
    }

    public List<MarketData> fetchMarketData(StockData savedStockData, LocalDate fromDate, LocalDate toDate) {
        List<MktDataDTO> marketDataList = apiService.fetchMarketData(savedStockData.getTicker(), null, fromDate, toDate);
        return marketDataList.stream().map(marketDataDTO -> saveMarketData(marketDataDTO, savedStockData)).toList();
    }

    @Transactional
    public MarketData saveMarketData(MktDataDTO marketDataDTO, StockData savedStockData) {
        MarketData marketData = conversionService.convert(marketDataDTO, MarketData.class);
        marketData.setStockData(savedStockData);
        return marketDataSave.marketDataSave(marketData);
    }

    public List<MarketData> findByStockName(String stockName) {
        return marketDataJPARepository.findAllByStockDataStockName(stockName);
    }
}
