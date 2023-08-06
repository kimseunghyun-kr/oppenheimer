package com.stock.oppenheimer.WebAPI;

import java.util.Date;

public class TickerData {
    public String ticker; //단축코드
    public String mktCtg; //시장구분
    public Long open; //시가
    public Long high; //고가
    public Long low; //저가
    public Long close; //종가
    public Long volume; //거래량


    //배당락 / 분할 조정한 가격 -> 한국거래소는 없는 것으로 보임
    public Long adjOpen;
    public Long adjClose;
    public Long adjVolume;
    public Long dividend;
    public Long splitFactor;



}
