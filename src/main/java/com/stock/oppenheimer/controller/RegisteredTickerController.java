package com.stock.oppenheimer.controller;

import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.service.StockDataService;
import com.stock.oppenheimer.service.StockMarketFacadeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/registeredTickers")
public class RegisteredTickerController {
    private final StockDataService stockDataService;
    private final StockMarketFacadeService stockMarketFacadeService;

    @Autowired
    public RegisteredTickerController(StockDataService stockDataService, StockMarketFacadeService stockMarketFacadeService) {
        this.stockDataService = stockDataService;
        this.stockMarketFacadeService = stockMarketFacadeService;
    }

    //    holistic find -> includes findall and find with condition.
    @GetMapping("/find")
    public Page<StockData> findAllMatchTickers(@Valid @ModelAttribute TickerSearchConditionDTO searchDTO, BindingResult result,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        // handle any validation errors
        if (result.hasErrors()) {
            // throw an exception or return an error response
        }
        // create a pageable object with page number and size
        Pageable pageable = PageRequest.of(page, size);
        // execute the query and get the results as a page object
        // return the results as a JSON response
        return stockDataService.findAllMatching(searchDTO, pageable);
    }


//    add delete find by ticker or stockNumberString
//    simple delete ticker
    @GetMapping("/remove/{tickerName}")
    public StockData removeTickerByTicker(@Valid @PathVariable String tickerName) {
        return stockDataService.removeByTickerName(tickerName);
    }

//simple search ticker

    @GetMapping("/find/{tickerName}")
    public StockData findTickerInfoByTicker(@PathVariable String tickerName) {
        return stockDataService.findByTickerName(tickerName);
    }

    @GetMapping("/add/{tickerName}")
    public Mono<StockData> addTickerByTicker(@PathVariable String tickerName) {
        return stockMarketFacadeService.addStockMarketData(null,tickerName);
    }

// add delete find by stock name
    //    simple delete ticker
    @GetMapping("/remove/{stockName}")
    public StockData removeTickerByStockName(@Valid @PathVariable String stockName) {
        return stockDataService.removeByStockName(stockName);
    }

//simple search ticker

    @GetMapping("/find/{stockName}")
    public StockData findTickerInfoByStockName(@PathVariable String stockName) {
        return stockDataService.findByStockName(stockName);
    }

    @GetMapping("/add/{stockName}")
    public Mono<StockData> addTickerByStockName(@PathVariable String stockName) {
        return stockMarketFacadeService.addStockMarketData(stockName,null);
    }



}
