package com.stock.oppenheimer.controller;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.service.StockDataService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/registeredTickers")
public class RegisteredTickerController {
    private StockDataService stockDataService;
    @GetMapping("/find")
    public Page<StockTickerData> findAllMatchTickers(@Valid @ModelAttribute TickerSearchConditionDTO searchDTO, BindingResult result,
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

//    simple delete ticker
    @GetMapping("/remove/{tickerName}")
    public StockTickerData removeTicker(@Valid @PathVariable String tickerName) {
        return stockDataService.removeTicker(tickerName);
    }

//simple search ticker

    @GetMapping("/find/{tickerName}")
    public StockTickerData findTickerInfo(@PathVariable String tickerName) {
        return stockDataService.findTicker(tickerName);
    }

    @GetMapping("/add/{tickerName}")
    public StockTickerData addTicker(@PathVariable String tickerName) {
        return stockDataService.addTicker(tickerName);
    }


}
