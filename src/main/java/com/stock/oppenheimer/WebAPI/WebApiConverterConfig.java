package com.stock.oppenheimer.WebAPI;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApiConverterConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new KOSPIAPIDTOToStockTickerDataConverter());
        registry.addConverter(new KOSPIAPIDTOToTickerMarketDataConverter());
    }
}
