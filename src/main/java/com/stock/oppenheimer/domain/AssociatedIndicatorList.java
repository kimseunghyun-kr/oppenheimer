package com.stock.oppenheimer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AssociatedIndicatorList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indicatorId;

    private String indicatorName;

    // Getters and setters
}