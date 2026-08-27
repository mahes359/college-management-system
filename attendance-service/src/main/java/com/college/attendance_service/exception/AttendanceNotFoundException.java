package com.college.attendance_service.exception;

public class AttendanceNotFoundException
        extends RuntimeException {

    public AttendanceNotFoundException(String message) {
        super(message);
    }
}