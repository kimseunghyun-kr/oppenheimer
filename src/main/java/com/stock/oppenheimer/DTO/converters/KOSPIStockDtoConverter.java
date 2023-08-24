package com.stock.oppenheimer.DTO.converters;

import com.stock.oppenheimer.DTO.Kospi.KospiDTO;
import com.stock.oppenheimer.DTO.Kospi.KospiDTOItem;
import com.stock.oppenheimer.DTO.StockDataDTO;
import org.springframework.core.convert.converter.Converter;

public class KOSPIStockDtoConverter implements Converter<KospiDTO, StockDataDTO> {
    @Override
    public StockDataDTO convert(KospiDTO source) {
        KospiDTOItem KospiDtoItem = source.getResponse().getBody().getItems().getItem().get(0);
        StockDataDTO stockDataDTO = new StockDataDTO();
        stockDataDTO.setStockName(KospiDtoItem.itmsNm);
        stockDataDTO.setMktCtg(KospiDtoItem.mrktCtg);
        stockDataDTO.setTicker(KospiDtoItem.srtnCd);
        stockDataDTO.setCount(source.getResponse().getBody().getTotalCount());
        return stockDataDTO;
    }
}
