package com.college.course.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
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
