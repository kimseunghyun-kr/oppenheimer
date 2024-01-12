package com.stock.oppenheimer.WebAPI.sync;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import com.stock.oppenheimer.WebAPI.async.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WebclientAdapter implements ApiServiceSync {

    private final ApiService apiService;

    @Autowired
    public WebclientAdapter(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public StockDataDTO fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve) {
        Mono<StockDataDTO> resultMono = apiService.fetchStockInfo(tickerToRetrieve, stockNameToRetrieve);

        return resultMono.block();
    }

    @Override
    public List<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve, LocalDate fromDate, LocalDate toDate) {
        Flux<MktDataDTO> resultFlux = apiService.fetchMarketData(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate);
        return resultFlux.collectList().block();
    }



}
