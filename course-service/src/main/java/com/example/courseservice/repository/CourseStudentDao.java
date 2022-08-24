package com.example.courseservice.repository;

import com.example.courseservice.model.entity.CourseEntity;
import com.example.courseservice.model.entity.CourseStudentEntity;
import com.example.courseservice.model.entity.JoinedCourseStudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CourseStudentDao {
    private final MongoTemplate mongoTemplate;

    public Page<JoinedCourseStudentEntity> getJoinedCourseStudentsByStudentId(final String studentId, final Pageable pageable) {
        final Query query = new Query(new Criteria().and("studentId").is(studentId));
        final long total = mongoTemplate.count(query, CourseStudentEntity.class);
        final Aggregation aggregation = getStudentIdAggregation(studentId, pageable);
        final List<JoinedCourseStudentEntity> results = getMappedResults(aggregation);

        return new PageImpl<>(results, pageable, total);
    }

    private List<JoinedCourseStudentEntity> getMappedResults(final Aggregation aggregation) {
        return mongoTemplate
                .aggregate(
                        aggregation,
                        mongoTemplate.getCollectionName(CourseStudentEntity.class),
                        JoinedCourseStudentEntity.class)
                .getMappedResults();
    }

    private Aggregation getStudentIdAggregation(final String studentId, final Pageable pageable) {
        return Aggregation.newAggregation(
                getCoursesAggregation(),
                match(where("studentId").is(studentId)),
                skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                limit(pageable.getPageSize()));
    }

    private AggregationOperation getCoursesAggregation() {
        return lookUp -> new Document("$lookup",
                new Document("from", mongoTemplate.getCollectionName(CourseEntity.class))
                        .append("let", new Document("courseId", new Document("$toObjectId", "$courseId")))
                        .append("pipeline",
                                List.of(new Document("$match",
                                        new Document("$expr",
                                                new Document("$eq", Arrays.asList("$_id", "$$courseId"))))))
                        .append("as", "courses"));
    }

}
