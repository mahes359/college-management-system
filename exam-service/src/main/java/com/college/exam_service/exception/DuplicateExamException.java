package com.college.exam_service.exception;

public class DuplicateExamException extends RuntimeException {

    public DuplicateExamException(String message) {
        super(message);
    }
}