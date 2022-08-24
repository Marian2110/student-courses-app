package com.example.courseservice.service;

import com.example.courseservice.model.entity.CourseEntity;
import com.example.courseservice.model.entity.CourseStudentEntity;
import com.example.courseservice.model.filter.CourseFilter;
import com.example.courseservice.repository.CourseDao;
import com.example.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseDao courseDAO;

    private final CourseStudentService courseStudentService;

    public Page<CourseEntity> getCourses(final CourseFilter filter, final Pageable pageable) {
        return courseDAO.getCourses(filter, pageable);

    }

    public CourseEntity createCourse(final CourseEntity entity) {
        return courseRepository.save(entity);
    }

    public Optional<CourseEntity> getCourse(final String id) {
        return courseRepository.findById(id);
    }

    public CourseStudentEntity addStudent(final String courseId, final String studentId) {
        return courseStudentService.addStudent(courseId, studentId);
    }

    public Page<CourseStudentEntity> getStudentsForCourse(final String id, final Pageable pageable) {
        return courseStudentService.getStudentsForCourse(id, pageable);
    }
}
