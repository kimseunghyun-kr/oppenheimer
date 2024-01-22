package com.stock.oppenheimer.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "indicator_list")
public class IndicatorList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indicator_list_id")
    private Long indicatorListId;

    @Column(name = "indicator_name")
    private String indicatorName;

    // Other fields and getters/setters

}