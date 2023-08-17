package com.stock.oppenheimer.DTO.converters;


import com.stock.oppenheimer.DTO.MktDataDTO;
import com.stock.oppenheimer.domain.MarketData;
import org.springframework.core.convert.converter.Converter;

public class MktDataApiToTickerMarketDataConverter implements Converter<MktDataDTO, MarketData> {
    @Override
    public MarketData convert(MktDataDTO source) {
        MarketData marketData = new MarketData();
        marketData.high = source.high;
        marketData.low = source.low;
        marketData.volume = source.volume;
        marketData.open = source.open;
        marketData.close = source.close;
        marketData.tradeDate = source.tradeDate;
        return marketData;
    }
}
