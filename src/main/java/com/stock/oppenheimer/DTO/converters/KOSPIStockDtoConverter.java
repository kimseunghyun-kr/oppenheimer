package com.stock.oppenheimer.DTO.converters;

import com.stock.oppenheimer.DTO.Kospi.KospiDTOItem;
import com.stock.oppenheimer.DTO.StockDataDTO;
import org.springframework.core.convert.converter.Converter;

public class KOSPIStockDtoConverter implements Converter<KospiDTOItem, StockDataDTO> {
    @Override
    public StockDataDTO convert(KospiDTOItem source) {
        StockDataDTO stockDataDTO = new StockDataDTO();
        stockDataDTO.setStockName(source.itmsNm);
        stockDataDTO.setMktCtg(source.mrktCtg);
        stockDataDTO.setTicker(source.srtnCd);
        return stockDataDTO;
    }
}
