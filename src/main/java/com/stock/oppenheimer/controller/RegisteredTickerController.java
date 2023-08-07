package com.stock.oppenheimer.controller;

import com.stock.oppenheimer.service.StockDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/registered")
public class RegisteredTickerController {
    private StockDataService stockDataService;
    @GetMapping
    List<String> returnAvailableTickers(){
        return stockDataService.findAllAvailableTickers();
    }
}
