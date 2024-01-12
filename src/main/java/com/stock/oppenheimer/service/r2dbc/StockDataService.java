package com.stock.oppenheimer.service.r2dbc;

import com.stock.oppenheimer.WebAPI.async.ApiService;
import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
@Slf4j
@Transactional
public class StockDataService {
    private final TickerDataJPARepository tickerDataJPARepository;
    private final ApiService apiService;
    private final ConversionService conversionService;
    private final StockDataSave stockDataSave;

    @Autowired
    public StockDataService(TickerDataJPARepository tickerDataJPARepository, ApiService apiService, ConversionService conversionService, StockDataSave stockDataSave) {
        this.tickerDataJPARepository = tickerDataJPARepository;
        this.apiService = apiService;
        this.conversionService = conversionService;
        this.stockDataSave = stockDataSave;
    }

    public Page<StockData> findAllMatching(TickerSearchConditionDTO searchDTO, Pageable pageable) {

        // create a specification object
        Specification<StockData> spec = new TickerSpecification(searchDTO);
        return tickerDataJPARepository.findAll(spec, pageable);
    }

    public StockData findByTickerName(String tickerName) {
        return tickerDataJPARepository.findByTicker(tickerName);
    }

    public StockData removeByTickerName(String tickerName) {
        return tickerDataJPARepository.deleteByTicker(tickerName);
    }

    // method reference does not work here because -> Method references are better suited for direct method calls, while lambdas might provide more flexibility when dealing with reactive chains, especially if you need to perform multiple operations or access surrounding variables.
    public Mono<StockData> addStockData(String stockName, String tickerName) {
        return (stockName != null)
                ? fetchStockData(stockName, false)
                : fetchStockData(tickerName, true);
    }

    public StockData removeByStockName(String stockName) {
        return tickerDataJPARepository.deleteByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataJPARepository.findByStockName(stockName);
    }

    public Mono<StockData> fetchStockData (String inputString ,boolean isTicker) {
        if(isTicker) {
            return apiService.fetchStockInfo(inputString, null)
                    .flatMap(stockDataDTO -> {
                        StockData stockData = conversionService.convert(stockDataDTO, StockData.class);
//                        quick conversion failure check.
                        if(stockData == null) {
                            return Mono.empty();
                        }
                        stockData.setLastUpdatedDate(LocalDate.now());
                        return saveStockData(stockData);
                    });
        }
        else{
            return apiService.fetchStockInfo(null, inputString)
                    .flatMap(stockDataDTO -> {
                        StockData stockData = conversionService.convert(stockDataDTO, StockData.class);
//                        quick conversion failure check
                        if(stockData == null) {
                            return Mono.empty();
                        }
                        stockData.setLastUpdatedDate(LocalDate.now());
                        return saveStockData(stockData);
                    });
        }
    }

//helper methods
    public Mono<StockData> saveStockData(StockData stockData) {
//        return Mono.fromCallable(() -> {
//            log.info("transactionCheck          {}" , TransactionSynchronizationManager.isActualTransactionActive());
//            return stockDataSave.stockDataSave(stockData);
//        });
        return Mono.just(stockData).map(stockDataSave::stockDataSave);
    }


}