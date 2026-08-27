package com.college.enrollment.exception;

public class EnrollmentDatabaseException
        extends RuntimeException {

    public EnrollmentDatabaseException(String message) {
        super(message);
    }

    public EnrollmentDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}