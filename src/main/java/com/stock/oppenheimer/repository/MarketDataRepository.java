package com.stock.oppenheimer.repository;

import com.stock.oppenheimer.domain.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    List<MarketData> findAllByStockDataStockName(String stockName);

    @Query("SELECT m.close, SUM(m.volume) FROM MarketData m WHERE m.stockData.stockName = :stockName GROUP BY m.close")
    List<Object[]> findCloseAndSumVolumeByStockName(@Param("stockName") String stockName);

    default Map<Long, Long> findCloseAndSumVolumeMapByStockData(String stockName) {
        List<Object[]> result = findCloseAndSumVolumeByStockName(stockName);

        return result.stream()
                .collect(Collectors.toMap(
                        array -> (Long) array[0],  // close
                        array -> (Long) array[1]   // sum of volume
                ));
    }
}
