package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.WebAPI.ApiService;
import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.repository.MarketDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
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


    public Flux<MarketData> fetchMarketData(StockData savedStockData) {
        return apiService.fetchMarketData(savedStockData.getTicker(), null, null, LocalDate.now())
                .flatMapMany(marketDataDTOList -> saveMarketData(marketDataDTOList, savedStockData));
    }

    public Flux<MarketData> saveMarketData(List<MktDataDTO> marketDataDTOList, StockData savedStockData) {
        return Flux.fromIterable(marketDataDTOList)
                .flatMap(mktDataDTO -> {
                    MarketData marketData = conversionService.convert(mktDataDTO, MarketData.class);
                    marketData.setStockData(savedStockData);
                    marketDataRepository.save(marketData);
                    return Mono.just(marketData);
                });
    }
    
    public List<MarketData> findByStockName(String stockName) {
        return marketDataRepository.findAllByStockDataStockName(stockName);
    }

}
