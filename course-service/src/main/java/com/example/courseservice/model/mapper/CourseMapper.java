package com.example.courseservice.model.mapper;

import com.example.courseservice.model.entity.CourseEntity;
import org.example.model.dto.Course;
import org.example.model.mapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements ModelMapper<Course, CourseEntity> {

    @Override
    public CourseEntity toEntity(final Course api) {
        return CourseEntity.builder()
                .id(api.id())
                .discipline(api.discipline())
                .description(api.description())
                .build();

    }

    @Override
    public Course toApi(final CourseEntity entity) {
        return Course.builder()
                .id(entity.id())
                .discipline(entity.discipline())
                .description(entity.description())
                .build();
    }
}
