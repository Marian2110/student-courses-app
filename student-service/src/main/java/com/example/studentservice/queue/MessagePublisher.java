package com.example.studentservice.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.Student;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final FanoutExchange fanoutExchange;
    private final RabbitTemplate rabbit;

    public void publishDeleteStudentFanout(Student student) {
        log.info("publishing on delete student fanout %s".formatted(student));
        rabbit.convertAndSend(fanoutExchange.getName(), "", student);
    }
}
