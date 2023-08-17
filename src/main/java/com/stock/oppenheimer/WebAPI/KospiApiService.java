package com.stock.oppenheimer.WebAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Date;

public class KospiApiService implements ApiService{

    private final WebClient webClient;
    private final String serviceKey;

    private final ConversionService conversionService;

    @Autowired
    public KospiApiService(@Qualifier("KOSPIApiClient")WebClient webclient,
                           ConversionService conversionService,
                           @Value("${SERVICE_KEY_ENV_VAR}") String serviceKey) {
        this.webClient = webclient;
        this.serviceKey = serviceKey;
        this.conversionService = conversionService;
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
                .flatMap(this::extractStockTickerDataMono
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
                .flatMap(this::extractMktDataMono
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

    private Mono<StockTickerData> extractStockTickerDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KOSPIAPIDTO KOSPIAPIDTO = objectMapper.readValue(apiResponse, KOSPIAPIDTO.class);
            StockTickerData stockTickerData = conversionService.convert(KOSPIAPIDTO, StockTickerData.class);
//            assert stockTickerData != null;
            return Mono.just(stockTickerData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }

    private Mono<TickerMarketData> extractMktDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KOSPIAPIDTO KOSPIAPIDTO = objectMapper.readValue(apiResponse, KOSPIAPIDTO.class);
            TickerMarketData marketData = conversionService.convert(KOSPIAPIDTO, TickerMarketData.class);
//            assert marketData != null;
            return Mono.just(marketData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }


}
