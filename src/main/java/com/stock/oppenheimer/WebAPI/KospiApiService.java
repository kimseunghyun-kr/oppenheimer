package com.stock.oppenheimer.WebAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.oppenheimer.domain.StockTickerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

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


        return webClient
                .get()    //set up the HTTP method and request headers
                .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(apiResponse -> {
                            try {
                                // Use Jackson ObjectMapper to parse JSON
                                ObjectMapper objectMapper = new ObjectMapper();
                                Map<String, Object> valueMap = objectMapper.readValue(apiResponse, Map.class);
                                StockTickerData stockTickerData = new StockTickerData();
                                stockTickerData.stockName = (String) valueMap.get("itmsNm");
                                stockTickerData.mktCtg = (String)valueMap.get("mktCtg");
                                stockTickerData.ticker = (String)valueMap.get("strnCd");
                                return Mono.just(stockTickerData);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e); //TODO
                            }
                        }
                        );
    }

//    @Override
//    public Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, String stockNameToRetrieve,
//                                                     Date fromDate, Date toDate) {
//        return webClient.get()
//                .build(tickerToRetrieve))
//                .retrieve()
//                .bodyToMono(TickerMarketData.class);
//    }
}
