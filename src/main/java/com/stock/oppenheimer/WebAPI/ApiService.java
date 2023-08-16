package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public interface ApiService {

    Mono<StockTickerData> fetchStockInfoApi(String tickerToRetrieve,
                                            @Value("${SERVICE_KEY_ENV_VAR}") String serviceKey);

    Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, Date fromDate, Date toDate);


}
