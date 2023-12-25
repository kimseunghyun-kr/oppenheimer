package com.stock.oppenheimer.WebAPI.async;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class KospiApiService implements ApiService {

    private static final Integer NUMROWS = 30;
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
    //    stockDataDTO returned can never be null
    @Override
    public Mono<StockDataDTO> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve) {
        //            UriComponentsBuilder queryUri = baseURISetting(tickerToRetrieve, stockNameToRetrieve);
        return webClient
                .get()
                .uri(queryURIBuilder(tickerToRetrieve, stockNameToRetrieve,null,null,1))//set up the HTTP method and request headers
//                    .uri(queryUri.build().toUri())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::extractStockTickerDataMono
                );
    }

    private Function<UriBuilder, URI> queryURIBuilder(String tickerToRetrieve, String stockNameToRetrieve, LocalDate fromDate, LocalDate toDate, Integer page) {
        return uriBuilder -> {
            uriBuilder.queryParam("servicekey", serviceKey)
                    .queryParam("numOfRows", NUMROWS)
                    .queryParam("pageNo", page)
                    .queryParam("resultType", "json");

            uriParams(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, uriBuilder);
            return uriBuilder.build();
        };
    }

    private static void uriParams(String tickerToRetrieve, String stockNameToRetrieve, LocalDate fromDate, LocalDate toDate, UriBuilder uriBuilder) {
        if (tickerToRetrieve != null) {
//                String encodedString = URLEncoder.encode(tickerToRetrieve, StandardCharsets.UTF_8);
            uriBuilder.queryParam("likeSrtnCd", tickerToRetrieve);
        } else if (stockNameToRetrieve != null) {
//                String encodedString = URLEncoder.encode(stockNameToRetrieve, StandardCharsets.UTF_8);
            uriBuilder.queryParam("itmsNm", stockNameToRetrieve);
        }
        if (fromDate != null) {
            uriBuilder.queryParam("beginBasDt", fromDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        if (toDate != null) {
            uriBuilder.queryParam("endBasDt", toDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//            uriBuilder.queryParam("basDt", toDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }
    }


    @Override
    public Flux<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                            LocalDate fromDate, LocalDate toDate) {
        return fetchMarketDataPage(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, 1)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    private Flux<MktDataDTO> fetchMarketDataPage(String tickerToRetrieve, String stockNameToRetrieve,
                                                 LocalDate fromDate, LocalDate toDate, int page) {
        if (page > 3) {
            return Flux.empty();
        }
        return webClient.get()
                .uri(queryURIBuilder(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, page))
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(apiResponse -> {
                    // Process the current response
                    List<MktDataDTO> currentData = extractMktDataMono(apiResponse);
                    // next page
                    if (currentData.isEmpty()) {
                        return Flux.empty();
                    }
                    Flux<MktDataDTO> currFlux = Flux.fromIterable(currentData);
                    Flux<MktDataDTO> nextPageData = fetchMarketDataPage(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, page + 1);
                    // Combine the results of the current and next page
                    return currFlux.concatWith(nextPageData);
                });
    }

    private Mono<StockDataDTO> extractStockTickerDataMono(String apiResponse) {
        try {
            // Use Jackson ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            KospiDTO kospiDTO = objectMapper.readValue(apiResponse, KospiDTO.class);
            StockDataDTO stockDataDTO = conversionService.convert(kospiDTO, StockDataDTO.class);
//            assert stockTickerData != null;
            return Mono.justOrEmpty(stockDataDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO
        }
    }

    private List<MktDataDTO> extractMktDataMono(String apiResponse){
        // Use Jackson ObjectMapper to parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        List<MktDataDTO> marketDataList = new ArrayList<>();
        KospiDTO kospiDTO = null;
        try {
            kospiDTO = objectMapper.readValue(apiResponse, KospiDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<KospiDTOItem> kospiDtoList = kospiDTO.getResponse().getBody().getItems().getItem();

        for (KospiDTOItem itemDTO : kospiDtoList) {
            MktDataDTO mktDataDTO = conversionService.convert(itemDTO, MktDataDTO.class);
            marketDataList.add(mktDataDTO);
//            assert marketData != null;
        }

        return marketDataList;

    }


}

