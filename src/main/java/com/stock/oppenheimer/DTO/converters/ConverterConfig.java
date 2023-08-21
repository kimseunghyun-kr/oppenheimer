package com.stock.oppenheimer.DTO.converters;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StockApiToStockTickerDataConverter());
        registry.addConverter(new MktDataApiToTickerMarketDataConverter());
        registry.addConverter(new KOSPIMktDtoConverter());
        registry.addConverter(new KOSPIStockDtoConverter());
    }
}
