package com.stock.oppenheimer.DTO;

import lombok.Data;

import java.time.LocalDate;


@Data
public class KOSPIMktDataDTO {

    public Long mkp; //시가
    public Long dpr; //종가

    public Long hipr; //고가

    public Long lopr; //저가

    public Long trqu; //거래량
    public LocalDate basDt; //날짜

}
