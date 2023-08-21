package com.stock.oppenheimer.domain;
import org.antlr.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

public class TickerSearchConditionDTO {
    public String ticker;

    public String nameContains;

    public Date lastUpdatedAfter;

    public Date lastUpdatedBefore;
    public String mktCtg;

    public String sortBy = "ticker";

    public boolean ascending = false;

}
