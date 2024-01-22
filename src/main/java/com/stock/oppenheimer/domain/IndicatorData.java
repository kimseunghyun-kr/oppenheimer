package com.stock.oppenheimer.domain;

import jakarta.persistence.*;

@Entity
public class IndicatorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private IndicatorId indicatorId;
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "stock_id", referencedColumnName = "stock_id"),
//            @JoinColumn(name = "indicator_list_id", referencedColumnName = "indicator_list_id")
//    })
//    private Indicator indicator;
    @Column(name = "data_key")
    private String dataKey;

    @Column(name = "data_value")
    private String dataValue;

    // Constructors, getters, and setters

}
