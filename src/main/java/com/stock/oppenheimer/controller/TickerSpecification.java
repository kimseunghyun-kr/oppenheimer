package com.stock.oppenheimer.controller;

import com.stock.oppenheimer.domain.StockTickerData;
import com.stock.oppenheimer.domain.TickerSearchConditionDTO;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TickerSpecification implements Specification<StockTickerData> {

    TickerSearchConditionDTO searchConditionDTO;

    public TickerSpecification(TickerSearchConditionDTO searchConditionDTO) {
        this.searchConditionDTO = searchConditionDTO;
    }

    @Override
    public Predicate toPredicate(Root<StockTickerData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(searchConditionDTO.nameContains != null && !searchConditionDTO.nameContains.isEmpty()){
            predicates.add(cb.like(root.get("ticker"), "%" + searchConditionDTO.nameContains + "%"));
        }

        // add predicate for lastUpdatedDate
        if (searchConditionDTO.lastUpdatedAfter != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("lastUpdatedDate"), searchConditionDTO.lastUpdatedAfter));
        }

        // add predicate for lastUpdatedDate
        if (searchConditionDTO.lastUpdatedBefore != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("lastUpdatedDate"), searchConditionDTO.lastUpdatedAfter));
        }

        // add predicate for region
        if (searchConditionDTO.region != null && !searchConditionDTO.region.isEmpty()) {
            predicates.add(cb.equal(root.get("region"), searchConditionDTO.region));
        }

        // add predicate for sortBy
        Order order = searchConditionDTO.ascending ? cb.asc(root.get(searchConditionDTO.sortBy)) : cb.desc(root.get(searchConditionDTO.sortBy));

        // set the order by clause of the query with the Order object
        query.orderBy(order);

        // combine all predicates using and operator
        return cb.and(predicates.toArray(new Predicate[0]));

    }
}
