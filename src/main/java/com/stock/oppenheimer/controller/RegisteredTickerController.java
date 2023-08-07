package com.stock.oppenheimer.controller;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import com.stock.oppenheimer.service.StockDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/registered")
public class RegisteredTickerController {
    private StockDataService stockDataService;
    @GetMapping
    public List<String> returnAvailableTickers(){
        return stockDataService.findAllAvailableTickers();
    }

    @PostMapping("/find/{tickerName}")
    public StockTickerData findTickerInfo(@PathVariable String tickerName) {
        return stockDataService.findTicker(tickerName);
    }

}
