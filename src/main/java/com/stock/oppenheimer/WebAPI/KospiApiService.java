package com.stock.oppenheimer.WebAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.oppenheimer.DTO.Kospi.KospiDTO;
import com.stock.oppenheimer.DTO.Kospi.KospiDTOItem;
import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Receive Data from External API: this class is responsible for making the necessary calls to the external API, receiving the data, and possibly handling any error cases that might arise during the API interaction.
 * Parse Data into DTO: After receiving the data, this class is responsible for parsing and transforming it into a DTO (Data Transfer Object) that is suitable for consumption by other parts of your application.
 * Return DTO in a Reactive Wrapper: this class can wrap the parsed DTO in a reactive type (such as Mono or Flux, depending on your use case) to indicate the asynchronous nature of the data retrieval and processing.
 */
@Service
@Slf4j
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
    public Mono<StockDataDTO> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve) {
        //            UriComponentsBuilder queryUri = baseURISetting(tickerToRetrieve, stockNameToRetrieve);
        return webClient
                .get()
                .uri(queryURIBuilder(tickerToRetrieve, stockNameToRetrieve))//set up the HTTP method and request headers
//                    .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::extractStockTickerDataMono
                );
    }

    private Function<UriBuilder, URI> queryURIBuilder(String tickerToRetrieve, String stockNameToRetrieve) {
        return uriBuilder -> {
            uriBuilder.queryParam("servicekey", serviceKey)
                    .queryParam("numOfRows", 10)
                    .queryParam("pageNo", 1)
                    .queryParam("resultType", "json");

            if (tickerToRetrieve != null) {
//                String encodedString = URLEncoder.encode(tickerToRetrieve, StandardCharsets.UTF_8);
                uriBuilder.queryParam("likeSrtnCd", tickerToRetrieve);
            } else if (stockNameToRetrieve != null) {
//                String encodedString = URLEncoder.encode(stockNameToRetrieve, StandardCharsets.UTF_8);
                uriBuilder.queryParam("itmsNm", stockNameToRetrieve);
            }
            return uriBuilder.build();
        };
    }


    @Override
    public Mono<List<MktDataDTO>> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                            LocalDate fromDate, LocalDate toDate) {
        return webClient
                .get()    //set up the HTTP method and request headers
                .uri(queryURIBuilder(tickerToRetrieve,stockNameToRetrieve))
//                .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::extractMktDataMono
                );



    }

    private Mono<StockDataDTO> extractStockTickerDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KospiDTOItem KospiDtoItem = objectMapper.readValue(apiResponse, KospiDTO.class).getResponse().getBody().getItems().getItem().get(0);
            StockDataDTO stockDataDTO = conversionService.convert(KospiDtoItem, StockDataDTO.class);
//            assert stockTickerData != null;
            return Mono.justOrEmpty(stockDataDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }

    private Mono<List<MktDataDTO>> extractMktDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            List<MktDataDTO> marketDataList = new ArrayList<>();
            List<KospiDTOItem> kospiDtoList = objectMapper.readValue(apiResponse, KospiDTO.class).getResponse().getBody().getItems().getItem();
            for (KospiDTOItem kospiDTO : kospiDtoList) {
                MktDataDTO mktDataDTO = conversionService.convert(kospiDTO, MktDataDTO.class);
                marketDataList.add(mktDataDTO);
//            assert marketData != null;
            }
            return Mono.justOrEmpty(marketDataList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }


}
