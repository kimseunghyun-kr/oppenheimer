package com.stock.oppenheimer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    public String ticker;

//    @Column(nullable = false)
    public LocalDate lastUpdatedDate;

    @Column(nullable = false)
    public String mktCtg;

    @Column(nullable = false)
    public String stockName;

    @ElementCollection
    public List<String> associatedIndicators;



}
