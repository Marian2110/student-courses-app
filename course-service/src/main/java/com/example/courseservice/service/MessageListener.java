package com.example.courseservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {
    private final CourseStudentService courseStudentService;

    @RabbitListener(queues = "#{fanoutQueue.name}")
    void receiveDeleteStudentFanout(Student student) {
        log.info("Received request to delete %s".formatted(student));
        courseStudentService.deleteStudentCourses(student.id());
    }
}