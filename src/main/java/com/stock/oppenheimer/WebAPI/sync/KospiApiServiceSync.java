package com.stock.oppenheimer.WebAPI.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.oppenheimer.DTO.Kospi.KospiDTO;
import com.stock.oppenheimer.DTO.Kospi.KospiDTOItem;
import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KospiApiServiceSync implements ApiServiceSync {

    private static final String BASE_URL = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    private static final Integer NUMROWS = 30;
    private final RestTemplate restTemplate;
    private final String serviceKey;

    private final ConversionService conversionService;

    @Autowired
    public KospiApiServiceSync(RestTemplate restTemplate,
                               ConversionService conversionService,
                               @Value("${SERVICE_KEY_ENV_VAR}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
        this.conversionService = conversionService;
    }

    @Override
    public StockDataDTO fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve) {
        URI uri = buildUri(tickerToRetrieve, stockNameToRetrieve, null, null, 1);
        String apiResponse = restTemplate.getForObject(uri, String.class);
        return extractStockTickerData(apiResponse);
    }

    @Override
    public List<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                            LocalDate fromDate, LocalDate toDate) {
        return fetchMarketDataPage(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, 1);
    }

    private List<MktDataDTO> fetchMarketDataPage(String tickerToRetrieve, String stockNameToRetrieve,
                                                 LocalDate fromDate, LocalDate toDate, int page) {
        if (page > 3) {
            return new ArrayList<>();
        }

        URI uri = buildUri(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, page);
        String apiResponse = restTemplate.getForObject(uri, String.class);
        List<MktDataDTO> currentData = extractMktData(apiResponse);

        if (currentData.isEmpty()) {
            return new ArrayList<>();
        }

        List<MktDataDTO> result = new ArrayList<>(currentData);
        result.addAll(fetchMarketDataPage(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, page + 1));
        return result;
    }

    private URI buildUri(String tickerToRetrieve, String stockNameToRetrieve, LocalDate fromDate, LocalDate toDate, Integer page) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        uriBuilder.queryParam("servicekey", serviceKey)
                .queryParam("numOfRows", NUMROWS)
                .queryParam("pageNo", page)
                .queryParam("resultType", "json");

        uriParams(tickerToRetrieve, stockNameToRetrieve, fromDate, toDate, uriBuilder);
        return uriBuilder.build().toUri();
    }

    private void uriParams(String tickerToRetrieve, String stockNameToRetrieve, LocalDate fromDate, LocalDate toDate, UriComponentsBuilder uriBuilder) {
        if (tickerToRetrieve != null) {
            uriBuilder.queryParam("likeSrtnCd", tickerToRetrieve);
        } else if (stockNameToRetrieve != null) {
            uriBuilder.queryParam("itmsNm", stockNameToRetrieve);
        }
        if (fromDate != null) {
            uriBuilder.queryParam("beginBasDt", fromDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        if (toDate != null) {
            uriBuilder.queryParam("endBasDt", toDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }
    }

    private StockDataDTO extractStockTickerData(String apiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KospiDTO kospiDTO = objectMapper.readValue(apiResponse, KospiDTO.class);
            return conversionService.convert(kospiDTO, StockDataDTO.class);
        } catch (Exception e) {
            log.error("Error extracting stock ticker data", e);
            throw new RuntimeException(e);
        }
    }

    private List<MktDataDTO> extractMktData(String apiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KospiDTO kospiDTO = objectMapper.readValue(apiResponse, KospiDTO.class);
            List<KospiDTOItem> kospiDtoList = kospiDTO.getResponse().getBody().getItems().getItem();

            List<MktDataDTO> marketDataList = new ArrayList<>();
            for (KospiDTOItem itemDTO : kospiDtoList) {
                MktDataDTO mktDataDTO = conversionService.convert(itemDTO, MktDataDTO.class);
                marketDataList.add(mktDataDTO);
            }
            return marketDataList;

        } catch (Exception e) {
            log.error("Error extracting market data", e);
            throw new RuntimeException(e);
        }
    }
}
