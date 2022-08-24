package com.example.courseservice.exception.custom;

public class InvalidCourseException extends RuntimeException {
    public InvalidCourseException(String message) {
        super(message);
    }
}
