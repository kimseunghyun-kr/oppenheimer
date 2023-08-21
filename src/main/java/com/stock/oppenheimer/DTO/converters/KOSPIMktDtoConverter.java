package com.stock.oppenheimer.DTO.converters;


import com.stock.oppenheimer.DTO.Kospi.KospiDTOItem;
import com.stock.oppenheimer.DTO.MktDataDTO;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KOSPIMktDtoConverter implements Converter<KospiDTOItem, MktDataDTO> {
    @Override
    public MktDataDTO convert(KospiDTOItem source) {
        MktDataDTO mktDataDTO = new MktDataDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        mktDataDTO.setTicker(source.srtnCd);
        mktDataDTO.setOpen(Long.valueOf(source.mkp));
        mktDataDTO.setClose(Long.valueOf(source.clpr));
        mktDataDTO.setHigh(Long.valueOf(source.hipr));
        mktDataDTO.setLow(Long.valueOf(source.lopr));
        mktDataDTO.setVolume(Long.valueOf(source.trqu));
        mktDataDTO.setTradeDate(LocalDate.parse(source.basDt, formatter));
        return mktDataDTO;
    }
}
