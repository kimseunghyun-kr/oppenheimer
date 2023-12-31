package com.stock.oppenheimer.DTO;

import lombok.Data;

import java.time.LocalDate;


@Data
public class MktDataDTO {
    public Long open; //시가
    public Long close; //종가

    public Long high; //고가

    public Long low; //저가

    public Long volume; //거래량

    public LocalDate tradeDate; //날짜
}
