package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public interface ApiService {


    //    gets data of the stock
    Mono<StockDataDTO> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    Mono<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                     LocalDate fromDate, LocalDate toDate);

}
