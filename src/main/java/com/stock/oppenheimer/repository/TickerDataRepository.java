package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.StockTickerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerDataRepository extends JpaRepository<StockTickerData, Long> {

    StockTickerData findByTicker(String tickerName);
}
