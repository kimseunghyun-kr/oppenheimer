package com.stock.oppenheimer.DTO.converters;

import com.stock.oppenheimer.DTO.KOSPIStockDataDTO;
import com.stock.oppenheimer.DTO.StockDataDTO;
import org.springframework.core.convert.converter.Converter;

public class KOSPIStockConverter implements Converter<KOSPIStockDataDTO, StockDataDTO> {
    @Override
    public StockDataDTO convert(KOSPIStockDataDTO source) {
        StockDataDTO stockDataDTO = new StockDataDTO();
        stockDataDTO.setStockName(source.itmsNm);
        stockDataDTO.setMktCtg(source.mktCtg);
        stockDataDTO.setTicker(source.strnCd);
        return stockDataDTO;
    }
}
