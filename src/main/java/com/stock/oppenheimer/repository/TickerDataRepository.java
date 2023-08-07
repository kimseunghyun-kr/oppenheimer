package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.TickerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerDataRepository extends JpaRepository<TickerData, Long> {
}
