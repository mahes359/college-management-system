package com.college.faculty.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(String message) {
        super(message);
    }
}
