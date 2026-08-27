package com.college.exam_service.exception;

public class ExamValidationException extends RuntimeException {

    public ExamValidationException(String message) {
        super(message);
    }
}