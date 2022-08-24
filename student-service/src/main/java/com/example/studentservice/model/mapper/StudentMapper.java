package com.example.studentservice.model.mapper;

import com.example.studentservice.model.entity.StudentEntity;
import org.example.model.dto.Student;
import org.example.model.mapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements ModelMapper<Student, StudentEntity> {
    @Override
    public Student toApi(final StudentEntity entity) {
        return Student.builder()
                .id(entity.id())
                .name(entity.name())
                .age(entity.age())
                .build();

    }

    @Override
    public StudentEntity toEntity(final Student api) {
        return StudentEntity.builder()
                .id(api.id())
                .name(api.name())
                .age(api.age())
                .build();
    }
}
