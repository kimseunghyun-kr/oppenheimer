package com.stock.oppenheimer.service;

import com.stock.oppenheimer.WebAPI.KospiApiService;
import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockDataService {

    private TickerDataRepository tickerDataRepository;
    private KospiApiService kospiApiService;

    private ConversionService conversionService;
//    public List<String> findAllAvailableTickers() {
//        return tickerDataRepository.findAll().stream().map(x-> x.ticker).toList();
//    }

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
        return kospiApiService.fetchStockInfo(tickerName, null)
                .flatMap(stockApiDTO-> {
                    StockData stockData = conversionService.convert(stockApiDTO, StockData.class);
                    return Mono.fromCallable(()->tickerDataRepository.save(stockData));
                });

    }

    public StockData removeByStockName(String stockName) {
        return tickerDataRepository.findByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataRepository.deleteByStockName(stockName);
    }

    public Mono<StockData> addByStockName(String stockName) {
        return kospiApiService.fetchStockInfo(null, stockName)
                .flatMap(stockApiDTO-> {
                    StockData stockData = conversionService.convert(stockApiDTO, StockData.class);
                    return Mono.fromCallable(()->tickerDataRepository.save(stockData));
                });

    }
}
