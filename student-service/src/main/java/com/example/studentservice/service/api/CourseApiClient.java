package com.example.studentservice.service.api;

import com.example.studentservice.config.CourseApiConfig;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.CollectionResponse;
import org.example.model.dto.StudentCourse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CourseApiClient {
    private final CourseApiConfig config;

    public CollectionResponse<StudentCourse> getStudentCourses(String studentId, final Pageable pageable) {
        final String url = config.url() + "/course-student?studentId=" + studentId +
                "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize();
        return new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<CollectionResponse<StudentCourse>>() {
                }).getBody();
    }
}
