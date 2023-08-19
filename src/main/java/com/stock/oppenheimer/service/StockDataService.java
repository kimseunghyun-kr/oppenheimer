package com.stock.oppenheimer.service;

import com.stock.oppenheimer.WebAPI.ApiService;
import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.MarketData;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.MarketDataRepository;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class StockDataService {
    private final TickerDataRepository tickerDataRepository;
    private final MarketDataRepository marketDataRepository;
    private final ApiService apiService;
    private final ConversionService conversionService;

    @Autowired
    public StockDataService(TickerDataRepository tickerDataRepository, MarketDataRepository marketDataRepository, ApiService apiService, ConversionService conversionService) {
        this.tickerDataRepository = tickerDataRepository;
        this.marketDataRepository = marketDataRepository;
        this.apiService = apiService;
        this.conversionService = conversionService;
    }

    public Page<StockData> findAllMatching(TickerSearchConditionDTO searchDTO, Pageable pageable) {

        // create a specification object
        Specification<StockData> spec = new TickerSpecification(searchDTO);
        return tickerDataRepository.findAll(spec, pageable);
    }

    public StockData findByTickerName(String tickerName) {
        return tickerDataRepository.findByTicker(tickerName);
    }

    public StockData removeByTickerName(String tickerName) {
        return tickerDataRepository.deleteByTicker(tickerName);
    }

    public Mono<StockData> addByTickerName(String tickerName) {
        Mono<StockData> sd =  apiService.fetchStockInfo(tickerName, null)
                .flatMap(stockDataDTO-> {
                    StockData stockData = conversionService.convert(stockDataDTO, StockData.class);
                    return Mono.fromCallable(()->tickerDataRepository.save(stockData))
                            .thenReturn(stockData);
                });

        apiService.fetchMarketData(tickerName, null, null, LocalDate.now())
                .flatMap(marketDataDTO -> {
                    MarketData marketData = conversionService.convert(marketDataDTO, MarketData.class);
                    return Mono.fromCallable(()->marketDataRepository.save(marketData))
                            .doOnNext(savedMktData-> log.info("mktData, {}", savedMktData))
                            .then();
                });

        return sd;

    }

    public StockData removeByStockName(String stockName) {
        return tickerDataRepository.findByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataRepository.deleteByStockName(stockName);
    }

    public Mono<StockData> addByStockName(String stockName) {
        Mono<StockData> sd =  apiService.fetchStockInfo(null, stockName)
                .flatMap(stockDataDTO-> {
                    StockData stockData = conversionService.convert(stockDataDTO, StockData.class);
                    return Mono.fromCallable(()->tickerDataRepository.save(stockData));
                });
        Mono<MarketData> md = apiService.fetchMarketData(null, stockName, null, LocalDate.now())
                .flatMap(marketDataDTO -> {
                    MarketData marketData = conversionService.convert(marketDataDTO, MarketData.class);
                    return Mono.fromCallable(()->marketDataRepository.save(marketData));
                });

        return sd;

    }
}
