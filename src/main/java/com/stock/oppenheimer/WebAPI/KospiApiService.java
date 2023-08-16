package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    public Mono<StockTickerData> fetchStockInfoApi(String tickerToRetrieve,
                                                   @Value("${SERVICE_KEY_ENV_VAR}") String serviceKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stocks/{ticker}")
                        .queryParam("servicekey", serviceKey)
                        .queryParam("numOfRows", Integer.MAX_VALUE)
                        .queryParam("pageNo", 1)
                        .queryParam("resultType", "json")
                        .queryParam("likeSrtnCd", tickerToRetrieve)
                        .build(tickerToRetrieve))
                .retrieve()
                .bodyToMono(StockTickerData.class);
    }

    @Override
    public Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, Date fromDate, Date toDate) {
        return null;
    }
}
