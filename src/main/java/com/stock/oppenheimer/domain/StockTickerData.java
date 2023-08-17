package com.stock.oppenheimer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class StockTickerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    public String ticker;

//    @Column(nullable = false)
    public Date lastUpdatedDate;

    @Column(nullable = false)
    public String mktCtg;

    @Column(nullable = false)
    public String stockName;

    @ElementCollection
    public List<String> associatedIndicators;



}
