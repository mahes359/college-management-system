package com.college.exam_service.exception;

public class CourseServiceException extends RuntimeException {

    public CourseServiceException(String message) {
        super(message);
    }

    public CourseServiceException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}