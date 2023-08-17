package com.stock.oppenheimer.WebAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Date;

public class KospiApiService implements ApiService{

    private final WebClient webClient;
    private final String serviceKey;

    @Autowired
    public KospiApiService(@Qualifier("KOSPIApiClient")WebClient webclient, @Value("${SERVICE_KEY_ENV_VAR}") String serviceKey) {
        this.webClient = webclient;
        this.serviceKey = serviceKey;
    }
    //    gets data of the stock
    @Override
    public Mono<StockTickerData> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve) {

        UriComponentsBuilder queryUri = baseURISetting(tickerToRetrieve, stockNameToRetrieve);


        return webClient
                .get()    //set up the HTTP method and request headers
                .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(KospiApiService::extractStockTickerDataMono
                );
    }


    @Override
    public Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, String stockNameToRetrieve,
                                                     Date fromDate, Date toDate) {

        UriComponentsBuilder queryUri = baseURISetting(tickerToRetrieve, stockNameToRetrieve);
        return webClient
                .get()    //set up the HTTP method and request headers
                .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(KospiApiService::extractMktDataMono
                );

    }

    private UriComponentsBuilder baseURISetting(String tickerToRetrieve, String stockNameToRetrieve) {
        UriComponentsBuilder queryUri = UriComponentsBuilder.newInstance()
                .queryParam("servicekey", serviceKey)
                .queryParam("numOfRows", Integer.MAX_VALUE)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json");

        if(tickerToRetrieve != null) {
            queryUri.queryParam("likeSrtnCd", tickerToRetrieve);
        }

        else {
            queryUri.queryParam("itmsNm", stockNameToRetrieve);
        }
        return queryUri;
    }

    private static Mono<StockTickerData> extractStockTickerDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KOSPIAPIDTO KOSPIAPIDTO = objectMapper.readValue(apiResponse, KOSPIAPIDTO.class);
            StockTickerData stockTickerData = new StockTickerData();
            stockTickerData.stockName = KOSPIAPIDTO.itmsNm;
            stockTickerData.mktCtg = KOSPIAPIDTO.mktCtg;
            stockTickerData.ticker = KOSPIAPIDTO.strnCd;
            return Mono.just(stockTickerData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }

    private static Mono<TickerMarketData> extractMktDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KOSPIAPIDTO KOSPIAPIDTO = objectMapper.readValue(apiResponse, KOSPIAPIDTO.class);
            TickerMarketData marketData = new TickerMarketData();
            marketData.high = KOSPIAPIDTO.hipr;
            marketData.low = KOSPIAPIDTO.lopr;
            marketData.volume = KOSPIAPIDTO.trqu;
            marketData.open = KOSPIAPIDTO.mkp;
            marketData.close = KOSPIAPIDTO.dpr;
            return Mono.just(marketData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }


}
