package com.stock.oppenheimer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Column(nullable = false, unique = true)
    public String ticker;

    @Column(nullable = false)
    public String mktCtg;

    @Column(nullable = false)
    public String stockName;

    @OneToMany(mappedBy = "stockData")
    private List<MarketData> marketDataList;

    @OneToMany(mappedBy = "id.stock",cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Indicator> associatedIndicators;

    //    @Column(nullable = false)
    public LocalDate lastUpdatedDate = LocalDate.now();

    public Integer count;


}
