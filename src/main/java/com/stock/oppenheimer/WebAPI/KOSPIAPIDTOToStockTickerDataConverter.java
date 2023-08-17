package com.stock.oppenheimer.WebAPI;

import com.stock.oppenheimer.domain.StockTickerData;
import org.springframework.core.convert.converter.Converter;


public class KOSPIAPIDTOToStockTickerDataConverter implements Converter<KOSPIAPIDTO, StockTickerData> {
    @Override
    public StockTickerData convert(KOSPIAPIDTO source) {
        StockTickerData stockTickerData = new StockTickerData();
        stockTickerData.stockName = source.itmsNm;
        stockTickerData.mktCtg = source.mktCtg;
        stockTickerData.ticker = source.strnCd;
        return stockTickerData;
    }
}
