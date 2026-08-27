package com.college.exam_service.exception;

public class ExamDatabaseException extends RuntimeException {

    public ExamDatabaseException(String message) {
        super(message);
    }

    public ExamDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}