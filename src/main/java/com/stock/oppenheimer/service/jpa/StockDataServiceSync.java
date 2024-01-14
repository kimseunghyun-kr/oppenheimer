package com.stock.oppenheimer.service.jpa;

import com.stock.oppenheimer.DTO.StockDataDTO;
import com.stock.oppenheimer.WebAPI.sync.ApiServiceSync;
import com.stock.oppenheimer.controller.TickerSpecification;
import com.stock.oppenheimer.domain.StockData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.repository.jpaRepository.TickerDataJPARepository;
import com.stock.oppenheimer.service.StockDataSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;


@Service
@Transactional
public class StockDataServiceSync {

    private final TickerDataJPARepository tickerDataJPARepository;
    private final ApiServiceSync apiService;
    private final ConversionService conversionService;
    private final StockDataSave stockDataSave;

    @Autowired
    public StockDataServiceSync(TickerDataJPARepository tickerDataJPARepository, @Qualifier("webclientAdapter") ApiServiceSync apiService, ConversionService conversionService, StockDataSave stockDataSave) {
        this.tickerDataJPARepository = tickerDataJPARepository;
        this.apiService = apiService;
        this.conversionService = conversionService;
        this.stockDataSave = stockDataSave;
    }


    public Page<StockData> findAllMatching(TickerSearchConditionDTO searchDTO, Pageable pageable) {

        // create a specification object
        Specification<StockData> spec = new TickerSpecification(searchDTO);
        return tickerDataJPARepository.findAll(spec, pageable);
    }

    public StockData findByTickerName(String tickerName) {
        return tickerDataJPARepository.findByTicker(tickerName);
    }

    public StockData removeByTickerName(String tickerName) {
        return tickerDataJPARepository.deleteByTicker(tickerName);
    }

    // method reference does not work here because -> Method references are better suited for direct method calls, while lambdas might provide more flexibility when dealing with reactive chains, especially if you need to perform multiple operations or access surrounding variables.
    public StockData addStockData(String stockName, String tickerName) {
        return (stockName != null)
                ? fetchStockData(stockName, false)
                : fetchStockData(tickerName, true);
    }

    public StockData removeByStockName(String stockName) {
        return tickerDataJPARepository.deleteByStockName(stockName);
    }

    public StockData findByStockName(String stockName) {
        return tickerDataJPARepository.findByStockName(stockName);
    }

    public StockData fetchStockData (String inputString ,boolean isTicker) {
        StockDataDTO sd = null;
        if(isTicker) {
            sd = apiService.fetchStockInfo(inputString, null);
        }
        else{
            sd = apiService.fetchStockInfo(null, inputString);
        }
            StockData stockData = conversionService.convert(sd, StockData.class);
            if (sd == null) {
                return null;
            }
            stockData.setLastUpdatedDate(LocalDate.now());
            return saveStockData(stockData);

    }

    //helper methods
    public StockData saveStockData(StockData stockData) {
        return stockDataSave.stockDataSave(stockData);
    }


}
