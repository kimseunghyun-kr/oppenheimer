package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;

import java.time.LocalDate;
import java.util.List;

public interface ApiService {
    StockDataDTO fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    List<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                     LocalDate fromDate, LocalDate toDate);
}
