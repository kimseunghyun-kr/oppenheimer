package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.WebAPI.async.ApiService;
import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.MarketDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
//@Transactional
public class MarketDataService {

    private final MarketDataRepository marketDataRepository;
    private final ConversionService conversionService;
    private final ApiService apiService;

    @Autowired
    public MarketDataService(MarketDataRepository marketDataRepository, ConversionService conversionService, ApiService apiService) {
        this.marketDataRepository = marketDataRepository;
        this.conversionService = conversionService;
        this.apiService = apiService;
    }


    public Flux<MarketData> fetchMarketData(StockData savedStockData, LocalDate fromDate, LocalDate toDate) {
        return apiService.fetchMarketData(savedStockData.getTicker(), null, fromDate, toDate)
                .flatMap(marketDataDTO -> saveMarketData(marketDataDTO, savedStockData));
    }

    public Flux<MarketData> saveMarketData(MktDataDTO marketDataDTO, StockData savedStockData) {
        MarketData marketData = conversionService.convert(marketDataDTO, MarketData.class);
        marketData.setStockData(savedStockData);
        marketDataRepository.save(marketData);
        return Flux.just(marketData);
    }

    public List<MarketData> findByStockName(String stockName) {
        return marketDataRepository.findAllByStockDataStockName(stockName);
    }

}
