package com.stock.oppenheimer.DTO.converters;


import com.stock.oppenheimer.DTO.KOSPIMktDataDTO;
import com.stock.oppenheimer.DTO.MktDataDTO;
import org.springframework.core.convert.converter.Converter;

public class KOSPIMktConverter implements Converter<KOSPIMktDataDTO, MktDataDTO> {
    @Override
    public MktDataDTO convert(KOSPIMktDataDTO source) {
        MktDataDTO mktDataDTO = new MktDataDTO();
        mktDataDTO.setOpen(source.mkp);
        mktDataDTO.setClose(source.dpr);
        mktDataDTO.setHigh(source.hipr);
        mktDataDTO.setLow(source.lopr);
        mktDataDTO.setVolume(source.trqu);
        mktDataDTO.setTradeDate(source.basDt);
        return mktDataDTO;
    }
}
