package com.college.attendance_service.exception;

public class DuplicateAttendanceException
        extends RuntimeException {

    public DuplicateAttendanceException(String message) {
        super(message);
    }
}