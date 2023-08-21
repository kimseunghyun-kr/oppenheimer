package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    List<MarketData> findAllByStockDataStockName(String stockName);
}
