package com.example.studentservice.model.filter;

import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

@Builder
public record StudentFilter(
        String name,
        Integer age,
        Integer minAge,
        Integer maxAge,
        String studentId) {
    public Query getQuery() {
        return new Query(getCriteria());
    }

    private Criteria getCriteria() {
        final Criteria criteria = new Criteria();

        Optional.ofNullable(name).ifPresent(n -> criteria.and("name").regex(n));
        Optional.ofNullable(age).ifPresent(a -> criteria.and("age").is(a));
        Optional.ofNullable(studentId).ifPresent(id -> criteria.and("id").is(id));

        if (minAge != null || maxAge != null) {
            final Criteria ageCriteria = new Criteria();
            Optional.ofNullable(minAge).ifPresent(a -> ageCriteria.and("age").gte(a));
            Optional.ofNullable(maxAge).ifPresent(a -> ageCriteria.and("age").lte(a));
            criteria.andOperator(ageCriteria);
        }

        return criteria;
    }
}
