package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
}
