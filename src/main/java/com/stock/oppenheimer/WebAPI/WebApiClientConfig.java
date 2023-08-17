package com.stock.oppenheimer.WebAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebApiClientConfig{

//    Tiingo
    @Bean
    public WebClient NASDAQApiClient() {
        return WebClient.create("https://api.tiingo.com/daily/");
    }



//    금융위원회 한국거래소
    @Bean
    public WebClient KOSPIApiClient() {
        return WebClient.create("https://apis.data.go.kr/1160100/service/" +
                "GetStockSecuritiesInfoService/getStockPriceInfo");
    }


}
