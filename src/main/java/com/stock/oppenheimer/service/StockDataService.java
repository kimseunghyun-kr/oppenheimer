package com.stock.oppenheimer.service;

import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerMarketData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.TickerDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<StockTickerData> findAllMatching(TickerSearchConditionDTO searchDTO, Pageable pageable) {

        // create a specification object
        Specification<StockTickerData> spec = new TickerSpecification(searchDTO);
        return tickerDataRepository.findAll(spec, pageable);
    }

    public StockTickerData removeTicker (String tickerName) {
        return tickerDataRepository.deleteByTicker(tickerName);
    }

    public StockTickerData addTicker(String tickerName) {

    }
}
