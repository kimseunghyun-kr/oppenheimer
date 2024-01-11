package com.stock.oppenheimer.repository.r2dbcRepository;

import com.stock.oppenheimer.domain.MarketData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MarketDataRepository extends R2dbcRepository<MarketData, Long> {
}
