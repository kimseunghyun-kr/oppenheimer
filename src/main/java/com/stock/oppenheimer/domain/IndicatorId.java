package com.stock.oppenheimer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class IndicatorId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "stockId")
    private StockData stock;

    @Column(name = "indicator_list_id")
    private Long indicatorListId;

    // Getters/setters and equals/hashCode methods

}
