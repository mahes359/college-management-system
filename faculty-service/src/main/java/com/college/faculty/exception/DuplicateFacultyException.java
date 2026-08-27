package com.college.faculty.exception;

public class DuplicateFacultyException extends RuntimeException {

    public DuplicateFacultyException(String message) {
        super(message);
    }
}