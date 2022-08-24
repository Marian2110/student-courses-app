package com.example.courseservice.service.api;

import com.example.courseservice.config.StudentApiConfig;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentApiClient {
    private final StudentApiConfig studentapiConfig;

    public Optional<Student> getStudentById(final String id) {
        try {
            final Student forObject = Objects.requireNonNull(new RestTemplate().exchange(studentapiConfig.url() + "/" + id,
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    new ParameterizedTypeReference<List<Student>>() {
                    }).getBody()).get(0);
            return Optional.ofNullable(forObject);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
