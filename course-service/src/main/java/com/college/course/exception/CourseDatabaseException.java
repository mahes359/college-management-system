package com.college.course.exception;

public class CourseDatabaseException extends RuntimeException {

    public CourseDatabaseException(String message) {
        super(message);
    }

    public CourseDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}