package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

public class KospiApiService implements ApiService{

    private final WebClient webClient;

    @Autowired
    public KospiApiService(@Qualifier("KOSPIApiClient")WebClient webclient) {
        this.webClient = webclient;
    }
    @Override
    public Mono<StockTickerData> fetchStockInfoApi(String tickerToRetrieve) {
        return null;
    }

    @Override
    public Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, Date fromDate, Date toDate) {
        return null;
    }
}
