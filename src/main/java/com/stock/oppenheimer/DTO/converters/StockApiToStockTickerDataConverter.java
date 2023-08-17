package com.stock.oppenheimer.DTO.converters;

import com.stock.oppenheimer.DTO.StockDataDTO;
import com.stock.oppenheimer.domain.StockData;
import org.springframework.core.convert.converter.Converter;


public class StockApiToStockTickerDataConverter implements Converter<StockDataDTO, StockData> {
    @Override
    public StockData convert(StockDataDTO source) {
        StockData stockData = new StockData();
        stockData.stockName = source.stockName;
        stockData.mktCtg = source.mktCtg;
        stockData.ticker = source.ticker;
        return stockData;
    }
}
