package com.college.attendance_service.exception;

public class AttendanceDatabaseException
        extends RuntimeException {

    public AttendanceDatabaseException(String message) {
        super(message);
    }

    public AttendanceDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}