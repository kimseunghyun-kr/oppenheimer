package com.stock.oppenheimer.domain;

import jakarta.persistence.*;

@Entity
public class AssociatedIndicator {
    @EmbeddedId
    private AssociatedIndicatorId id;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private StockData stockData;

    @ManyToOne
    @MapsId("indicatorId")
    @JoinColumn(name = "indicator_id")
    private AssociatedIndicatorList indicatorList;

    private Long indicatorPrice;
    private Long indicatorVolume;

    // Getters and setters
}
