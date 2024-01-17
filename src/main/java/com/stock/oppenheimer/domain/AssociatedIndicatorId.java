package com.stock.oppenheimer.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class AssociatedIndicatorId implements Serializable {
    private Long stockId;
    private Long indicatorId;

    // Getters and setters
}
