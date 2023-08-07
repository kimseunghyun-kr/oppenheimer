package com.stock.oppenheimer.service;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockDataService {
    private TickerDataRepository tickerDataRepository;

    public List<String> findAllAvailableTickers() {
        return tickerDataRepository.findAll().stream().map(x-> x.ticker).toList();
    }

    public StockTickerData findTicker(String tickerName) {
        return tickerDataRepository.findByTicker(tickerName);
    }
}
