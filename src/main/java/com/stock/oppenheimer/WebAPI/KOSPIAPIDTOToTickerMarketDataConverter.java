package com.stock.oppenheimer.WebAPI;


import com.stock.oppenheimer.domain.TickerMarketData;
import org.springframework.core.convert.converter.Converter;

public class KOSPIAPIDTOToTickerMarketDataConverter implements Converter<KOSPIAPIDTO, TickerMarketData> {
    @Override
    public TickerMarketData convert(KOSPIAPIDTO source) {
        TickerMarketData marketData = new TickerMarketData();
        marketData.high = source.hipr;
        marketData.low = source.lopr;
        marketData.volume = source.trqu;
        marketData.open = source.mkp;
        marketData.close = source.dpr;
        marketData.date = source.basDt;
        return marketData;
    }
}
