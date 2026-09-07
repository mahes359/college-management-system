package com.college.faculty.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class FacultyValidationException extends RuntimeException {

    public FacultyValidationException(String message) {
        super(message);
    }
}
