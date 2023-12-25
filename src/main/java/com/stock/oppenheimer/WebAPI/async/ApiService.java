package com.stock.oppenheimer.WebAPI.async;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ApiService {


    //    gets data of the stock
    Mono<StockDataDTO> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    Flux<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                     LocalDate fromDate, LocalDate toDate);

}
