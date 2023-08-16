package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import com.stock.oppenheimer.domain.StockTickerData;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerDataRepository extends JpaRepository<StockTickerData, Long> , JpaSpecificationExecutor<StockTickerData> {

    StockTickerData findByTicker(String tickerName);

    StockTickerData deleteByTicker(String tickerName);
}
