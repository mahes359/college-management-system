package com.college.enrollment.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
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
