package com.college.course.exception;

public class CourseValidationException extends RuntimeException {

    public CourseValidationException(String message) {
        super(message);
    }
}