package com.stock.oppenheimer.WebAPI.sync;

import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;

import java.time.LocalDate;
import java.util.List;

public interface ApiServiceSync {
    StockDataDTO fetchStockInfo(String tickerToRetrieve, String stockNameToRetrieve);

    List<MktDataDTO> fetchMarketData(String tickerToRetrieve, String stockNameToRetrieve,
                                     LocalDate fromDate, LocalDate toDate);
}