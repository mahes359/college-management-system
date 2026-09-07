package com.college.enrollment.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class DuplicateEnrollmentException
        extends RuntimeException {

    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
