package com.example.courseservice.service;

import com.example.courseservice.model.entity.CourseStudentEntity;
import com.example.courseservice.model.entity.JoinedCourseStudentEntity;
import com.example.courseservice.repository.CourseStudentDao;
import com.example.courseservice.repository.CourseStudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseStudentService {
    private final CourseStudentRepository courseStudentRepository;
    private final CourseStudentDao courseStudentDao;

    public List<CourseStudentEntity> getCourseStudents(final String studentId) {
        return courseStudentRepository.findByStudentId(studentId);
    }

    public CourseStudentEntity addStudent(final String courseId, final String studentId) {
        return courseStudentRepository.save(CourseStudentEntity.builder()
                .courseId(courseId)
                .studentId(studentId)
                .build());
    }

    public Page<CourseStudentEntity> getStudentsForCourse(final String id, final Pageable pageable) {
        return courseStudentRepository.findByCourseId(id, pageable);
    }

    public Page<JoinedCourseStudentEntity> getJoinedCourseStudents(final String studentId, Pageable pageable) {
        return courseStudentDao.getJoinedCourseStudentsByStudentId(studentId, pageable);
    }

    public void deleteStudentCourses(final String id) {
        final List<CourseStudentEntity> byStudentId = courseStudentRepository.findByStudentId(id);
        courseStudentRepository.deleteAll(byStudentId);
        log.info(byStudentId.size() + " entries deleted form CourseStudentEntity");
    }
}
