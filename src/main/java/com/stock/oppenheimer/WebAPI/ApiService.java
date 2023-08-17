package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public interface ApiService {


    //    gets data of the stock
    Mono<StockDataDTO> fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    Mono<MktDataDTO> fetchMarketDataApi(String tickerToRetrieve, String stockNameToRetrieve,
                                        Date fromDate, Date toDate);

}
