package com.stock.oppenheimer.service;

import com.stock.oppenheimer.DTO.StockDataDTO;
import com.stock.oppenheimer.WebAPI.ApiService;
import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class StockDataService {
    private final TickerDataRepository tickerDataRepository;
    private final MarketDataService marketDataService;
    private final ApiService apiService;
    private final ConversionService conversionService;

    @Autowired
    public StockDataService(TickerDataRepository tickerDataRepository, MarketDataService marketDataService, ApiService apiService, ConversionService conversionService) {
        this.tickerDataRepository = tickerDataRepository;
        this.marketDataService = marketDataService;
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
    public Mono<StockData> addStockData(String stockName, String tickerName) {
        return (stockName != null)
                ? fetchStockData(stockName, false)
                : fetchStockData(tickerName, true);
    }

    public StockData removeByStockName(String stockName) {
        return tickerDataRepository.deleteByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataRepository.findByStockName(stockName);
    }

    public Mono<StockData> fetchStockData (String inputString ,boolean isTicker) {
        if(isTicker) {
            return apiService.fetchStockInfo(inputString, null)
                    .flatMap(this::saveStockData);
        }
        else{
            return apiService.fetchStockInfo(null, inputString)
                    .flatMap(this::saveStockData);
        }
    }

//helper methods
    private Mono<StockData> saveStockData(StockDataDTO stockDataDTO) {
        StockData stockData = conversionService.convert(stockDataDTO, StockData.class);
        return Mono.fromCallable(() -> tickerDataRepository.save(stockData));
    }


}