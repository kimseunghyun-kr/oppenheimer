package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerDataRepository extends JpaRepository<StockData, Long> , JpaSpecificationExecutor<StockData> {

    StockData findByTicker(String tickerName);

    StockData deleteByTicker(String tickerName);

    StockData findByStockName(String stockName);

    StockData deleteByStockName(String stockName);
}
