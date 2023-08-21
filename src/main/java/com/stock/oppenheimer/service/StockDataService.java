package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // method reference does not work here because -> Method references are better suited for direct method calls, while lambdas might provide more flexibility when dealing with reactive chains, especially if you need to perform multiple operations or access surrounding variables.
    public Mono<StockData> addByTickerName(String tickerName) {
        return apiService.fetchStockInfo(tickerName, null)
                .flatMap(this::saveStockDataAndFetchMarketData);
    }

    //backup method in case async fails or something
    public StockData addByTickerNameSerialised(String tickerName) {
        return this.addByTickerName(tickerName).block();
    }

    public StockData removeByStockName(String stockName) {
        return tickerDataRepository.findByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataRepository.deleteByStockName(stockName);
    }

    public Mono<StockData> addByStockName(String stockName) {
        return apiService.fetchStockInfo(null, stockName)
                .flatMap(this::saveStockDataAndFetchMarketData);

    }


//helper methods

    private Mono<StockData> saveStockDataAndFetchMarketData(StockDataDTO stockDataDTO) {
        StockData stockData = conversionService.convert(stockDataDTO, StockData.class);

        return Mono.fromCallable(() -> tickerDataRepository.save(stockData))
                .flatMap(savedStockData -> fetchAndSaveMarketData(savedStockData).collectList().thenReturn(savedStockData));
    }

    private Flux<MarketData> fetchAndSaveMarketData(StockData savedStockData) {
        return apiService.fetchMarketData(savedStockData.getTicker(), null, null, LocalDate.now())
                .flatMapMany(marketDataDTOList -> convertAndSaveMarketData(marketDataDTOList, savedStockData));
    }

    private Flux<MarketData> convertAndSaveMarketData(List<MktDataDTO> marketDataDTOList, StockData savedStockData) {
        return Flux.fromIterable(marketDataDTOList)
                .flatMap(mktDataDTO -> {
                    MarketData marketData = conversionService.convert(mktDataDTO, MarketData.class);
                    marketData.setStockData(savedStockData);
                    marketDataRepository.save(marketData);
                    return Mono.just(marketData);
                });
    }

}
