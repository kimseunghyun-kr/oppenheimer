package com.stock.oppenheimer.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Indicator {
    @EmbeddedId
    private IndicatorId id;

    @OneToMany(mappedBy = "indicatorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IndicatorData> indicatorData;

    // Getters and setters
}
