package com.college.attendance_service.exception;

public class AttendanceValidationException
        extends RuntimeException {

    public AttendanceValidationException(String message) {
        super(message);
    }
}