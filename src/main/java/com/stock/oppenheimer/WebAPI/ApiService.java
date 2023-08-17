package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public interface ApiService {


    //    gets data of the stock
    Mono<StockTickerData> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    Mono<TickerMarketData> fetchMarketDataApi(String tickerToRetrieve, String stockNameToRetrieve,
                                              Date fromDate, Date toDate);

}
